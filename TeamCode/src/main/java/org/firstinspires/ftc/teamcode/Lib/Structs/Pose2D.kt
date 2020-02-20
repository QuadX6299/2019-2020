package org.firstinspires.ftc.teamcode.Lib.Structs

import org.firstinspires.ftc.teamcode.Lib.Util.r2d
import kotlin.math.cos
import kotlin.math.sin

class Pose2D (x: Double = 0.0, y: Double = 0.0, var heading: Double = 0.0) : Point(x, y) {
    constructor(p0 : Point, heading: Double) : this(p0.x, p0.y, heading)

    override fun toString() : String {
        return "($x, $y, $heading)"
    }

    fun easyToRead() : String {
        return "\nX: ${x.toInt()}\nY: ${y.toInt()}\nT: ${heading.r2d().toInt()}"
    }

    fun headingVector() : Point {
        return Point(cos(heading), sin(heading))
    }
}