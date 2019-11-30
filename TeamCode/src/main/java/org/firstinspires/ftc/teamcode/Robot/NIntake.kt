package org.firstinspires.ftc.teamcode.Robot

import android.graphics.Path
import android.os.Handler
import android.os.Looper
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.ElapsedTime
import org.openftc.revextensions2.ExpansionHubMotor

class NIntake constructor(op : OpMode) {
    lateinit var intakeMotor : ExpansionHubMotor

    init {
        intakeMotor = op.hardwareMap.get(ExpansionHubMotor::class.java, "int")
        intakeMotor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        intakeMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
    }

    fun powerTime(power : Double, timeout: Double){
        val time = ElapsedTime()

        time.reset()

        while (time.milliseconds() < timeout) {
            intakeMotor.power = power
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
        intakeMotor.power = power
    }


    fun stopIntake(){
        intakeMotor.power = 0.0
    }
}