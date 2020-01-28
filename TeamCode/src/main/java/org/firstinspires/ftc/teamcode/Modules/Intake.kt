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

    init {
        intakeMotorLeft = op.hardwareMap.get(ExpansionHubMotor::class.java, "il")
        intakeMotorRight = op.hardwareMap.get(ExpansionHubMotor::class.java, "ir")
        intakeMotorLeft.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        intakeMotorRight.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        intakeMotorLeft.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        intakeMotorRight.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
    }

    fun powerTime(power : Double, timeout: Double){
        val time = ElapsedTime()

        time.reset()

        while (time.milliseconds() < timeout) {
            intakeMotorLeft.power = power
            intakeMotorRight.power = -power
        }
    }

    fun unfold() {
        power(-1.0)
        Handler(Looper.getMainLooper()).postDelayed({
            power(1.0)
            Handler(Looper.getMainLooper()).postDelayed({
                stopIntake()
            }, 500)
        }, 1000)


    }

    fun powerAsync(power: Double, time: Long) {
        power(power)
        Handler(Looper.getMainLooper()).postDelayed({
            stopIntake()
        }, time)
    }

    fun power (power: Double){
        intakeMotorLeft.power = power
        intakeMotorRight.power = -power
    }


    fun stopIntake(){
        intakeMotorLeft.power = 0.0
        intakeMotorRight.power = 0.0
    }
}