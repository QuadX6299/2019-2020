package org.firstinspires.ftc.teamcode.lib.Path

import org.firstinspires.ftc.teamcode.lib.Constraints.MotionConstraint
import org.firstinspires.ftc.teamcode.lib.Coords.*
import org.firstinspires.ftc.teamcode.lib.Extensions.minus
import org.firstinspires.ftc.teamcode.lib.Extensions.plus
import org.firstinspires.ftc.teamcode.lib.Extensions.times
import org.firstinspires.ftc.teamcode.lib.Math.Arc
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

object PathGenerator {


    fun generate(points: MutableList<State>, spacing: Double, constraints : MotionConstraint) : MutableList<State> {
        val out : MutableList<State> = mutableListOf()
        inject(out, points, spacing)
        label(out, constraints)
        return out
    }

    fun generate(points: MutableList<State>, spacing: Double, a : Double, b : Double, tol : Double, constraints : MotionConstraint) : MutableList<State> {
        val out : MutableList<State> = mutableListOf()
        inject(out, points, spacing)
        val smooth = smooth(out, a, b, tol)
        label(smooth, constraints)
        return smooth
    }

    fun inject(arr : MutableList<State>, points : MutableList<State>, spacing: Double) {
        for (i in 0 until points.size-1) {
            val a = points[i].location
            val b = points[i+1].location
            var vi = b - a
            val pointCount = Math.ceil(vi.magnitude/spacing)
            vi = vi.unitVector * spacing
            for (j in 0 until pointCount.toInt()) {
                val pt = a + (vi * j.toDouble())
                arr.add(State(pt.x, pt.y))
            }
        }
        arr.add(points.last())
    }

    fun smooth(out : MutableList<State>,a : Double, b : Double, tol : Double) : MutableList<State> {
        val new : MutableList<State> = out.toMutableList()
        var c = tol

        while (c >= tol) {
            c = 0.0
            for (i in 1 until out.size-1) {
                val auxX = new[i].x
                new[i].x += a * (out[i].x - new[i].x) + b * (new[i-1].x + new[i+1].x - (2.0 * new[i].x))
                c += abs(auxX - new[i].x)
                val auxY = new[i].y
                new[i].y += a * (out[i].y - new[i].y) + b * (new[i-1].y + new[i+1].y - (2.0 * new[i].y))
                c += abs(auxY - new[i].y)
            }
        }
        return new
    }

    fun label(out : MutableList<State>,const : MotionConstraint) {
        var sum = 0.0
        out.forEachIndexed { index, state ->
            state.next = if (index < out.size-1) {
                out[index+1]
            } else {
                state
            }
            state.curvature = if (index > 0 && index < out.size-1) {
                val c = getCurvature(out[index-1], state, out[index+1])
                if (c.isNaN()) {
                    0.0
                } else {
                    c
                }
            } else {
                0.0
            }
            state.distance = sum
            sum += if (index < out.size-1) {
                state distance out[index+1]
            } else {
                0.0
            }

            state.velocity = min(const.maxV,if (state.curvature.isNaN()) {
                const.maxV
            } else {
                const.k / state.curvature
            })
        }
        out[out.size-1].velocity = 0.0
        for (i in out.size-2 downTo 0) {
            val distance = out[i] distance out[i+1]
            val b = sqrt(out[i + 1].velocity.pow(2) + (2.0 * const.maxA * distance))
            out[i].velocity = min(out[i].velocity, b)
        }
    }

    private fun getCurvature(p0: Point, p1: Point, p2: Point) : Double {
        if (p0.x == p1.x) {
            p1.x += .0001
        }
        val k1 = .5 * (p0.x.pow(2) + p0.y.pow(2) - p1.x.pow(2) -p1.y.pow(2))/ (p0.x - p1.x)
        val k2= (p0.y - p1.y) / (p0.x - p1.x)
        val b= 0.5 * (p1.x.pow(2)- 2 * p1.x  * k1 + p1.y.pow(2) - p2.x.pow(2) + 2 * p2.x * k1 - p2.y.pow(2)) / (p2.x * k2 - p2.y + p1.y - p1.x * k2)
        val a = k1-k2 * b
        val r = sqrt( (p0.x - a).pow(2) + (p0.y - b).pow(2))
        return 1.0/r
    }
}