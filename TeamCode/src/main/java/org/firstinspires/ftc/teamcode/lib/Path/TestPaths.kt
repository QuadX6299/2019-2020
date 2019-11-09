package org.firstinspires.ftc.teamcode.lib.Path

import org.firstinspires.ftc.teamcode.lib.Constraints.MotionConstraint
import org.firstinspires.ftc.teamcode.lib.Coords.Point
import org.firstinspires.ftc.teamcode.lib.Coords.State

object TestPaths {
    @JvmStatic
    val test = mutableListOf<Point>(Point(0.0,0.0), Point(-1.0, 20.0), Point(20.0, 20.0), Point(21.0, 40.0))
    @JvmStatic
    val straightLine = PathGenerator.generate(mutableListOf(State(0.0,0.0), State(40.0,-10.0), State(80.0, -30.0)), 2.0, .9,.1,.5, MotionConstraint(30.0, 5.0, 1.0))
    @JvmStatic
    val backUpStraight = straightLine
    @JvmStatic
    val backUpToPlate = PathGenerator.generate(mutableListOf(State(0.0,0.0), State(40.0,10.0), State(80.0, 0.0), State(120.0,0.0)), 2.0, .9,.1,.5, MotionConstraint(30.0, 5.0, 1.0))

    @JvmStatic
    val lowres = PathGenerator.generate(mutableListOf(State(0.0,0.0), State(30.0,20.0), State(60.0,-20.0)), 2.0, .9,.1,.5, MotionConstraint(40.0, 10.0, 1.0))

}