package org.firstinspires.ftc.teamcode.Lib.Util

import org.firstinspires.ftc.teamcode.Lib.Structs.Point
import java.lang.Math.copySign
import kotlin.math.*

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

fun Double.wrap(): Double {
    var remain = this % (PI * 2)
    if (abs(remain) > PI) {
        remain -= copySign(PI * 2, remain)
        remain -= if (remain < 0) { -1 } else { 1 } * PI * 2
    }
    return remain
}

fun Double.toVector(angle : Double = 0.0) : Point {
    val x2 = this * cos(angle)
    val y2 = this * sin(angle)
    return Point(x2, y2)
}