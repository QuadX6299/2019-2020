package org.firstinspires.ftc.teamcode.Lib.Structs

import org.firstinspires.ftc.teamcode.Lib.Util.minus
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

// sometimes acts as a vector so uhhh gl!

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

    fun rotate(angle : Double) : Point {
        val x2 = cos(angle) * this.x - sin(angle) * this.y
        val y2 = sin(angle) * this.x + cos(angle) * this.y
        return Point(x2,y2)
    }
}