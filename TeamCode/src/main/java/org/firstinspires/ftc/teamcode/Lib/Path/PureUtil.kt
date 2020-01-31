package org.firstinspires.ftc.teamcode.Lib.Path

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint
import org.firstinspires.ftc.teamcode.Lib.Structs.Point
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D
import org.firstinspires.ftc.teamcode.Lib.Util.*
import kotlin.math.*
enum class MOVEMENTS(val speed: Double) {
    NORMAL(1.0),
    THEQUICK(.7),
    ACCURATE(.3)
}

fun PureController(pose2D: Pose2D, vel2D: Pose2D, target: Waypoint, speed: MOVEMENTS) : List<Double> {
    val targetSDelta = (target - pose2D).magnitude
    val targetTDeltaPoint = (pose2D - target)
    val targetTDeltaAngle = atan2(targetTDeltaPoint.y, targetTDeltaPoint.x) - pose2D.heading
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
    tp.heading = targetAngle/ reduction.heading

    val fl = tp.x - tp.heading - tp.y
    val bl = tp.x - tp.heading + tp.y
    val fr = tp.x + tp.heading + tp.y
    val br = tp.x + tp.heading - tp.y

    return listOf(fl, fr, bl, br)
}

var MAX_VELOCITY = Pose2D(48.0, 60.0, 5.0)
var SLIP_DISTANCES = Pose2D(1.0, 1.0, Math.toRadians(1.0))
var MIN_TRANSLATION_POWERS = 0.3
var GUNNING_REDUCTION_DISTANCES = Pose2D(6.0, 6.0, Math.PI / 2)
var FINE_REDUCTION_DISTANCES = Pose2D(30.0, 30.0, Math.PI / 2)

fun goToPosition(robotPose: Pose2D, robotVelocity: Pose2D, target: Waypoint, movementSpeed: Double, gunning: Boolean): List<Double> {

    val relSlip : Pose2D = SLIP_DISTANCES * (robotVelocity / (MAX_VELOCITY))

    val distance = target.minus(robotPose).magnitude
    val relAngle = (robotPose.minus(target)).atan() - robotPose.heading
    val relX = distance * cos(relAngle) + relSlip.x
    val relY = distance * sin(relAngle) + relSlip.y

    val translationPowers = Pose2D(-relX, -relY, 0.0) * (movementSpeed)

    val reductionDistances = if (gunning) GUNNING_REDUCTION_DISTANCES else FINE_REDUCTION_DISTANCES
    translationPowers.x /= reductionDistances.x
    translationPowers.y /= reductionDistances.y

    if (translationPowers.magnitude < MIN_TRANSLATION_POWERS) {
        translationPowers * (MIN_TRANSLATION_POWERS / translationPowers.magnitude)
    }

    val forwardAngle = target.minus(robotPose).atan()
    val backwardAngle = forwardAngle + Math.PI
    val angleToForward = (forwardAngle - robotPose.heading).wrap()
    val angleToBackward = (backwardAngle - robotPose.heading).wrap()
    val autoAngle = if (abs(angleToForward) < abs(angleToBackward)) forwardAngle else backwardAngle
    val desiredAngle = if (target.heading.isNaN())
        autoAngle
    else
        target.heading

    val angleToTarget = (desiredAngle - robotPose.heading).wrap()
    translationPowers.heading = angleToTarget / reductionDistances.heading

    var fl : Double = translationPowers.x - translationPowers.heading - translationPowers.y
    var bl : Double = translationPowers.x - translationPowers.heading + translationPowers.y
    var fr : Double = translationPowers.x + translationPowers.heading + translationPowers.y
    var br : Double = translationPowers.x + translationPowers.heading - translationPowers.y
    val max : Double = max(max(fl,fr), max(bl,br))
    val min : Double = min(min(fl,fr), min(bl,br))
    val absMax = max(max, -min)
    if (absMax > 1) {
        fl /= max
        bl /= max
        fr /= max
        br /= max
    }

    return listOf(
            fl,
            fr,
            bl,
            br
    )
}