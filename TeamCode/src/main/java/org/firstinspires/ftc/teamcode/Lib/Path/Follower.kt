package org.firstinspires.ftc.teamcode.Lib.Path

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.Lib.Hooks.BlockingLoop
import org.firstinspires.ftc.teamcode.Lib.Hooks.SingleRoutine
import org.firstinspires.ftc.teamcode.Lib.Marker.End
import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D
import org.firstinspires.ftc.teamcode.Modules.Robot
import kotlin.math.min

class Follower constructor(val path: List<Waypoint>, val r: Robot, val op: OpMode) {
    var done = false
    var index: Int = 0


    fun update(rloc: Pose2D): List<Double> {

        closestWaypoint(rloc)

        val closest = path[index]

        when (closest) {
            is End -> {
                return listOf(0.0,0.0,0.0,0.0)
            }
        }

        when (closest.hook) {
            is BlockingLoop -> {
                closest.hook.blockLoop(r)
            }
            is SingleRoutine -> {
                if (!done) {
                    closest.hook.action(r)
                }
            }
        }

        var lookAhead : Waypoint? = null
        for (i in index until path.size - 1) {
            lookAhead = lookAhead(rloc, path[i], if (i + 1 == path.size) path[i] else path[i+1], closest.followDistance)
            if (lookAhead != null) {
                break
            }
        }
        op.telemetry.addData("lookA", lookAhead)
        op.telemetry.addData("Closest", closest)

        op.telemetry.update()
        requireNotNull(lookAhead) { "Lookahead null" }
        return goToPosition(rloc, Pose2D(20.0,20.0,0.0), lookAhead, .2, true)
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