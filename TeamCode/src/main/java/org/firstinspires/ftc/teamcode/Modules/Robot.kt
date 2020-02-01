package org.firstinspires.ftc.teamcode.Modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.Lib.Path.Follower
import kotlin.*
import kotlin.math.*

class Robot constructor(val opMode: OpMode) {
    var flip = 1.0
    var g1prev : Gamepad = Gamepad()
    var g2prev : Gamepad = Gamepad()
    var feedforward : Boolean = false

    companion object Modules {
        lateinit var cap : Cap
        lateinit var driveTrain: DriveTrain
        lateinit var foundationHooks: FoundationHooks
        lateinit var gantry: Gantry
        lateinit var intake: Intake
        lateinit var lift : Lift
        lateinit var autoGrabberRight: AutoGrabberRight
        lateinit var autoGrabberLeft: AutoGrabberLeft

        fun init(Op: OpMode) {
            cap = Cap(Op)
            driveTrain = DriveTrain(Op)
            foundationHooks = FoundationHooks(Op)
            gantry = Gantry(Op)
            intake = Intake(Op)
            lift = Lift(Op)
            autoGrabberRight = AutoGrabberRight(Op)
            autoGrabberLeft = AutoGrabberLeft(Op)
            IMU.init(Op)
        }
    }

    init {
        flip = 1.0
        init(opMode)
    }

    fun controls() {
        liftControls()
        g1()
        g2()
        intakeControls()
        sixArcadeArc()
    }

    fun g2(){
        if (opMode.gamepad2.a && g2prev.a != opMode.gamepad2.a){
            gantry.setAssemblyPosition(Gantry.POSITIONS.COLLECTION)
        } else if (opMode.gamepad2.left_bumper && g2prev.left_bumper != opMode.gamepad2.left_bumper){
            gantry.toggleFrontClamp()
        } else if (opMode.gamepad2.y && g2prev.y != opMode.gamepad2.y){
            gantry.setAssemblyPosition(Gantry.POSITIONS.GANTRYOUT)
        } else if (opMode.gamepad2.right_bumper && g2prev.right_bumper != opMode.gamepad2.right_bumper){
            gantry.toggleBackClamp()
        } else if (opMode.gamepad2.x && g2prev.x != opMode.gamepad2.x){
            gantry.frontClampOpen()
        }
        g2prev.copy(opMode.gamepad2)
    }

    fun g1() {
        if (opMode.gamepad1.a && g1prev.a != opMode.gamepad1.a) {
            flip *= -1.0
        }
        else if (opMode.gamepad1.dpad_up && g1prev.dpad_up != opMode.gamepad1.dpad_up) {
            foundationHooks.toggle()
        } else if (opMode.gamepad1.x && g1prev.x != opMode.gamepad1.x){
            cap.toggle()
        }
        g1prev.copy(opMode.gamepad1)
    }

    fun liftControls() {
        if (opMode.gamepad2.left_stick_y < -0.1) {
            lift.power(opMode.gamepad2.left_stick_y.toDouble())
            feedforward = true
        } else if (opMode.gamepad2.left_stick_y > 0.1) {
            lift.power(-0.05)
            feedforward = false
        } else if (opMode.gamepad2.right_stick_x > .1) {
            lift.power(opMode.gamepad2.right_stick_x.toDouble())
            feedforward = false
        }
        else {
            lift.power(if (feedforward) -.2 else 0.0)
        }
    }

    fun intakeControls() {
        when {
            opMode.gamepad1.left_bumper -> intake.power(-1.0)
            opMode.gamepad1.right_bumper -> intake.power(1.0)
            else -> intake.power(0.0)
        }
    }
    fun sixArcadeArc() {
        //checking for valid range to apply power (has to give greater power than .1)
        if (abs(hypot(opMode.gamepad1.left_stick_x.toDouble(), opMode.gamepad1.left_stick_y.toDouble())) > .1 || abs(atan2(opMode.gamepad1.left_stick_y.toDouble(), opMode.gamepad1.left_stick_x.toDouble()) - PI / 4) > .1) {

            val r = hypot(opMode.gamepad1.left_stick_x.toDouble(), opMode.gamepad1.left_stick_y.toDouble())
            val theta = atan2(opMode.gamepad1.left_stick_y.toDouble(), -opMode.gamepad1.left_stick_x.toDouble()) - PI / 4
            val rightX = -opMode.gamepad1.right_stick_x.toDouble() * -flip

            //as per unit circle cos gives x, sin gives you y
            var FL = r * cos(theta) + rightX
            var FR = r * sin(theta) - rightX
            var BL = r * sin(theta) + rightX
            var BR = r * cos(theta) - rightX

            //make sure you don't try and give power bigger than 1
            if (abs(FL) > 1 || abs(BL) > 1 || abs(FR) > 1 || abs(BR) > 1) {
                FL /= max(max(abs(FL), abs(FR)), max(abs(BL), abs(BR)))
                BL /= max(max(abs(FL), abs(FR)), max(abs(BL), abs(BR)))
                FR /= max(max(abs(FL), abs(FR)), max(abs(BL), abs(BR)))
                BR /= max(max(abs(FL), abs(FR)), max(abs(BL), abs(BR)))

            }
            driveTrain.fl.power = FL * flip
            driveTrain.fr.power = FR * flip
            driveTrain.bl.power = BL * flip
            driveTrain.br.power = BR * flip


        } else {
            driveTrain.fl.power = 0.0
            driveTrain.fr.power = 0.0
            driveTrain.bl.power = 0.0
            driveTrain.br.power = 0.0
        }
    }


    fun follow(follower: Follower) {
        driveTrain.update()
        driveTrain.setPower(follower.update(driveTrain.internalUpdate()))
    }
}