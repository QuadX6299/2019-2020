package org.firstinspires.ftc.teamcode.Robot

import android.os.Handler
import android.os.Looper
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.PIDCoefficients
import com.qualcomm.robotcore.hardware.PIDFCoefficients
import com.qualcomm.robotcore.util.ElapsedTime
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
    val foundation get() = FoundationHook
    val cap get() = Cap
    val companion get() = Modules

    companion object Modules {
        lateinit var DriveTrain : NDriveTrain
        lateinit var Grabber : NGrabber
        lateinit var Intake : NIntake
        lateinit var Lift : NLift
        lateinit var FoundationHook : NFoundationHook
        lateinit var OSAsync : Handler
        lateinit var Generator : NPathBuilder
        lateinit var Cap : NCap
        lateinit var Gantry : NGantry

        fun init(Op : OpMode) {
            DriveTrain = NDriveTrain(Op)
            //Grabber = NGrabber(Op)
            Gantry = NGantry(Op)
            Intake = NIntake(Op)
            Lift = NLift(Op)
            FoundationHook = NFoundationHook(Op)
            OSAsync = Handler(Looper.getMainLooper())
            Generator = NPathBuilder()
            //Bhorn = NBhorn(Op)
            IMU.init(Op)
            //Cap = NCap(Op)
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

    fun newG2(){
        if (opMode.gamepad2.a && g2prev.a != opMode.gamepad2.a){
            Gantry.setAssemblyPosition(NGantry.POSITIONS.COLLECTION)
        } else if (opMode.gamepad2.left_bumper && g2prev.left_bumper != opMode.gamepad2.left_bumper){
            Gantry.setAssemblyPosition(NGantry.POSITIONS.TRANSITION)
        } else if (opMode.gamepad2.y && g2prev.y != opMode.gamepad2.y){
            Gantry.setAssemblyPosition(NGantry.POSITIONS.GANTRYOUT)
        } else if (opMode.gamepad2.right_bumper && g2prev.right_bumper != opMode.gamepad2.right_bumper){
            Gantry.setAssemblyPosition(NGantry.POSITIONS.DEPOSIT)
        }
        g2prev.copy(opMode.gamepad2)
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
        } else if (opMode.gamepad2.dpad_right && g2prev.dpad_right != opMode.gamepad2.dpad_right){
            Cap.toggle()
        } else if (opMode.gamepad2.dpad_left && g2prev.dpad_left != opMode.gamepad2.dpad_left){
            Grabber.setAssemblyPosition(NGrabber.POSITIONS.TRANSITION)
            OSAsync.postDelayed({
                Grabber.setAssemblyPosition(NGrabber.POSITIONS.CAP)
            },750)

        }
        g2prev.copy(opMode.gamepad2)
    }

    fun g1() {
        if (opMode.gamepad1.a && g1prev.a != opMode.gamepad1.a) {
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
        if (((abs(hypot(opMode.gamepad1.left_stick_x, opMode.gamepad1.left_stick_y))) > .1) ||
                abs(atan2(opMode.gamepad1.left_stick_y, opMode.gamepad1.left_stick_x) - PI / 4) > .1) {

            val r : Double = hypot(opMode.gamepad1.left_stick_x, opMode.gamepad1.left_stick_y).toDouble();
            val theta : Double = atan2(opMode.gamepad1.left_stick_y, -opMode.gamepad1.left_stick_x) - PI / 4;
            val rightX : Double = -opMode.gamepad1.right_stick_x.toDouble();

            //as per unit circle cos gives x, sin gives you y
            var FL : Double = r * cos(theta) + rightX
            var FR : Double = r * sin(theta) - rightX
            var BL : Double = r * sin(theta) + rightX
            var BR : Double = r * cos(theta) - rightX

            //make sure you don't try and give power bigger than 1
            if (((abs(FL) > 1) || (abs(BL) > 1)) || ((abs(FR) > 1) || (abs(BR) > 1))) {
                FL /= max(max(abs(FL), abs(FR)), max(abs(BL), abs(BR)));
                BL /= max(max(abs(FL), abs(FR)), max(abs(BL), abs(BR)));
                FR /= max(max(abs(FL), abs(FR)), max(abs(BL), abs(BR)));
                BR /= max(max(abs(FL), abs(FR)), max(abs(BL), abs(BR)));

            }
            DriveTrain.fl.power = FL
            //DriveTrain.ml.power = FL

            DriveTrain.fr.power = FR
            DriveTrain.bl.power = BL

            DriveTrain.br.power = BR
            //DriveTrain.mr.power = BR
        }
        else {
            DriveTrain.setPower(0.0,0.0)
        }
    }


    fun followPath(points : MutableList<State>) {
        DriveTrain.followPath(points)
    }

    fun followPath(points : MutableList<State>, pidCoefficients: PIDCoefficients, kv : Double, ka : Double) {
        DriveTrain.followPath(points, pidCoefficients, kv, ka)
    }

    fun builder() : NPathBuilder {
        return Generator
    }
}