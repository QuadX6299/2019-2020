package Spline

import org.firstinspires.ftc.teamcode.lib.Coords.*

class ParametricEquation(private val x: Equation, private val y: Equation) {

    override fun toString(): String {
        return "(($x),($y))"
    }

    fun getDerivative(t: Double): Point {
        return Point(x.getDerivative(t), y.getDerivative(t))
    }

    fun getDDerivative(t: Double): Point {
        return Point(x.getDDerivative(t), y.getDDerivative(t))
    }

    fun getDDDerivative(t: Double): Point {
        return Point(x.getDDDerivative(t), y.getDDDerivative(t))
    }

    fun getPoint(t: Double): Point {
        return Point(x.getPoint(t).y, y.getPoint(t).y)
    }

    fun getWaypoint(t: Double): Waypoint {
        return Waypoint(x.getPoint(t).y, y.getPoint(t).y, x.getDerivative(t), y.getDerivative(t), x.getDDerivative(t), y.getDDerivative(t))
    }

    fun getCurvature(t: Double): Double {
        return (x.getDerivative(t) * y.getDDerivative(t) - y.getDerivative(t) * x.getDDerivative(t)) / Math.pow(Math.pow(x.getDerivative(t), 2.0) + Math.pow(y.getDerivative(t), 2.0), 1.5)
    }

    fun getArcLength(sample: Double, t0: Double = 0.0, t1: Double = 1.0) : Double {
        var sum = 0.0
        var i = t0
        while (i <= t1) {
            sum += Math.sqrt(Math.pow(x.getDerivative(i), 2.0) + Math.pow(y.getDerivative(i), 2.0)) * (1.0 / sample)
            i += 1.0 / sample
        }
        return sum
    }
}
