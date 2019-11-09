package Spline

import org.firstinspires.ftc.teamcode.lib.Coords.Point

class Equation constructor(private val a: Double, private val b: Double, private val c: Double, private val d: Double, private val e: Double, private val f: Double) {

    override fun toString(): String {
        return a.toString() + "t^5+" + b + "t^4+" + c + "t^3+" + d + "t^2+" + e + "t+" + f
    }

    fun getDerivative(t: Double): Double {
        return 5.0 * t * t * t * t * a + 4.0 * t * t * t * b + 3.0 * t * t * c + 2.0 * t * d + e
    }

    fun getDDerivative(t: Double): Double {
        return 20.0 * t * t * t * a + 12.0 * t * t * b + 6.0 * t * c + 2 * d
    }

    fun getDDDerivative(t: Double): Double {
        return  60.0 * t * t * a + 24.0 * t * b + 6 * c
    }

    fun getPoint(t: Double): Point {
        val y = a * t * t * t * t * t + b * t * t * t * t + c * t * t * t + d * t * t + e * t + f
        return Point(t, y)
    }
}
