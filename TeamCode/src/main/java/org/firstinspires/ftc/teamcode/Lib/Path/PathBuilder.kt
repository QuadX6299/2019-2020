package org.firstinspires.ftc.teamcode.Lib.Path

import org.firstinspires.ftc.teamcode.Lib.Marker.End
import org.firstinspires.ftc.teamcode.Lib.Structs.Point
import org.firstinspires.ftc.teamcode.Lib.Util.plus
import org.firstinspires.ftc.teamcode.Lib.Util.times
import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint
import kotlin.math.cos
import kotlin.math.sin

class PathBuilder constructor(start: Waypoint) {
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
        val last = path.last()
        val fin = End(1.0,1.0, last)
        path[path.size - 1] = fin
        return path
    }
}