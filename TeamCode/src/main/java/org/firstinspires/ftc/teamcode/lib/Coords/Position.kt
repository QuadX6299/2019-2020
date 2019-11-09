package org.firstinspires.ftc.teamcode.lib.Coords

class Position constructor(x : Double, y : Double, var heading : Double) : Point(x,y) {
    override fun toString(): String {
        return "($x,$y,$heading)"
    }
}