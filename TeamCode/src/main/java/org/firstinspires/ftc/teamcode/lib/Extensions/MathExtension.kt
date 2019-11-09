package org.firstinspires.ftc.teamcode.lib.Extensions

import org.firstinspires.ftc.teamcode.lib.Coords.Point
import org.firstinspires.ftc.teamcode.lib.Coords.Waypoint
import Spline.ParametricEquation
import Spline.QHS
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun Double.fuzzyEquals(b: Double, tolerance: Double): Boolean {
    return abs(this - b) < tolerance
}

//TODO create a function that soothes out an array of double so that the maximum delta between two
//indices is drop
fun MutableList<Double>.smooth(drop : Double) {
    for (i in this) {
        println(i)
    }
}

infix fun Waypoint.interpolate(p1 : Waypoint) : ParametricEquation = QHS(this, p1).parametric

infix operator fun Point.minus(other : Point) : Point = Point(this.x-other.x, this.y-other.y)

infix operator fun Point.times(other : Double) : Point = Point(this.x * other, this.y * other)

infix operator fun Point.div(other : Double) : Point = Point(this.x/other, this.y/other)

infix operator fun Point.plus(other : Point) : Point = Point(this.x + other.x, this.y + other.y)

infix fun Point.dot(other: Point) : Double = (this.x*other.x) + (this.y*other.y)

infix fun Point.scalarProjection(other: Point) : Double = (this dot other) / other.magnitude

infix fun Point.vectorProjection(other: Point) : Point = other * ((this scalarProjection other) / other.magnitude)

fun Point.convertBasis(e0 : Point, e1 : Point) : Point = Point((this scalarProjection e0)/e0.magnitude, (this scalarProjection  e1)/e1.magnitude)


fun Double.r2d() : Double = this * (180/ PI)

fun Double.d2r() : Double = this * (PI/180)

fun Double.clip(vi : Double = 0.0, vf : Double = 0.0) = max(min(vf, this), vi)

fun Double.limitAngle() : Double = if (this < 0) {
    (PI - abs(this)) + PI
} else {
    this
}