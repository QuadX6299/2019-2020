package org.firstinspires.ftc.teamcode.lib.Math

import org.firstinspires.ftc.teamcode.lib.Coords.Point
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

class Arc {

    private var h: Double = 0.toDouble()
    private var k: Double = 0.toDouble()
    private var r: Double = 0.toDouble()
    var nullUntilSet : Arc? = null

    constructor(r: Double, h : Double, k : Double) {
        this.r = r
        this.h = h
        this.k = k
    }

    //if you want to do the math do it or just go here:
    //https://www.geeksforgeeks.org/equation-of-circle-when-three-points-on-the-circle-are-given/
    //shamelessly stolen don't @me
    constructor(p0: Point, p1: Point, p2: Point) {
        val x12 = p0.x - p1.x
        val x13 = p0.x - p2.x
        val y12 = p0.y - p1.y
        val y13 = p0.y - p2.y
        val y31 = p2.y - p0.y
        val y21 = p1.y - p0.y
        val x31 = p2.x - p0.x
        val x21 = p1.x - p0.x
        val sx13 = p0.x.pow(2.0) - p2.x.pow(2.0)
        val sy13 = p0.y.pow(2.0) - p2.y.pow(2.0)
        val sx21 = p1.x.pow(2.0) - p0.x.pow(2.0)
        val sy21 = p1.y.pow(2.0) - p0.y.pow(2.0)
        val f = (sx13 * x12
                + sy13 * x12
                + sx21 * x13
                + sy21 * x13) / (2 * (y31 * x12 - y21 * x13))
        val g = (sx13 * y12
                + sy13 * y12
                + sx21 * y13
                + sy21 * y13) / (2 * (x31 * y12 - x21 * y13))
        val c = -p0.x.pow(2.0) - p0.y.pow(2.0) -
                2.0 * g * p0.x - 2.0 * f * p0.y
        val h = -g
        val k = -f
        val sqr_of_r = h * h + k * k - c
        val r = sqrt(sqr_of_r)
        this.h = h
        this.r = r
        this.k = k
    }

    val curvature : Double get() = r.pow(-1.0)
    val radius : Double get() = r

    fun arcLen(p0: Point, p1: Point) : Double {
        val theta = acos(1-(((p0 distance p1).pow(2.0))/(2*r*r)))
        return theta*r;
    }

    override fun toString(): String {
        return String.format("(x-(%.2f))^2+(y-(%.2f))^2=%.2f", h, k, r.pow(2.0))
    }
    //(x-h)^2 + (y-k)^2 = r
}
