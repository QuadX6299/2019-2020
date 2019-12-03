package org.firstinspires.ftc.teamcode.Robot.Meta

import com.qualcomm.robotcore.hardware.PIDCoefficients
import com.qualcomm.robotcore.hardware.PIDFCoefficients
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType

object Constants {
    val DT_PID : PIDCoefficients = PIDCoefficients(35.0,0.0,30.0)
    val MOTOR_CONFIG : MotorConfigurationType = MotorConfigurationType.getMotorType(GoBILDA435::class.java)
    const val WHEEL_RAD : Double = 2.0
    const val GEAR_RATIO : Double = 1.0
    const val DT_WIDTH : Double = 19.0

}