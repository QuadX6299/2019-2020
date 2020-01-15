package org.firstinspires.ftc.teamcode.Lib.Path

import org.firstinspires.ftc.teamcode.Lib.Hooks.BlockingLoop
import org.firstinspires.ftc.teamcode.Lib.Hooks.SingleRoutine
import org.firstinspires.ftc.teamcode.Lib.Marker.End
import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D
import org.firstinspires.ftc.teamcode.Modules.Robot

class Follower constructor(val sLambda: () -> Pose2D, val vLambda: () -> Pose2D, val path: List<Waypoint>, val r: Robot) {

    var index: Int = 0


    fun update() {
        val rloc = sLambda()
        val rvel = vLambda()

        //TODO Get closest point
        val closest: Waypoint = Waypoint(0.0,0.0, 10.0)



        when (closest) {
            is End -> println("End")
        }

        when (closest.hook) {
            is BlockingLoop -> {
                do {
                    println("In Loop")
                } while (!closest.hook.blockLoop(r))
            }
            is SingleRoutine -> {
                closest.hook.action(r)
            }
        }
    }
}