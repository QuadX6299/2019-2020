package org.firstinspires.ftc.teamcode.Robot
import android.os.Handler
import android.os.Looper
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.util.ElapsedTime


object Intake{
    lateinit var intakeMotor : DcMotor

    @JvmStatic fun init(op : OpMode){
        intakeMotor = op.hardwareMap.get(DcMotor::class.java, "int")
    }

    @JvmStatic fun powerTime(power : Double, timeout: Double){
        val time = ElapsedTime()

        time.reset()

        while (time.milliseconds() < timeout) {
            intakeMotor.power = power
        }
    }

    @JvmStatic fun unfold() {
        power(-1.0)
        Handler(Looper.getMainLooper()).postDelayed({
            stopIntake()
        }, 1500)
    }

    @JvmStatic fun powerAsync(power: Double, time: Long) {
        power(power)
        Handler(Looper.getMainLooper()).postDelayed({
            stopIntake()
        }, time)
    }

    @JvmStatic fun power (power: Double){
        intakeMotor.power = power
    }


    @JvmStatic fun stopIntake(){
        intakeMotor.power = 0.0
    }
}