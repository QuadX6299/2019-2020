package org.firstinspires.ftc.teamcode.Lib.Path

import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint
import org.firstinspires.ftc.teamcode.Lib.Structs.Point
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D
import org.firstinspires.ftc.teamcode.Lib.Util.*
import kotlin.math.*

fun PureController(rloc: Pose2D, target: Waypoint, fast: Boolean, speed: Double, rvel: Pose2D = Pose2D(0.0,0.0,0.0)) : List<Double> {

    val error : Pose2D = Pose2D(1.0,1.0, 1.0.d2r()) * (rvel / Pose2D(48.0, 60.0, 5.0))

    val distance = (target - rloc).magnitude
    val angle = (rloc - target).atan() - rloc.heading
    val relVector = Pose2D(-1 * (distance * cos(angle) + error.x), -1 * (distance * sin(angle) + error.y), 0.0) * speed
    val pVector = (relVector as Point) / (if (fast) 6.0 else 30.0)
    val finalVector = Pose2D(pVector, 0.0)

    if (finalVector.magnitude < .3) {
        finalVector * (.3 / finalVector.magnitude)
    }

    val pog : Double = ({
        if (target.heading.isNaN())  {
            val angleToPt = (target - rloc).atan()
            if (abs((angleToPt-rloc.heading).wrap()) < abs(((angleToPt + PI) - rloc.heading).wrap())) angleToPt else angleToPt + PI
        } else {
            target.heading
        }
    }() - rloc.heading).wrap()

    finalVector.heading = pog / (Math.PI / 2.0)

    return finalVector.toPowers()
}
