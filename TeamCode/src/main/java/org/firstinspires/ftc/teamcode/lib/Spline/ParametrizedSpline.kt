package Spline

import org.firstinspires.ftc.teamcode.lib.Coords.Waypoint
import org.firstinspires.ftc.teamcode.lib.Math.Arc

class ParametrizedSpline(line: ParametricEquation, private val maxCurvature : Double =  .01, private val maxArcLength : Double = .25) {
    val parameterized : MutableList<Arc> = mutableListOf()
    val time : MutableList<Double> = mutableListOf(0.0)
    val position : MutableList<Waypoint> = mutableListOf(line.getWaypoint(0.0))

    init {
        arc(s = line, arcs = parameterized, time = time, position = position)
    }

    private fun arc(s: ParametricEquation, t0: Double = 0.0, t1: Double = 1.0, arcs: MutableList<Arc>, time : MutableList<Double>, position : MutableList<Waypoint>) {
        val a = Arc(s.getPoint(t0), s.getPoint((t1 + t0) / 2), s.getPoint(t1))
        //use arc length of constructed circle between points Q(t0) and Q(t1) to approximate arclength of segment without computationally heavy math
        if (a.arcLen(s.getPoint(t0), s.getPoint(t1)) > maxArcLength || Math.abs(s.getCurvature(t0) - s.getCurvature(t1)) > maxCurvature) {
            arc(s, t0, (t0 + t1) / 2.0, arcs, time, position)
            arc(s, (t0 + t1) / 2.0, t1, arcs, time, position)
        } else {
            arcs.add(a)
            time.add(t1)
            position.add(s.getWaypoint(t1))
        }
    }
}
