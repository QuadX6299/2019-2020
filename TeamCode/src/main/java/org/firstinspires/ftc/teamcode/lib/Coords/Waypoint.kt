package org.firstinspires.ftc.teamcode.lib.Coords

open class Waypoint//for qhs
(x: Double, y: Double, var dx: Double, var dy: Double, var ddx: Double, var ddy: Double) : Point(x, y) {
    var dddx: Double = 0.toDouble()
    var dddy: Double = 0.toDouble()

    fun unit() : Waypoint {
        val m = Point(dx, dy).magnitude
        val dm = Point(ddx, ddy).magnitude
        return Waypoint(x, y, dx/m, dy/m, ddx/dm, ddy/dm)
    }

    override fun toString(): String {
        return "($x, $y, $dx, $dy)";
    }
}
