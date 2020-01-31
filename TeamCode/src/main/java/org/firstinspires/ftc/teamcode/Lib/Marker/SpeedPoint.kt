package org.firstinspires.ftc.teamcode.Lib.Marker

import org.firstinspires.ftc.teamcode.Lib.Hooks.Routine
import org.firstinspires.ftc.teamcode.Lib.Structs.Point

open class SpeedPoint constructor(x: Double, y: Double, heading : Double = Double.NaN, followDistance: Double, hook: Routine? = null, val speed: Double = Double.NaN) : Waypoint(x,y,heading, followDistance, hook){
    constructor(point: Point, followDistance: Double, speed: Double = Double.NaN) : this(point.x, point.y, followDistance = followDistance)
    constructor(x: Double, y: Double, lookDist: Double, speed: Double = Double.NaN) : this(x, y, followDistance = lookDist)
}