package org.firstinspires.ftc.teamcode.Lib.Path

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.Lib.Hooks.BlockingLoop
import org.firstinspires.ftc.teamcode.Lib.Hooks.SingleRoutine
import org.firstinspires.ftc.teamcode.Lib.Hooks.Static
import org.firstinspires.ftc.teamcode.Lib.Hooks.StaticRoutine
import org.firstinspires.ftc.teamcode.Lib.Marker.*
import org.firstinspires.ftc.teamcode.Lib.Structs.Point
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D
import org.firstinspires.ftc.teamcode.Lib.Util.minus
import org.firstinspires.ftc.teamcode.Lib.Util.plus
import org.firstinspires.ftc.teamcode.Lib.Util.times
import org.firstinspires.ftc.teamcode.Modules.Robot
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

class Follower constructor(val path: List<Waypoint>, val r: Robot, val op: OpMode) {
    var done = false
    var index: Int = 0


    fun update(rloc: Pose2D): List<Double> {

        closestWaypoint(rloc)

        val closest = path[index]



        when (closest.hook) {
            is StaticRoutine -> {
                closest.hook.exec()
            }
            is BlockingLoop -> {
                closest.hook.blockLoop(r)
            }
            is SingleRoutine -> {
                if (!done) {
                    closest.hook.action(r)
                }
            }
        }

        when (closest) {
            is Accurate -> {
                if (closest.hook != null) {
                    if (!(closest.hook as Static).isDone()) return goToPosition(rloc, Pose2D(10.0,10.0,0.0), closest, .3, false)
                }
            }
            is SpeedEnd -> {
                if (rloc distance closest < closest.tolS) {
                    done = true
                    op.telemetry.addData("collect done ", true)
                    op.telemetry.update()
                    return listOf(0.0,0.0,0.0,0.0)
                }
                op.telemetry.addData("dist ", rloc distance closest)
                op.telemetry.update()
                return goToPosition(rloc, Pose2D(10.0,10.0,0.0), closest, .7, true)
            }
            is End -> {
                if (rloc distance closest < closest.tolS) {
                    done = true
                    op.telemetry.addData("collect done ", true)
                    op.telemetry.update()
                    return listOf(0.0,0.0,0.0,0.0)
                }
                op.telemetry.addData("dist ", rloc distance closest)
                op.telemetry.update()
                return goToPosition(rloc, Pose2D(10.0,10.0,0.0), closest, 1.0, false)
            }
        }

        var fractionalIndex : Double = Double.MIN_VALUE

        for (i in 0 until path.size) {
            if (path[i] is Accurate && (path[i] as Accurate).hook != null && !((path[i] as Accurate).hook as Static).isDone()) {
                op.telemetry.addLine("Accurate in")
                op.telemetry.update()
                fractionalIndex = i.toDouble()
                break
            }
            val indicies = lookAhead(rloc, path[i], if (i == path.size - 1) path[i] else path[i+1], closest.followDistance)
            if (indicies.first != null) {
                fractionalIndex = max(fractionalIndex, indicies.first!! + i)
            }
            if (indicies.second != null) {
                fractionalIndex = max(fractionalIndex, indicies.second!! + i)
            }
        }

        val aheadIndex = floor(fractionalIndex).toInt()

        val lookAheadPoint = path[aheadIndex] + (path[aheadIndex+1] - path[aheadIndex]) * (fractionalIndex - aheadIndex)
        op.telemetry.addData("Fractional Index ", fractionalIndex)
        op.telemetry.addData("Look ", lookAheadPoint)
        op.telemetry.addData("Closest ", closest)
        op.telemetry.update()

        val waypoint = Waypoint(lookAheadPoint.x, lookAheadPoint.y, closest.heading, closest.followDistance, closest.hook)
        if (fractionalIndex < index) op.telemetry.addLine("Look ahead not found")
        return goToPosition(rloc, Pose2D(10.0,10.0,0.0), if (fractionalIndex < index) closest else waypoint, if (closest is SpeedPoint) closest.speed else .5, true)
//        var lookAhead : Waypoint? = null
//        for (i in index until path.size) {
//            lookAhead = lookAhead(rloc, path[i], if (i == path.size - 1) path[i] else path[i+1])
//            if (lookAhead != null) {
//                break
//            }
//        }
//        if (lookAhead == null) {
//            op.telemetry.addData("Look Ahead Not Found: ", lookAhead)
//            op.telemetry.update()
//            lookAhead = path[index + 1]
//        }
//        if (closest is Accurate) {
//            goToPosition(rloc, Pose2D(10.0,10.0,0.0), lookAhead, .7, false)
//        }
//        return goToPosition(rloc, Pose2D(10.0,10.0,0.0), lookAhead, .7, true)
    }

    fun closestWaypoint(rloc: Pose2D) {
        var minDist = Double.MAX_VALUE
        for (i in index until path.size) {
            val dist = rloc distance path[i]
            if (dist < minDist) {
                minDist = dist
                index = i
            }
        }
    }
}