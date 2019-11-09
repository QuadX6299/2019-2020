package org.firstinspires.ftc.teamcode.lib.Coords

import org.firstinspires.ftc.teamcode.lib.Extensions.minus
import kotlin.math.pow
import kotlin.math.sqrt

open class Point(var x: Double, var y: Double) {
    infix fun distance(other: Point) : Double {
        val pt = this - other
        return sqrt(pt.x.pow(2.0)+pt.y.pow(2.0))
    }
    val unitVector : Point get() = Point(x/magnitude, y/magnitude)
    val magnitude : Double get() = sqrt(x.pow(2.0) + y.pow(2.0))

    override fun toString(): String {
        return "($x,$y)"
    }
}
