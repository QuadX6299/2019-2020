package org.firstinspires.ftc.teamcode.lib.Constraints

data class PIDFCoefficients(val kP : Double, val kI : Double, val kD : Double, val kV : Double, val kA : Double)
//Kv should be ~(1/robot.maxV)
//Ka should be ~(.0002)
//Kp is on a case to case basis but ~.01 is a good value