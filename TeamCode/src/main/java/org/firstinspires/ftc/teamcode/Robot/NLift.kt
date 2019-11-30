package org.firstinspires.ftc.teamcode.Robot

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import org.openftc.revextensions2.ExpansionHubMotor

class NLift constructor(Op : OpMode) {
    lateinit var lift : ExpansionHubMotor

    init {
        Op.hardwareMap.get(ExpansionHubMotor::class.java, "lift")
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