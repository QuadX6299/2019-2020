package org.firstinspires.ftc.teamcode.Robot

import com.acmerobotics.dashboard.FtcDashboard
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.PIDCoefficients
import com.qualcomm.robotcore.hardware.PIDFCoefficients
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.lib.Coords.Position
import org.firstinspires.ftc.teamcode.Robot.Meta.Constants
import org.firstinspires.ftc.teamcode.Robot.Sensors.Clock
import org.firstinspires.ftc.teamcode.Robot.Sensors.IMU
import org.firstinspires.ftc.teamcode.lib.Constraints.TankKinematics
import org.firstinspires.ftc.teamcode.lib.Coords.Point
import org.firstinspires.ftc.teamcode.lib.Coords.State
import org.firstinspires.ftc.teamcode.lib.Extensions.d2r
import org.firstinspires.ftc.teamcode.lib.Extensions.fuzzyEquals
import org.firstinspires.ftc.teamcode.lib.Extensions.plus
import org.firstinspires.ftc.teamcode.lib.Extensions.toVector
import org.firstinspires.ftc.teamcode.lib.Path.NPathFollower
import org.firstinspires.ftc.teamcode.lib.Path.Paths
import org.firstinspires.ftc.teamcode.lib.Path.TankController
import org.openftc.revextensions2.ExpansionHubEx
import org.openftc.revextensions2.ExpansionHubMotor
import org.openftc.revextensions2.RevBulkData
import kotlin.math.*
import kotlin.system.measureTimeMillis

class NDriveTrain constructor(val Op : OpMode) {





    val fl : DcMotor
    val bl : DcMotor
    val fr : DcMotor
    val br : DcMotor

    init {


        fl = Op.hardwareMap.get(DcMotor::class.java, "fl")
        fr =  Op.hardwareMap.get(DcMotor::class.java, "fr")
        bl =  Op.hardwareMap.get(DcMotor::class.java, "bl")
        br = Op.hardwareMap.get(DcMotor::class.java, "br")

        fr.direction = DcMotorSimple.Direction.REVERSE
        br.direction = DcMotorSimple.Direction.REVERSE




        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE)
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE)
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE)
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE)

        resetEncoders()
    }




    fun Double.e2i() : Double = (Constants.WHEEL_RAD * 2.0 * PI * Constants.GEAR_RATIO * this) / 383.6

    fun Double.i2e() : Double = (this * 383.6) / (Constants.WHEEL_RAD * 2.0 * PI * Constants.GEAR_RATIO)




    fun resetEncoders() {
        fl.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        fl.mode = DcMotor.RunMode.RUN_USING_ENCODER

        fr.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        fr.mode = DcMotor.RunMode.RUN_USING_ENCODER

        bl.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        bl.mode = DcMotor.RunMode.RUN_USING_ENCODER

        br.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        br.mode = DcMotor.RunMode.RUN_USING_ENCODER

    }


    fun getEncoderAverage(): Double {
        var countZeros = 0.0

        if (fl.currentPosition == 0) {
            countZeros++
        }
        if (fr.currentPosition == 0) {
            countZeros++
        }
        if (bl.currentPosition == 0) {
            countZeros++
        }
        if (br.currentPosition == 0) {
            countZeros++
        }



        return if (countZeros == 4.0) {
            0.0
        } else (Math.abs(fl.currentPosition) +
                Math.abs(fr.currentPosition) +
                Math.abs(bl.currentPosition) +
                Math.abs(br.currentPosition)) / (4 - countZeros)

    }



    fun setPower(left:Double, right:Double) {
        fl.power = left
        fr.power = right
        bl.power = left
        br.power = right
    }

    fun stopMotors() {
        fl.power = 0.0
        fr.power = 0.0
        bl.power = 0.0
        br.power = 0.0

    }

    fun strafe(power: Double, right: Boolean) {
        if (right) {
            fl.power = -power
            fr.power = power
            bl.power = power
            br.power = -power

        } else {
            fl.power = power
            fr.power = -power
            bl.power = -power
            br.power = power

        }

    }

    fun diagonal(power: Double, right: Boolean, forward: Boolean) {
        if (right && forward) {
            fl.power = power
            br.power = power

        } else if (!right && forward) {
            fr.power = power
            bl.power = power

        } else if (right) {
            fr.power = -power
            bl.power = -power

        } else {
            fl.power = -power
            br.power = -power

        }

    }

    fun moveEncoder(power: Double, distance: Double, timeout: Double) {
        val time = ElapsedTime()

        resetEncoders()
        time.reset()

        while (getEncoderAverage() < distance.i2e() && time.seconds() < timeout && !Thread.interrupted()) {
            setPower(power,power)

        }
        stopMotors()

    }


    fun strafeEncoder(power: Double, distance: Double, right: Boolean, timeout: Double) {
        val time = ElapsedTime()

        resetEncoders()
        time.reset()

        while (getEncoderAverage() < distance.i2e() && time.seconds() < timeout && !Thread.interrupted()) {
            strafe(power, right)

        }
        stopMotors()

    }

    fun diagonalEncoder(power: Double, distance: Double, right: Boolean, forward: Boolean, timeout: Double) {
        val time = ElapsedTime()

        resetEncoders()
        time.reset()

        while (getEncoderAverage() < distance.i2e() && time.seconds() < timeout && !Thread.interrupted()) {
            diagonal(power, right, forward)

        }
        stopMotors()

    }


    fun turnPID(kP:Double, right:Boolean, angle:Double){
        while (!IMU.heading().fuzzyEquals(angle, 3.0.d2r()) && !Thread.interrupted()) {
            val error : Double = abs(angle - IMU.heading())
            Op.telemetry.addData("Error", error)
            turnPrimitive(max(error * kP,.1), right)
        }
        stopMotors()
    }

    fun turnPrimitive(power : Double, right : Boolean) {
        if (right) setPower(power, -power) else setPower(-power, power)
    }








}