package org.firstinspires.ftc.teamcode.lib.Constraints

class TankKinematics(val w : Double) : DriveKinematics {
    override fun getTargetVelocities(v: Double, k: Double): List<Double> {

        val left : Double = v * (2.0 + k*w)/2.0
        val right : Double = v * (2.0 - k*w)/2.0
        return listOf(left, right, left, right)
    }
}