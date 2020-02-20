package org.firstinspires.ftc.teamcode.Lib.Path

import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint
import org.firstinspires.ftc.teamcode.Lib.Structs.Point
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D
import org.firstinspires.ftc.teamcode.Lib.Util.*
import kotlin.math.*

fun PureController(rloc: Pose2D, target: Waypoint, fast: Boolean, speed: Double) : List<Double> {

    var mp = .3

    val distance = (target - rloc).magnitude
    if (abs(distance) < 1.0) mp = .1
    val angle = (rloc - target).atan() - rloc.heading
    val relVector = Pose2D(-1 * (distance * cos(angle)), -1 * (distance * sin(angle)), 0.0) * speed
    val pVector = (relVector as Point) / (if (fast) 10.0 else 20.0)
    var finalVector = Pose2D(pVector, 0.0)

    if (finalVector.magnitude < mp) {
        finalVector *= (mp / finalVector.magnitude)
    }

    val pog : Double = ({
        if (target.heading.isNaN())  {
            val angleToPt = (target - rloc).atan()
            if (abs((angleToPt-rloc.heading).wrap()) < abs(((angleToPt + PI) - rloc.heading).wrap())) angleToPt else angleToPt + PI
        } else {
            target.heading
        }
    }() - rloc.heading).wrap()

    finalVector.heading = pog / (TestConstants.reductionHeading)

    return finalVector.toPowers()
}
