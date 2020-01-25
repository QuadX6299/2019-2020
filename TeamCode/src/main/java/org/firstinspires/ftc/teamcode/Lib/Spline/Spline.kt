package org.firstinspires.ftc.teamcode.Lib.Spline

import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D

interface Spline {
    fun parametrize(maxDistance: Double) : List<Waypoint>

    fun compute(independent: Double) : Pose2D
}