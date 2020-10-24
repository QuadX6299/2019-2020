package org.firstinspires.ftc.teamcode.Modules

import android.os.Handler
import android.os.Looper
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.ElapsedTime
import org.openftc.revextensions2.ExpansionHubMotor

class Intake constructor(op : OpMode) {
    lateinit var intakeMotorLeft : ExpansionHubMotor
    lateinit var intakeMotorRight : ExpansionHubMotor

    lateinit var transition : ExpansionHubMotor

    init {
        intakeMotorLeft = op.hardwareMap.get(ExpansionHubMotor::class.java, "il")
        intakeMotorRight = op.hardwareMap.get(ExpansionHubMotor::class.java, "ir")
        transition = op.hardwareMap.get(ExpansionHubMotor::class.java, "transition")
        intakeMotorLeft.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        intakeMotorRight.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        intakeMotorLeft.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        intakeMotorRight.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
    }






    fun power (power: Float){
        intakeMotorLeft.power = power.toDouble()
        intakeMotorRight.power = power.toDouble()
        transition.power = (-power).toDouble()
    }


    fun stopIntake(){
        intakeMotorLeft.power = 0.0
        intakeMotorRight.power = 0.0
    }
}