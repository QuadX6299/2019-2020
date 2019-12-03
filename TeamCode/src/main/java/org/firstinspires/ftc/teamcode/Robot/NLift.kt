package org.firstinspires.ftc.teamcode.Robot

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import org.openftc.revextensions2.ExpansionHubMotor

class NLift constructor(Op : OpMode) {
    val lift : ExpansionHubMotor = Op.hardwareMap.get(ExpansionHubMotor::class.java, "lift")

    init {
        lift.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        lift.mode = DcMotor.RunMode.RUN_USING_ENCODER
    }

    fun power(power : Double) {
        lift.power = power
    }

    fun stopLift() {
        lift.power = 0.0
    }
}