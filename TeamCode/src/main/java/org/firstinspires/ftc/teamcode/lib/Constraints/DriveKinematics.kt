package org.firstinspires.ftc.teamcode.lib.Constraints

interface DriveKinematics {
    fun getTargetVelocities(v : Double, k : Double) : List<Double>
}