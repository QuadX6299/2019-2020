package org.firstinspires.ftc.teamcode.Lib.Path

import android.media.tv.TvRecordingClient
import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint
import org.firstinspires.ftc.teamcode.Lib.Structs.Point
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D
import org.firstinspires.ftc.teamcode.Lib.Util.*
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sqrt

fun lookAhead(rloc: Point, closest : Point, next: Point, follow: Double) : Pair<Double?, Double?> {
    val d = next - closest
    val f = closest - rloc
    val a = d dot d
    val b = 2.0 * (f dot d)
    val c = (f dot f) - follow.pow(2.0)
    var discrim = b.pow(2.0) - 4.0 * a * c
    if (discrim < 0) {
        return Pair(null,null)
    } else {
        discrim = sqrt(discrim)
        val t0 : Double = (-b - discrim) / (2.0 * a)
        val t1 : Double = (-b + discrim) / (2.0 * a)
        var one : Double? = null
        var two : Double? = null
        if (t0 in 0.0..1.0) {
            one = t0
        }
        if (t1 in 0.0..1.0) {
            two = t1
        }
        return Pair(one,two)
    }
}

fun List<Waypoint>.toRed() {
    this.forEach {
        it.y *= -1
        it.heading = (it.heading + PI).limitAngle2()
    }
}