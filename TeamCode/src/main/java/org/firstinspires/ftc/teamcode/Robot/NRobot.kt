package org.firstinspires.ftc.teamcode.Robot

import android.os.Handler
import android.os.Looper
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.PIDCoefficients
import com.qualcomm.robotcore.hardware.PIDFCoefficients
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.HerculesLibraries.Vision.NewBitMap
import org.firstinspires.ftc.teamcode.Robot.Sensors.IMU
import org.firstinspires.ftc.teamcode.lib.Coords.State
import org.firstinspires.ftc.teamcode.lib.Extensions.clip
import org.firstinspires.ftc.teamcode.lib.Path.NPathBuilder
import kotlin.math.*

class NRobot constructor(val opMode: OpMode) {

    var flip = 1.0
    
    var g1prev : Gamepad = Gamepad()
    var g2prev : Gamepad = Gamepad()

    val dt get() = DriveTrain
    val intake get() = Intake
    val lift get() = Lift
    val gantry get() = Gantry
//    val foundation get() = FoundationHook
    val companion get() = Modules


    companion object Modules {
        lateinit var DriveTrain : NDriveTrain4Mecanum
        lateinit var Intake : NIntake
        lateinit var Lift : NLift
        lateinit var FoundationHook : NFoundationHook
        lateinit var OSAsync : Handler
        lateinit var Generator : NPathBuilder
        //lateinit var Cap : NCap
        lateinit var Gantry : NGantry
        lateinit var Vision : NVision
        lateinit var AutoGrabber : NAutoGrabber

        fun init(Op : OpMode) {
            AutoGrabber = NAutoGrabber(Op)
            DriveTrain = NDriveTrain4Mecanum(Op)
            Gantry = NGantry(Op)
            Intake = NIntake(Op)
            Lift = NLift(Op)
            FoundationHook = NFoundationHook(Op)
            OSAsync = Handler(Looper.getMainLooper())
            Generator = NPathBuilder()
            Vision = NVision(Op)
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
        newG2()
        intakeControls()
        liftControls()
        sixArcadeArc()
    }

    fun newG2(){
        if (opMode.gamepad2.a && g2prev.a != opMode.gamepad2.a){
            Gantry.setAssemblyPosition(NGantry.POSITIONS.COLLECTION)
        } else if (opMode.gamepad2.left_bumper && g2prev.left_bumper != opMode.gamepad2.left_bumper){
            Gantry.setAssemblyPosition(NGantry.POSITIONS.TRANSITION)
        } else if (opMode.gamepad2.y && g2prev.y != opMode.gamepad2.y){
            Gantry.setAssemblyPosition(NGantry.POSITIONS.GANTRYOUT)
        } else if (opMode.gamepad2.right_bumper && g2prev.right_bumper != opMode.gamepad2.right_bumper){
            Gantry.setAssemblyPosition(NGantry.POSITIONS.DEPOSIT)
        } else if (opMode.gamepad2.x && g2prev.x != opMode.gamepad2.x){
            Gantry.frontClampOpen()
        }
        g2prev.copy(opMode.gamepad2)
    }

    fun g1() {
        if (opMode.gamepad1.a && g1prev.a != opMode.gamepad1.a) {
            flip *= -1.0
        }
        else if (opMode.gamepad1.dpad_up && g1prev.dpad_up != opMode.gamepad1.dpad_up) {
            FoundationHook.toggle()
        }
        g1prev.copy(opMode.gamepad1)
    }

    fun liftControls() {
        if (opMode.gamepad2.right_trigger > .1) {
            Lift.power(-.2)
        } else {
            if (opMode.gamepad2.right_stick_y > 0.1) {
                Lift.power(opMode.gamepad2.left_stick_y.toDouble())
                //this is up
            } else if (opMode.gamepad2.right_stick_y < 0.1) {
                Lift.power(opMode.gamepad2.left_stick_y.toDouble())
            } else {
                Lift.power(0.0)
            }
        }
    }

    fun intakeControls() {
        when {
            opMode.gamepad1.left_bumper -> Intake.power(-1.0)
            opMode.gamepad1.right_bumper -> Intake.power(1.0)
            else -> Intake.power(0.0)
        }
    }

    fun sixArcadeArcTank() {
        if (abs(opMode.gamepad1.left_stick_y) > .01 || abs(opMode.gamepad1.right_stick_x) > .01) {
            val multiplier = if (opMode.gamepad1.right_trigger > .5) {
                .3
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

    fun sixArcadeArc() {
        //checking for valid range to apply power (has to give greater power than .1)
        if (Math.abs(Math.hypot(opMode.gamepad1.left_stick_x.toDouble(), opMode.gamepad1.left_stick_y.toDouble())) > .1 || Math.abs(Math.atan2(opMode.gamepad1.left_stick_y.toDouble(), opMode.gamepad1.left_stick_x.toDouble()) - Math.PI / 4) > .1) {

            val r = Math.hypot(opMode.gamepad1.left_stick_x.toDouble(), opMode.gamepad1.left_stick_y.toDouble())
            val theta = Math.atan2(opMode.gamepad1.left_stick_y.toDouble(), -opMode.gamepad1.left_stick_x.toDouble()) - Math.PI / 4
            val rightX = -opMode.gamepad1.right_stick_x.toDouble() * -flip

            //as per unit circle cos gives x, sin gives you y
            var FL = r * Math.cos(theta) + rightX
            var FR = r * Math.sin(theta) - rightX
            var BL = r * Math.sin(theta) + rightX
            var BR = r * Math.cos(theta) - rightX

            //make sure you don't try and give power bigger than 1
            if (Math.abs(FL) > 1 || Math.abs(BL) > 1 || Math.abs(FR) > 1 || Math.abs(BR) > 1) {
                FL /= Math.max(Math.max(Math.abs(FL), Math.abs(FR)), Math.max(Math.abs(BL), Math.abs(BR)))
                BL /= Math.max(Math.max(Math.abs(FL), Math.abs(FR)), Math.max(Math.abs(BL), Math.abs(BR)))
                FR /= Math.max(Math.max(Math.abs(FL), Math.abs(FR)), Math.max(Math.abs(BL), Math.abs(BR)))
                BR /= Math.max(Math.max(Math.abs(FL), Math.abs(FR)), Math.max(Math.abs(BL), Math.abs(BR)))

            }
            DriveTrain.fl.power = FL * flip
            DriveTrain.fr.power = FR * flip
            DriveTrain.bl.power = BL * flip
            DriveTrain.br.power = BR * flip


        } else {
            DriveTrain.fl.power = 0.0
            DriveTrain.fr.power = 0.0
            DriveTrain.bl.power = 0.0
            DriveTrain.br.power = 0.0

        }

    }


//    fun followPath(points : MutableList<State>) {
//        DriveTrain.followPath(points)
//    }
//
//    fun followPath(points : MutableList<State>, pidCoefficients: PIDCoefficients, kv : Double, ka : Double) {
//        DriveTrain.followPath(points, pidCoefficients, kv, ka)
//    }
//
//    fun builder() : NPathBuilder {
//        return Generator
//    }
}