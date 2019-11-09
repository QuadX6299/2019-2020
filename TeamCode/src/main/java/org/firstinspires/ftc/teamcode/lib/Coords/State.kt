package org.firstinspires.ftc.teamcode.lib.Coords

class State constructor(x : Double, y : Double, var velocity : Double = 0.0, var distance : Double = 0.0, var curvature : Double = 0.0, var next : Point = Point(0.0,0.0)) : Point(x,y) {
    val location : Point
        get() = Point(x,y)

    override fun toString() : String {
        return "($x,$y)"
    }
}
