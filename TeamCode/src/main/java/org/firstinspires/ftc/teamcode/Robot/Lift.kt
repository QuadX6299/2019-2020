package org.firstinspires.ftc.teamcode.Robot
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.lib.Constraints.PIDFCoefficients
import kotlin.math.PI
import kotlin.time.milliseconds
import kotlin.time.seconds


object Lift {

    lateinit var liftMotor : DcMotor
    val coefficients : PIDFCoefficients = PIDFCoefficients(.005,.0,0.0,0.0,0.0)

    var gravityForward : Double = .15
    private const val tpr : Double = 537.6
    private const val spoolDiam : Double = 1.49
    const val cpi : Double = (tpr) / (spoolDiam * PI)

    var level = 0;

    //full slide extension is ~28.346 in
    //the error should be >= 2in to be full power
    //gravity forward is ~.15
    //so P = ~.2
    //D = ~.07

    private var previousValue = 0.0
    private var loopTime = ElapsedTime(ElapsedTime.SECOND_IN_NANO)


    @JvmStatic fun init(op : OpMode){
        liftMotor = op.hardwareMap.get(DcMotor::class.java, "lift")
        liftMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        liftMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        liftMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER
        loopTime.reset()
    }

    @JvmStatic fun power(power : Double){
        liftMotor.power = power
    }

    @JvmStatic fun stopLift(){
        liftMotor.power = 0.0
    }

    @JvmStatic fun goToLevel() {
        if (level > 0) {
            val ticks : Double = liftMotor.currentPosition.toDouble()
            val error = (470 * (level - 1)) - ticks
            val changeError = ((ticks) - previousValue) / loopTime.milliseconds()
            previousValue = ticks
            loopTime.reset()
            liftMotor.power = coefficients.kP * error
        } else {
            if (liftMotor.currentPosition > 30) {
                liftMotor.power = -.2
            } else {
                liftMotor.power = 0.0
            }
        }
    }

    @JvmStatic fun goOrigin() {
        if (liftMotor.currentPosition > 30) {
            liftMotor.power = -.3
        }
    }
}