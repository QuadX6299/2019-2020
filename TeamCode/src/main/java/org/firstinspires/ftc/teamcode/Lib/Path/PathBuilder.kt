package org.firstinspires.ftc.teamcode.Lib.Path

import org.firstinspires.ftc.teamcode.Lib.Marker.End
import org.firstinspires.ftc.teamcode.Lib.Structs.Point
import org.firstinspires.ftc.teamcode.Lib.Util.plus
import org.firstinspires.ftc.teamcode.Lib.Util.times
import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint
import org.firstinspires.ftc.teamcode.Lib.Util.minus
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.sin

class PathBuilder constructor(start: Waypoint = Waypoint(0.0,0.0,0.0)) {
    val path : MutableList<Waypoint> = mutableListOf(start)

    fun addPoint(waypoint: Waypoint) : PathBuilder {
        path.add(waypoint)
        return this
    }

    fun addList(waypoints: List<Waypoint>) : PathBuilder {
        path.addAll(waypoints)
        return this
    }

    fun addStraight(distance: Double, angle: Double, lookDistance: Double) : PathBuilder {
        val pt = path.last()
        val deltapt = Point(cos(angle),sin(angle)) * distance
        path.add(Waypoint(deltapt+pt, lookDistance))
        return this
    }

    fun build() : List<Waypoint> {
        return path
    }

    fun inject(points: List<Waypoint>, spacing: Double) : List<Waypoint> {
        val arr = mutableListOf<Waypoint>()
        for (i in 0 until points.size-1) {
            val a = points[i]
            val b = points[i+1]
            var vi = b - a
            val ptCount = ceil(vi.magnitude / spacing)
            vi = vi.unitVector * spacing
            for  (j in 0 until ptCount.toInt()) {
                val pt = a + (vi * j.toDouble())
                arr.add(Waypoint(pt.x, pt.y, a.heading, a.followDistance, if (j == 0) a.hook else null))
            }
        }
        arr.add(arr.last())
        return arr
    }
}