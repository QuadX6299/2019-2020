package org.firstinspires.ftc.teamcode.lib.Constraints

data class MotionConstraint(val maxV : Double = 65.0, val maxA : Double = 20.0, val k : Double)
//K should be a value btw 1-5 and limits speed in proportion to curvature
//all units in in/sec or in/sec^2