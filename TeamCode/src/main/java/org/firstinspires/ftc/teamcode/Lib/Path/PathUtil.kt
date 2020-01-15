package org.firstinspires.ftc.teamcode.Lib.Path

import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint
import org.firstinspires.ftc.teamcode.Lib.Structs.Point
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D

typealias Line = Point

// y = mx + b form
// x = m, y = b
@Throws(InterruptedException::class)
infix fun Line.intersectionWith(other: Line) : Line {
    if (this.x == other.x || this.y == other.y) {
        throw InterruptedException("Silly goose the lines are similar")
    } else {
        val intercept = (other.y - this.y) / (this.x - other.x)
        return Line(intercept, this.valueAt(intercept))
    }
}

infix fun Point.lineTo(other: Point) : Point {
    val slope = (other.y - this.y)  / (other.x - this.x)
    val intercept = this.y  - this.x  * slope
    return Point(slope, intercept)
}

@Throws(InterruptedException::class)
infix fun Point.perpendicularProjectionOnto(other: Line) {
    val perpSlope = -1.0 / other.x
    other intersectionWith Point(perpSlope, this.y - this.x * perpSlope)
}

fun Line.valueAt(other: Double) : Double = this.x * other + this.y

fun findLookAhead(roloc: Pose2D, closest: Point, end: Point) {
    //TODO find it
    roloc  perpendicularProjectionOnto (closest lineTo end)
}