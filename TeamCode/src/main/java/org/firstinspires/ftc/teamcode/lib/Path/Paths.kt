package org.firstinspires.ftc.teamcode.lib.Path

import android.graphics.Path
import org.firstinspires.ftc.teamcode.lib.Constraints.MotionConstraint
import org.firstinspires.ftc.teamcode.lib.Coords.State

object Paths {
    @JvmStatic
    val straightRed = PathGenerator.generate(mutableListOf(State(0.0,0.0), State(28.0, 1.0)), 2.0, .9,.1,.5, MotionConstraint(30.0, 7.0, 1.0))

    //Use look dist 10
    @JvmStatic
    val leftRed = PathGenerator.generate(mutableListOf(State(0.0,0.0), State(25.5, 4.0)), 2.0, .9,.1,.5, MotionConstraint(30.0, 7.0, 1.0))

    @JvmStatic
    val rightRed = PathGenerator.generate(mutableListOf(State(0.0,0.0), State(34.0,-4.0)), 2.0,.9,.1,0.5,MotionConstraint(30.0,7.0,1.0))

    @JvmStatic
    val straightBlue = PathGenerator.generate(mutableListOf(State(0.0,0.0), State(28.0, -1.0)), 2.0, .9,.1,.5, MotionConstraint(30.0, 7.0, 1.0))

    //Use look dist 10
    @JvmStatic
    val leftBlue = PathGenerator.generate(mutableListOf(State(0.0,0.0), State(25.5, 4.0)), 2.0, .9,.1,.5, MotionConstraint(30.0, 7.0, 1.0))

    @JvmStatic
    val rightBlue = PathGenerator.generate(mutableListOf(State(0.0,0.0), State(36.0,-6.0)), 2.0,.9,.1,0.5,MotionConstraint(30.0,7.0,1.0))

    @JvmStatic
    val backUpBlueLeft = PathGenerator.generate(mutableListOf(State(0.0,0.0), State(40.0,0.0), State(60.0,-2.0), State(70.0,15.0),State (75.0,25.0)), 6.0,.9,.1,1.0,MotionConstraint(30.0,5.0,1.0))

    @JvmStatic
    val backUpBlueCenter = PathGenerator.generate(mutableListOf(State(0.0,0.0), State(50.0,0.0), State(60.0,0.0), State(70.0,20.0),State (75.0,25.0)), 6.0,.9,.1,1.0,MotionConstraint(30.0,5.0,1.0))

    @JvmStatic
    val backUpBlueRight = PathGenerator.generate(mutableListOf(State(0.0,0.0), State(20.0,0.0),State(40.0,0.0), State(50.0,0.0),State(60.0,0.0), State(65.0,2.0), State(67.0,3.0), State(70.0,4.0),State(72.0,5.0), State(75.0,12.0), State(77.0,20.0)), 2.0, .9,.1,.5, MotionConstraint(30.0, 7.0, 1.0))

    @JvmStatic
    val blueStraight = PathGenerator.generate(mutableListOf(State(0.0,0.0), State(65.0,0.0)), 2.0, .9,.1,.5, MotionConstraint(30.0, 7.0, 1.0))

    @JvmStatic
    val redStraight = PathGenerator.generate(mutableListOf(State(0.0,0.0), State(65.0,0.0)), 2.0, .9,.1,.5, MotionConstraint(30.0, 7.0, 1.0))

    @JvmStatic
    val backUpRedRight = PathGenerator.generate(mutableListOf(State(0.0,0.0), State(40.0,0.0), State(60.0,2.0), State(70.0,-15.0),State (75.0,-25.0)), 6.0,.9,.1,1.0,MotionConstraint(30.0,5.0,1.0))

    @JvmStatic
    val backUpRedCenter = PathGenerator.generate(mutableListOf(State(0.0,0.0), State(50.0,0.0), State(60.0,0.0), State(70.0,-20.0),State (75.0,-25.0)), 6.0,.9,.1,1.0,MotionConstraint(30.0,5.0,1.0))

    @JvmStatic
    val backUpRedLeft = PathGenerator.generate(mutableListOf(State(0.0,0.0), State(50.0,0.0), State(60.0,2.0), State(75.0,-20.0),State (80.0,-25.0)), 6.0,.9,.1,1.0,MotionConstraint(30.0,5.0,1.0))


    @JvmStatic
    val backUpBlock = PathGenerator.generate(mutableListOf(State(0.0,0.0), State(5.0,0.0)),2.0,.9,.1,1.0, MotionConstraint(20.0,5.0,1.0))


    //Old paths
//    @JvmStatic
//    val backUpRedLeft = PathGenerator.generate(mutableListOf(State(0.0,0.0), State(40.0,-18.0), State(70.0,-18.0), State(90.0,-10.0),State (95.0,15.0)), 2.0,0.9,0.1,0.5, MotionConstraint(30.0,5.0,1.0))
//
//    @JvmStatic
//    val backUpRedRight = PathGenerator.generate(mutableListOf(State(0.0,0.0), State(40.0,-18.0), State(70.0,-18.0), State(85.0,-12.0),State (90.0,10.0)), 2.0,0.9,0.1,0.5, MotionConstraint(30.0,7.0,2.0))
//
//    @JvmStatic
//    val backUpBlueLeft = PathGenerator.generate(mutableListOf(State(0.0,0.0), State(40.0,18.0), State(70.0,18.0), State(90.0,10.0),State (95.0,-15.0)), 2.0,0.9,0.1,0.5, MotionConstraint(30.0,5.0,1.0))
//
//    @JvmStatic
//    val backUpBlueRight = PathGenerator.generate(mutableListOf(State(0.0,0.0), State(40.0,18.0), State(70.0,18.0), State(85.0,12.0),State (90.0,-10.0)), 2.0,0.9,0.1,0.5, MotionConstraint(30.0,7.0,2.0))
}