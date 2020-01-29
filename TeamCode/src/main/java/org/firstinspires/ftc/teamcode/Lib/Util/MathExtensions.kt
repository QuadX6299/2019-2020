package org.firstinspires.ftc.teamcode.Lib.Util

import org.firstinspires.ftc.teamcode.Lib.Structs.Point
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D
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

infix operator fun Pose2D.minus(other: Pose2D) : Pose2D = Pose2D(this.x - other.x, this.y - other.y, this.heading - other.heading)

infix operator fun Pose2D.div(scalar: Double) : Pose2D = Pose2D(this.x / scalar, this.y / scalar, this.heading / scalar)

infix operator fun Pose2D.div(other: Pose2D) : Pose2D = Pose2D(this.x / other.x, this.y / other.y, this.heading / other.heading)

infix operator fun Point.times(other : Double) : Point = Point(this.x * other, this.y * other)

infix operator fun Pose2D.times(other: Pose2D) : Pose2D = Pose2D(this.x * other.x, this.y * other.y, this.heading * other.heading)

infix operator fun Point.div(other : Double) : Point = Point(this.x/other, this.y/other)

infix operator fun Point.plus(other : Point) : Point = Point(this.x + other.x, this.y + other.y)

infix operator fun Pose2D.times(other: Double) : Pose2D = Pose2D(this.x * other, this.y * other, this.heading * other)

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

fun Double.limitAngle2() : Double  {
    var modifiedAngle = this % (2 * PI)
    modifiedAngle = (modifiedAngle + 2 * PI) % (2 * PI)
    return modifiedAngle
}

fun Double.wrap(): Double {
    var negTauToTau: Double = this % (Math.PI * 2)

    if (abs(negTauToTau) > Math.PI) {
        negTauToTau -= (Math.PI * 2).withSign(negTauToTau)
    }
    return negTauToTau
}

fun Double.toVector(angle : Double = 0.0) : Point {
    val x2 = this * cos(angle)
    val y2 = this * sin(angle)
    return Point(x2, y2)
}

typealias Line = Point

// y = mx + b form
// x = m, y = b


@Throws(InterruptedException::class)
infix fun Line.intersectionWith(other: Line): Line {
    if (this.x == other.x || this.y == other.y) {
        throw InterruptedException("Silly goose the lines are similar")
    } else {
        val intercept = (other.y - this.y) / (this.x - other.x)
        return Line(intercept, this.valueAt(intercept))
    }
}

infix fun Point.lineTo(other: Point): Point {
    val slope = (other.y - this.y) / (other.x - this.x)
    val intercept = this.y - this.x * slope
    return Point(slope, intercept)
}

@Throws(InterruptedException::class)
infix fun Point.perpendicularProjectionOnto(other: Line) {
    val perpSlope = -1.0 / other.x
    other intersectionWith Point(perpSlope, this.y - this.x * perpSlope)
}

fun Line.valueAt(other: Double): Double = this.x * other + this.y

fun findLookAhead(roloc: Pose2D, closest: Point, end: Point) {
    //TODO find it
    roloc perpendicularProjectionOnto (closest lineTo end)
}
