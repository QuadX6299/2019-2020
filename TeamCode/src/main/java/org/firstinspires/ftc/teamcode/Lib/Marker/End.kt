package org.firstinspires.ftc.teamcode.Lib.Marker

class End(val tolS: Double, val tolT: Double, x: Double, y: Double, lookDistance: Double) : Waypoint(x,y, lookDist = lookDistance) {
    constructor(tolS: Double, tolT: Double, waypoint: Waypoint) : this(tolS, tolT, waypoint.x, waypoint.y, waypoint.followDistance)
}