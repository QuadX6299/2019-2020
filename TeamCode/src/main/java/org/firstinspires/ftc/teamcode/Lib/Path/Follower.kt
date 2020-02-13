package org.firstinspires.ftc.teamcode.Lib.Path

import android.os.Handler
import android.os.Looper
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.Lib.Hooks.*
import org.firstinspires.ftc.teamcode.Lib.Marker.*
import org.firstinspires.ftc.teamcode.Lib.Structs.Point
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D
import org.firstinspires.ftc.teamcode.Lib.Util.minus
import org.firstinspires.ftc.teamcode.Lib.Util.plus
import org.firstinspires.ftc.teamcode.Lib.Util.times
import org.firstinspires.ftc.teamcode.Modules.Robot
import java.util.*
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

class Follower constructor(val path: List<Waypoint>, val r: Robot, val op: OpMode) {
    var done = false
    var index : Int = 0
    var lastFracdex : Double = 0.0


    fun update(rloc: Pose2D): List<Double> {

        closestWaypoint(rloc)

        val closest = path[index]



        when (closest.hook) {
            is Static -> {
                op.telemetry.addData("Static done? ", closest.hook.isDone())
                if (closest.hook is ArrivalRoutine) {
                    op.telemetry.addData("Distance? ", rloc distance closest)
                    closest.hook.update(rloc, closest)
                }
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
            is Interrupt -> {
                closest.hook as Static
                op.telemetry.addData("Routine Done? ", (closest.hook).isDone())
            }
        }

        val minheap : PriorityQueue<Double> = PriorityQueue()

        for (i in path.indices) {
            if (path[i] is Interrupt && !(path[i].hook as Static).isDone()) {
                if (rloc distance path[i] < closest.followDistance) {
                    return PureController(rloc, path[i], true, 1.0)
                }
                minheap.add(i.toDouble())
                break
            }
            val indicies = lookAhead(rloc, path[i], if (i == path.size - 1) path[i] else path[i+1], closest.followDistance)
            if (indicies.first != null) {
                val pog = indicies.first!! + i
                if (pog > lastFracdex) {
                    minheap.add(pog)
                }
            }
            if (indicies.second != null) {
                val pog = indicies.second!! + i
                if (pog > lastFracdex) {
                    minheap.add(pog)
                }
            }
        }
        if (minheap.peek() != null) {
            val fractionalIndex : Double = if (minheap.peek() == 0.0) index.toDouble() else minheap.peek()
            val aheadIndex = floor(minheap.peek()).toInt()
            val lookAheadPoint = path[aheadIndex] + (path[aheadIndex+1] - path[aheadIndex]) * (fractionalIndex - aheadIndex)
            val waypoint = Waypoint(lookAheadPoint.x, lookAheadPoint.y, closest.heading, closest.followDistance, closest.hook)
            if (fractionalIndex < lastFracdex) op.telemetry.addLine("Look Ahead Bad Routing to Closest")
            lastFracdex = fractionalIndex
            return PureController(rloc, if (fractionalIndex < lastFracdex) closest else waypoint, true, if (closest is SpeedPoint) closest.speed else 1.0)
        } else {
            op.telemetry.addLine("Look ahead not found routing to le monke")
            return PureController(rloc, if (index == path.size - 1) path[index] else path[index+1], true, 1.0)
        }
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