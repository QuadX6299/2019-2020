package org.firstinspires.ftc.teamcode.Lib.Path

import android.media.tv.TvRecordingClient
import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint
import org.firstinspires.ftc.teamcode.Lib.Structs.Point
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D
import org.firstinspires.ftc.teamcode.Lib.Util.dot
import org.firstinspires.ftc.teamcode.Lib.Util.minus
import org.firstinspires.ftc.teamcode.Lib.Util.plus
import org.firstinspires.ftc.teamcode.Lib.Util.times
import kotlin.math.pow
import kotlin.math.sqrt

fun lookAhead(rloc: Pose2D, closest : Waypoint, next: Waypoint) : Waypoint? {
    val d = next - closest
    val f = closest - rloc
    val a = d dot d
    val b = 2.0 * (f dot d)
    val c = (f dot f) - closest.followDistance.pow(2.0)
    var discrim = b.pow(2.0) - 4.0 * a * c
    if (discrim < 0) {
        return null
    } else {
        discrim = sqrt(discrim)
        val t0 : Double = (-b - discrim) / (2.0 * a)
        val t1 : Double = (-b + discrim) / (2.0 * a)
        if (t0 in 0.0..1.0) {
            val pt = (closest + (d * t0))
            return Waypoint(pt.x, pt.y, closest.heading, closest.followDistance, closest.hook)
        }
        if (t1 in 0.0..1.0) {
            val pt = (closest + (d * t1))
            return Waypoint(pt.x, pt.y, closest.heading, closest.followDistance, closest.hook)
        }
        //no intersection
    }
    return null
}