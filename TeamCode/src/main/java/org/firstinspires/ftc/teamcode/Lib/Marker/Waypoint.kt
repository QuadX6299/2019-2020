package org.firstinspires.ftc.teamcode.Lib.Marker

import org.firstinspires.ftc.teamcode.Lib.Hooks.Routine
import org.firstinspires.ftc.teamcode.Lib.Structs.Point



open class Waypoint constructor(x: Double, y: Double, var heading : Double = Double.NaN, val followDistance: Double, val hook: Routine? = null) : Point(x,y) {
    constructor(point: Point, followDistance: Double) : this(point.x, point.y, followDistance = followDistance)
    constructor(x: Double, y: Double, lookDist: Double) : this(x, y, followDistance = lookDist)
}