package org.firstinspires.ftc.teamcode.Lib.Marker

import org.firstinspires.ftc.teamcode.Lib.Hooks.Routine

class End(val tolS: Double, val tolT: Double, x: Double, y: Double, heading: Double, lookDistance: Double, hook: Routine? = null) : Waypoint(x,y, heading, lookDistance, hook) {
    constructor(tolS: Double, tolT: Double, waypoint: Waypoint) : this(tolS, tolT, waypoint.x, waypoint.y, waypoint.heading,waypoint.followDistance, waypoint.hook)
}