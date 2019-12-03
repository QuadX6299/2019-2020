package org.firstinspires.ftc.teamcode.Robot

import android.os.Handler
import android.os.Looper
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.Robot.Sensors.IMU
import org.firstinspires.ftc.teamcode.lib.Coords.State
import org.firstinspires.ftc.teamcode.lib.Extensions.clip
import org.firstinspires.ftc.teamcode.lib.Path.NPathBuilder
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sign

class NRobot constructor(val opMode: OpMode) {

    var flip = 1.0
    
    var g1prev : Gamepad = Gamepad()
    var g2prev : Gamepad = Gamepad()

    companion object Modules {
        lateinit var DriveTrain : NDriveTrain
        lateinit var Grabber : NGrabber
        lateinit var Intake : NIntake
        lateinit var Lift : NLift
        lateinit var FoundationHook : NFoundationHook
        lateinit var OSAsync : Handler
        lateinit var Generator : NPathBuilder

        fun init(Op : OpMode) {
            DriveTrain = NDriveTrain(Op)
            Grabber = NGrabber(Op)
            Intake = NIntake(Op)
            Lift = NLift(Op)
            FoundationHook = NFoundationHook(Op)
            OSAsync = Handler(Looper.getMainLooper())
            Generator = NPathBuilder()
            IMU.init(Op)
        }
    }

    init {
        flip = 1.0
        Modules.init(opMode)
    }

    fun reset() {
        Modules.init(opMode)
    }

    fun controls() {
        g1()
        g2()
        intakeControls()
        liftControls()
        sixArcadeArc()
    }

    fun g2() {
        if (opMode.gamepad2.dpad_up && g2prev.dpad_up != opMode.gamepad2.dpad_up) {
            Grabber.toggleHorn()
        } else if (opMode.gamepad2.dpad_down && g2prev.dpad_down != opMode.gamepad2.dpad_down) {
            Grabber.toggleGrabber()
            
        } else if (opMode.gamepad2.y && g2prev.y != opMode.gamepad2.y) {
            Grabber.setAssemblyPosition(NGrabber.POSITIONS.HORNDOWN)
            OSAsync.postDelayed({
                Grabber.setAssemblyPosition(NGrabber.POSITIONS.CLAMPDOWN)
            }, 1000)
            
        } else if (opMode.gamepad2.b && g2prev.b != opMode.gamepad2.b) {
            Grabber.setAssemblyPosition(NGrabber.POSITIONS.TRANSITION)
            OSAsync.postDelayed({
                Grabber.setAssemblyPosition(NGrabber.POSITIONS.VERTICALDEPO)
            },750)
            
        } else if (opMode.gamepad2.a && g2prev.a != opMode.gamepad2.a) {
            Grabber.setAssemblyPosition(NGrabber.POSITIONS.HORIZONTALDEPO)
            
        } else if (opMode.gamepad2.x && g2prev.x != opMode.gamepad2.x) {
            Grabber.setAssemblyPosition(NGrabber.POSITIONS.VERTICALDEPO)
            Grabber.setAssemblyPosition(NGrabber.POSITIONS.DROP)
            OSAsync.postDelayed({
                Grabber.setAssemblyPosition(NGrabber.POSITIONS.COLLECTION)
            }, 500)
        }
        g2prev.copy(opMode.gamepad2)
    }

    fun g1() {
        if (opMode.gamepad1.dpad_down && g1prev.dpad_down != opMode.gamepad1.dpad_down) {
            Grabber.toggleGrabber()
            Grabber.setAssemblyPosition(NGrabber.POSITIONS.HORNDOWN)
        } else if (opMode.gamepad1.a && g1prev.a != opMode.gamepad1.a) {
            flip *= -1.0
        } else if (opMode.gamepad1.dpad_up && g1prev.dpad_up != opMode.gamepad1.dpad_up) {
            FoundationHook.toggle()
        }
        g1prev.copy(opMode.gamepad1)
    }

    fun liftControls() {
        if (opMode.gamepad2.right_stick_y > 0.05) {
            Lift.power(opMode.gamepad2.left_stick_y.toDouble() * -1.0)
            //this is up
        } else if (opMode.gamepad2.right_stick_y < 0.05) {
            Lift.power(opMode.gamepad2.left_stick_y.toDouble() * -1.0)
        } else {
            Lift.power(0.0)
        }
    }

    fun intakeControls() {
        when {
            opMode.gamepad1.left_bumper -> Intake.power(1.0)
            opMode.gamepad1.right_bumper -> Intake.power(-1.0)
            else -> Intake.power(0.0)
        }
    }

    fun sixArcadeArc() {
        if (abs(opMode.gamepad1.left_stick_y) > .01 || abs(opMode.gamepad1.right_stick_x) > .01) {
            val multiplier = if (opMode.gamepad1.right_trigger > .5) {
                .5
            } else {
                1.0
            }
            val left : Double = (opMode.gamepad1.left_stick_y.toDouble() + (opMode.gamepad1.right_stick_x * flip)).clip(-1.0,1.0) * flip * (multiplier)
            val right : Double = (opMode.gamepad1.left_stick_y.toDouble() - (opMode.gamepad1.right_stick_x * flip)).clip(-1.0,1.0) * flip * (multiplier)
            val leftSign = if (left < 0) {
                -1.0
            } else {
                1.0
            }
            val rightSign = if (right < 0) {
                -1.0
            } else {
                1.0
            }
            DriveTrain.setPower((left.pow(2)) * leftSign, (right.pow(2)) * rightSign)
        } else {
            DriveTrain.setPower(0.0,0.0)
        }
    }

    fun getCompanion() : Modules {
        return Modules
    }

    fun followPath(points : MutableList<State>) {
        DriveTrain.followPath(points)
    }

    fun builder() : NPathBuilder {
        return Generator
    }
}