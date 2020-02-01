package org.firstinspires.ftc.teamcode.Modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.openftc.revextensions2.ExpansionHubMotor

class Lift constructor(Op : OpMode) {
    val liftL : ExpansionHubMotor = Op.hardwareMap.get(ExpansionHubMotor::class.java, "ll")
    val liftR : ExpansionHubMotor = Op.hardwareMap.get(ExpansionHubMotor::class.java, "lr")

    init {
        liftL.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        liftR.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
    }

    fun power(power : Double) {
        liftL.power = power
        liftR.power = power
    }
}