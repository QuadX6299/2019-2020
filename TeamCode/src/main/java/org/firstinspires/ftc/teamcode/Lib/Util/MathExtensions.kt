package org.firstinspires.ftc.teamcode.Lib.Util

import org.firstinspires.ftc.teamcode.Lib.Structs.Point
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D
import java.lang.Math.copySign
import kotlin.math.*


fun Double.fuzzyEquals(b: Double, tolerance: Double): Boolean {
    return abs(this - b) < tolerance
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


fun Double.limitAngle2() : Double  {
    var modifiedAngle = this % (2 * PI)
    modifiedAngle = (modifiedAngle + 2 * PI) % (2 * PI)
    return modifiedAngle
}

fun Double.wrap(): Double {
    var modify: Double = this % (Math.PI * 2)
    if (abs(modify) > Math.PI) {
        modify -= (Math.PI * 2).withSign(modify)
    }
    return modify
}

fun Pose2D.toPowers() : List<Double> {
    var fl : Double = this.x - this.heading - this.y
    var bl : Double = this.x - this.heading + this.y
    var fr : Double = this.x + this.heading + this.y
    var br : Double = this.x + this.heading - this.y
    val max : Double = max(max(fl,fr), max(bl,br))
    val min : Double = min(min(fl,fr), min(bl,br))
    val absMax = max(max, -min)
    if (absMax > 1) {
        fl /= absMax
        bl /= absMax
        fr /= absMax
        br /= absMax
    }

    return listOf(
            fl,
            fr,
            bl,
            br
    )
}





