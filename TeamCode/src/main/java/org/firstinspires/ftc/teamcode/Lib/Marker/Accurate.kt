package org.firstinspires.ftc.teamcode.Lib.Marker

import org.firstinspires.ftc.teamcode.Lib.Hooks.Routine
import org.firstinspires.ftc.teamcode.Lib.Hooks.Static
import org.firstinspires.ftc.teamcode.Lib.Structs.Point

open class Accurate constructor(x: Double, y: Double, heading : Double = Double.NaN, followDistance: Double, hook: Static? = null) : Waypoint(x,y,heading, followDistance, hook){
    constructor(point: Point, followDistance: Double) : this(point.x, point.y, followDistance = followDistance)
    constructor(x: Double, y: Double, lookDist: Double) : this(x, y, followDistance = lookDist)
}