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

fun lookAhead(rloc: Pose2D, closest : Waypoint, next: Waypoint, lookDist: Double) : Waypoint? {
    val d = next - closest
    val f = closest - rloc
    val a = d dot d
    val b = 2.0 * (f dot d)
    val c = (f dot f) - lookDist.pow(2.0)
    var discrim = b.pow(2.0) - 4.0 * a * c
    val newC = closest
    if (discrim < 0) {
        return null
    } else {
        discrim = sqrt(discrim)
        val t0 : Double = (-b - discrim) / (2.0 * a)
        val t1 : Double = (-b + discrim) / (2.0 * a)
        if (t0 in 0.0..1.0) {
            val pt = (closest + (d * t0))
            newC.x = pt.x
            newC.y = pt.y
            return newC
        }
        if (t1 in 0.0..1.0) {
            val pt = (closest + (d * t1))
            newC.x = pt.x
            newC.y = pt.y
            return newC
        }
        //no intersection
    }
    return null
}