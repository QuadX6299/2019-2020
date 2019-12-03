package org.firstinspires.ftc.teamcode.lib.Path

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.PIDCoefficients
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.lib.Constraints.DriveKinematics
import org.firstinspires.ftc.teamcode.lib.Coords.Point
import org.firstinspires.ftc.teamcode.lib.Coords.Position
import org.firstinspires.ftc.teamcode.lib.Coords.State
import org.firstinspires.ftc.teamcode.lib.Extensions.*
import kotlin.math.*
import org.firstinspires.ftc.teamcode.Robot.Meta.Constants
import org.firstinspires.ftc.teamcode.lib.Constraints.TankKinematics


class NPathFollower constructor(var path : MutableList<State>, var lookDist : Double = 10.0, val kinematics: DriveKinematics = TankKinematics(Constants.DT_WIDTH), val acceleration : Double = 20.0, val clock : ElapsedTime, val op : OpMode) {
    var lastClosest : Int = 0
    var previousVelocity : Double = 0.0
    var isDone : Boolean = false
    var rloc: Position = Position(0.0,0.0,0.0)
    var lastTime = Double.NaN

    private fun closestWaypoint() : State {
        var minDist = Double.MAX_VALUE
        var closestPoint = path.get(0)
        for (i in lastClosest until path.size) {
            val dist = rloc distance path[i]
            if (minDist > dist) {
                minDist = dist
                closestPoint = path[i]
                lastClosest = i
            }
        }
        return closestPoint
    }

    fun getTargets(rloc: Position) : List<Double> {
        this.rloc = rloc
        val time = clock.seconds()
        val closestPoint = closestWaypoint()
        if (rloc.x.fuzzyEquals(path.last().x, 2.0) && rloc.y.fuzzyEquals(path.last().y,2.0)) {
            isDone = true
            return listOf(0.0,0.0)
        }
        if (closestPoint == path[path.size-1] || isDone) {
            isDone = true
            return listOf(0.1,0.1)
        }
        var looka : Point = closestPoint.next
        for (waypoint in path.subList(lastClosest, path.size)) {
            val lookb = lookAhead(rloc, waypoint)
            if (lookb != null) {
                looka = lookb
                break
            }
        }
        val curvature = findCurvature(looka)
        val maxC : Double = (time - if (lastTime.isNaN()) 0.0 else lastTime) * (acceleration)
        lastTime = time
        val totalChange : Double = (closestPoint.velocity - previousVelocity).clip(-maxC, maxC)
        val newVelocity : Double = previousVelocity + totalChange
        previousVelocity = newVelocity
        return kinematics.getTargetVelocities(min(closestPoint.velocity, newVelocity), curvature)
    }

    private fun lookAhead(rloc: Position, closest : State) : Point? {
        val d = closest.next - closest
        val f = closest - rloc
        val a = d dot d
        val b = 2.0 * (f dot d)
        val c = (f dot f) - lookDist.pow(2.0)
        var discrim = b.pow(2.0) - 4.0 * a * c
        if (discrim < 0) {
            return null
        } else {
            discrim = sqrt(discrim)
            val t0 : Double = (-b - discrim) / (2.0 * a)
            val t1 : Double = (-b + discrim) / (2.0 * a)
            if (t0 in 0.0..1.0) {
                return (closest + (d * t0))
            }
            if (t1 in 0.0..1.0) {
                return (closest + (d * t1))
            }
            //no intersection
        }
        return null
    }

    private fun findCurvature(lookAheadPoint: Point) : Double {
        val a = -tan(rloc.heading)
        val b = 1.0
        val c = tan(rloc.heading) * rloc.x - rloc.y
        val x = abs(a * lookAheadPoint.x + b * lookAheadPoint.y + c) / sqrt(a.pow(2) + b.pow(2))
        val side = sign(sin(rloc.heading) * (lookAheadPoint.x - rloc.x) - cos(rloc.heading) * (lookAheadPoint.y - rloc.y))
        return (2.0 * x / lookDist.pow(2)) * side
    }
}