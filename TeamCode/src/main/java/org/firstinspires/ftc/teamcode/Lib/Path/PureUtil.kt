package org.firstinspires.ftc.teamcode.Lib.Path

import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint
import org.firstinspires.ftc.teamcode.Lib.Structs.Point
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D
import org.firstinspires.ftc.teamcode.Lib.Util.div
import org.firstinspires.ftc.teamcode.Lib.Util.minus
import org.firstinspires.ftc.teamcode.Lib.Util.times
import org.firstinspires.ftc.teamcode.Lib.Util.wrap
import kotlin.math.*

enum class MOVEMENTS(val speed: Double) {
    NORMAL(.5),
    THEQUICK(.7),
    ACCURATE(.3)
}

// 8802s algorithm before we make our own. All credit goes to them
fun PureController(pose2D: Pose2D, vel2D: Pose2D, target: Waypoint, speed: MOVEMENTS) : List<Double> {
    val targetSDelta = (target - pose2D).magnitude
    val targetTDeltaPoint = (pose2D - target)
    val targetTDeltaAngle = atan2(targetTDeltaPoint.y, targetTDeltaPoint.x)
    val translation = Point(cos(targetTDeltaAngle), sin(targetTDeltaAngle)) * targetSDelta
    val tp = Pose2D(-translation.x, -translation.y, 0.0) * speed.speed

    val reduction = Pose2D(6.0,6.0, PI / 2)
    tp.x = tp.x / reduction.x
    tp.y = tp.y / reduction.y

    val forwardDelta = target - pose2D
    val forwardT = atan2(forwardDelta.y, forwardDelta.x)
    val backT = forwardT + PI
    val relativeFront = (forwardT - pose2D.heading).wrap()
    val relativeBack = (backT - pose2D.heading).wrap()
    val closestAngle = if (abs(relativeFront) < abs(relativeBack)) {
        relativeFront
    } else {
        relativeBack
    }
    val desired = if (target.heading.isNaN()) {
        closestAngle
    } else {
        target.heading
    }
    val targetAngle = (desired - pose2D.heading).wrap()
    tp.heading = targetAngle/ reduction.heading;

    val fl = tp.x - tp.heading - tp.y
    val bl = tp.x - tp.heading + tp.y
    val fr = tp.x + tp.heading + tp.y
    val br = tp.x + tp.heading - tp.y

    return listOf(fl, fr, bl, br)
}
