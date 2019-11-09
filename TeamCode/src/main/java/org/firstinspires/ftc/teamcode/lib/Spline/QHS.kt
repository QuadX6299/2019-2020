package Spline

import org.firstinspires.ftc.teamcode.lib.Coords.*

class QHS(p0: Waypoint, p1: Waypoint) {
    private val ax = -6 * p0.x - 3 * p0.dx - 0.5 * p0.ddx + 0.5 * p1.ddx - 3 * p1.dx + 6 * p1.x
    private val bx = 15 * p0.x + 8 * p0.dx + 1.5 * p0.ddx - p1.ddx + 7 * p1.dx - 15 * p1.x
    private val cx = -10 * p0.x - 6 * p0.dx - 1.5 * p0.ddx + 0.5 * p1.ddx - 4 * p1.dx + 10 * p1.x
    private val dx = 0.5 * p0.ddx
    private val ex = p0.dx
    private val fx = p0.x
    private val ay = -6 * p0.y - 3 * p0.dy - 0.5 * p0.ddy + 0.5 * p1.ddy - 3 * p1.dy + 6 * p1.y
    private val by = 15 * p0.y + 8 * p0.dy + 1.5 * p0.ddy - p1.ddy + 7 * p1.dy - 15 * p1.y
    private val cy = -10 * p0.y - 6 * p0.dy - 1.5 * p0.ddy + 0.5 * p1.ddy - 4 * p1.dy + 10 * p1.y
    private val dy = 0.5 * p0.ddy
    private val ey = p0.dy
    private val fy = p0.y

    private val yEquation: Equation
        get() = Equation(ay, by, cy, dy, ey, fy)

    private val xEquation: Equation
        get() = Equation(ax, bx, cx, dx, ex, fx)

    val parametric: ParametricEquation
        get() = ParametricEquation(xEquation, yEquation)

}
