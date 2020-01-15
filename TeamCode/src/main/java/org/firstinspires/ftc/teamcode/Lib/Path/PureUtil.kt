package org.firstinspires.ftc.teamcode.Lib.Path

import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint
import org.firstinspires.ftc.teamcode.Lib.Structs.Point
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D
import org.firstinspires.ftc.teamcode.Lib.Util.minus
import org.firstinspires.ftc.teamcode.Lib.Util.times
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

enum class MOVEMENTS {
    NORMAL,
    THEQUICK,
    ACCURATE
}

fun PureController(pose2D: Pose2D, vel2D: Pose2D, target: Waypoint) : List<Double> {
    val targetSDelta = (target - pose2D).magnitude
    val targetTDeltaPoint = (pose2D - target)
    val targetTDeltaAngle = atan2(targetTDeltaPoint.y, targetTDeltaPoint.x)
    val translation = Point(cos(targetTDeltaAngle), sin(targetTDeltaAngle)) * targetSDelta


    return listOf()
}