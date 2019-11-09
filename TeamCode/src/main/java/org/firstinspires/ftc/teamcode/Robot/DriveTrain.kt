package org.firstinspires.ftc.teamcode.Robot
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.util.ElapsedTime
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import org.firstinspires.ftc.teamcode.Robot.Sensors.IMU
import org.firstinspires.ftc.teamcode.lib.Constraints.DriveKinematics
import org.firstinspires.ftc.teamcode.lib.Constraints.MotionConstraint
import org.firstinspires.ftc.teamcode.lib.Constraints.PIDFCoefficients
import org.firstinspires.ftc.teamcode.lib.Coords.Point
import org.firstinspires.ftc.teamcode.lib.Coords.Position
import org.firstinspires.ftc.teamcode.lib.Coords.State
import org.firstinspires.ftc.teamcode.lib.Coords.Waypoint
import org.firstinspires.ftc.teamcode.lib.Extensions.clip
import org.firstinspires.ftc.teamcode.lib.Path.PathFollower
import java.util.concurrent.atomic.AtomicLong
import kotlin.system.measureNanoTime

import android.R.attr.right
import android.R.attr.angle
import android.R.attr.angle
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.lib.Extensions.d2r
import org.firstinspires.ftc.teamcode.lib.Extensions.fuzzyEquals
import org.firstinspires.ftc.teamcode.lib.Extensions.r2d
import kotlin.math.*


//Units = In
//object to create singleton
object DriveTrain {

    lateinit var op : OpMode
    lateinit var autoOp : LinearOpMode

    lateinit var fl: DcMotor
    lateinit var fr: DcMotor
    lateinit var bl: DcMotor
    lateinit var br: DcMotor
    lateinit var ml: DcMotor
    lateinit var mr: DcMotor

    const val encoderkD = .01
    const val encoderkP = .005

    var prevLeft = 0.0
    var prevRight = 0.0

    const val width: Double = 18.0
    const val tpr: Double = 383.6
    const val wheeldiam: Double = 3.77
    const val cpi: Double = (tpr) / (wheeldiam * PI)

    var timeB = ElapsedTime(0)


    @JvmStatic
    fun init(op: OpMode) {
        this.op = op
        fl = op.hardwareMap.get(DcMotor::class.java, "fl")
        fr = op.hardwareMap.get(DcMotor::class.java, "fr")
        ml = op.hardwareMap.get(DcMotor::class.java, "ml")
        mr = op.hardwareMap.get(DcMotor::class.java, "mr")
        bl = op.hardwareMap.get(DcMotor::class.java, "bl")
        br = op.hardwareMap.get(DcMotor::class.java, "br")
        fr.direction = DcMotorSimple.Direction.REVERSE
        br.direction = DcMotorSimple.Direction.REVERSE
        mr.direction = DcMotorSimple.Direction.REVERSE
        resetEncoders()
    }

    @JvmStatic
    fun initAuto(opLin: LinearOpMode) {
        autoOp = opLin
    }

    @JvmStatic
    fun resetEncoders() {
        fl.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        fr.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        ml.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        mr.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        bl.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        br.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER

        fl.mode = DcMotor.RunMode.RUN_USING_ENCODER
        fr.mode = DcMotor.RunMode.RUN_USING_ENCODER
        ml.mode = DcMotor.RunMode.RUN_USING_ENCODER
        mr.mode = DcMotor.RunMode.RUN_USING_ENCODER
        bl.mode = DcMotor.RunMode.RUN_USING_ENCODER
        br.mode = DcMotor.RunMode.RUN_USING_ENCODER
        prevLeft = 0.0
        prevRight = 0.0
    }

    @JvmStatic
    fun reverseMotors(backwards : Boolean) {
        if (backwards) {
            fl.direction = DcMotorSimple.Direction.REVERSE
            bl.direction = DcMotorSimple.Direction.REVERSE
            ml.direction = DcMotorSimple.Direction.REVERSE

            fr.direction = DcMotorSimple.Direction.FORWARD
            mr.direction = DcMotorSimple.Direction.FORWARD
            br.direction = DcMotorSimple.Direction.FORWARD
        } else {
            fr.direction = DcMotorSimple.Direction.REVERSE
            br.direction = DcMotorSimple.Direction.REVERSE
            mr.direction = DcMotorSimple.Direction.REVERSE

            fl.direction = DcMotorSimple.Direction.FORWARD
            ml.direction = DcMotorSimple.Direction.FORWARD
            bl.direction = DcMotorSimple.Direction.FORWARD
        }
    }

    @JvmStatic
    fun setRightPower(power: Double) {
        fr.power = (power.clip(0.0, 1.0))
        mr.power = (power.clip(0.0, 1.0))
        br.power = (power.clip(0.0, 1.0))
    }

    @JvmStatic
    fun setLeftPower(power: Double) {
        fl.power = power.clip(0.0, 1.0)
        ml.power = power.clip(0.0, 1.0)
        bl.power = power.clip(0.0, 1.0)
    }

    @JvmStatic fun turn(power:Double, right:Boolean){
        if (right) setPower(power, -power)
        else setPower(-power,power)

    }

    @JvmStatic
    fun setPower(left: Double, right: Double) {
        fl.power = left.clip(-1.0, 1.0)
        fr.power = right.clip(-1.0, 1.0)
        ml.power = if (left <= .2) {
            0.0
        } else {
            left.clip(-1.0, 1.0)
        }
        mr.power = if (right <= .2) {
            0.0
        } else {
            right.clip(-1.0, 1.0)
        }
        bl.power = left.clip(-1.0, 1.0)
        br.power = right.clip(-1.0, 1.0)
    }





    @JvmStatic
    fun stopMotors() {
        fl.power = 0.0
        fr.power = 0.0
        bl.power = 0.0
        br.power = 0.0
        ml.power = 0.0
        mr.power = 0.0
    }

    @JvmStatic
    fun setZeroPowerBehavior(behavior: DcMotor.ZeroPowerBehavior) {
        fl.zeroPowerBehavior = behavior
        fr.zeroPowerBehavior = behavior
        ml.zeroPowerBehavior = behavior
        mr.zeroPowerBehavior = behavior
        bl.zeroPowerBehavior = behavior
        br.zeroPowerBehavior = behavior
    }

    @JvmStatic fun getEncoderAvg() : Double{
        var countZeros = 0.0

        if (fl.currentPosition == 0) countZeros++

        if (fr.currentPosition == 0) countZeros++

        if (bl.currentPosition == 0) countZeros++

        if (br.currentPosition == 0) countZeros++


        if (countZeros == 4.0) return 0.0


        return (Math.abs(fl.getCurrentPosition()) +
                Math.abs(fr.getCurrentPosition()) +
                Math.abs(bl.getCurrentPosition()) +
                Math.abs(br.getCurrentPosition())) / (4.0 - countZeros)

    }

    @JvmStatic fun moveEncoder(reverse: Boolean,inDistance:Double, timeout:Double){
        val time = ElapsedTime()
        Intake.power(1.0)

        resetEncoders()
        time.reset()

        while (getEncoderAvg() < (inDistance * cpi) && time.milliseconds() < timeout && autoOp.opModeIsActive()) {
            val power = ((inDistance * cpi) - getEncoderAvg())/((inDistance * 1.0) * cpi) * (if(reverse) {
                -1.0
            } else {
                1.0
            })
            setPower(.05 + (power - .05),.05 + (power - .05))
            op.telemetry.addData("Encoder ticks:", getEncoderAvg())
            op.telemetry.update()
        }
        stopMotors()
        Intake.power(0.0)
    }

    @JvmStatic fun turnPID(kP:Double, right:Boolean, angle:Double){
        while (!IMU.heading().fuzzyEquals(angle, 3.0.d2r()) && autoOp.opModeIsActive()) {
            val error : Double = abs(angle - IMU.heading())
            op.telemetry.addData("Error", error)
            turn(max(error * kP,.1), right)
        }
        stopMotors()
    }

    @JvmStatic fun turnPID(kP:Double, right:Boolean, angle:Double, time: Double){
        timeB.reset()
        while (!IMU.heading().fuzzyEquals(angle, 3.0.d2r()) && autoOp.opModeIsActive() && timeB.milliseconds() < time) {
            val error : Double = abs(angle - IMU.heading())
            op.telemetry.addData("Error", error)
            turn(max(error * kP,.1), right)
        }
        stopMotors()
    }

    @JvmStatic fun getLeftAvg() : Double{
        var countZeros = 0.0
        val flpos = fl.currentPosition
        val blpos = bl.currentPosition

        if (flpos == 0) countZeros++

        if (blpos == 0) countZeros++


        if (countZeros == 2.0) return 0.0


        return (flpos + blpos) / (2.0 - countZeros)

    }

    @JvmStatic fun getRightAvg() : Double{
        var countZeros = 0.0
        val frpos = fl.currentPosition
        val brpos = bl.currentPosition

        if (frpos == 0) countZeros++

        if (brpos == 0) countZeros++


        if (countZeros == 2.0) return 0.0


        return (frpos + brpos) / (2.0 - countZeros)

    }


    fun getPose(time : Double) : Waypoint {
        val currentL = getLeftAvg() / cpi
        val currentR = getRightAvg() / cpi
        val distL = (currentL - prevLeft)
        val distR = (currentR - prevRight)
        val velL = distL / time
        val velR = distR / time
        val heading = IMU.heading()
        val dist = (distL + distR) / 2.0
        prevLeft = currentL
        prevRight = currentR
        return Waypoint(dist * cos(heading), dist * sin(heading), velL, velR, heading, heading)
    }

}