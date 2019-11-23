package org.firstinspires.ftc.teamcode.OpMode

import android.os.Handler
import android.os.Looper
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.HerculesLibraries.Vision.NewBitMap
import org.firstinspires.ftc.teamcode.Robot.*
import org.firstinspires.ftc.teamcode.Robot.Sensors.IMU
import org.firstinspires.ftc.teamcode.Robot.Sensors.RangeSensor
import org.firstinspires.ftc.teamcode.lib.Constraints.MotionConstraint
import org.firstinspires.ftc.teamcode.lib.Constraints.PIDFCoefficients
import org.firstinspires.ftc.teamcode.lib.Constraints.TankKinematics
import org.firstinspires.ftc.teamcode.lib.Coords.Position
import org.firstinspires.ftc.teamcode.lib.Coords.Waypoint
import org.firstinspires.ftc.teamcode.lib.Extensions.clip
import org.firstinspires.ftc.teamcode.lib.Path.PathFollower
import org.firstinspires.ftc.teamcode.lib.Path.TestPaths
import java.util.logging.Level
import kotlin.math.*

object Robot {
    var g2btnDelay = ElapsedTime(0)
    var g1dpadDelay = ElapsedTime(0)
    var g2dpadDelay = ElapsedTime(0)
    var g1btnDelay = ElapsedTime(0)


    private var liftHold : Boolean = false
    private var g2manual : Boolean = true

    private lateinit var opMode: OpMode

    private var manipState = Grabber.STATES.ORIGIN

    var flip : Double = 1.0

    var x = 0.0
    var y = 0.0
    var velL = 0.0
    var velR = 0.0

    @JvmStatic fun init(op : OpMode) {
        opMode = op
        DriveTrain.init(opMode)
        DriveTrain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE)
        IMU.init(opMode)
        Intake.init(opMode)
        Lift.init(opMode)
        Grabber.init(opMode)
        FoundationHook.init(opMode)
        RangeSensor.init(opMode)
        Bhorn.init(opMode)
        NewBitMap.init(opMode)
        manipState = Grabber.STATES.ORIGIN
    }

    @JvmStatic fun reset() {
        IMU.init(opMode)
        DriveTrain.resetEncoders()
        x = 0.0
        y = 0.0
        velL = 0.0
        velR = 0.0
        liftHold = false
        g2manual = true
        flip = 1.0
        manipState = Grabber.STATES.ORIGIN
    }

    @JvmStatic fun manipMachine() {
        when(manipState) {
            Grabber.STATES.ORIGIN -> {
                Grabber.setPosition(Grabber.POSITIONS.RETURNPUSH)
                Grabber.setPosition(Grabber.POSITIONS.COLLECTION)
                manipState = Grabber.STATES.COLLECTION
            }
            Grabber.STATES.COLLECTION -> {
                Grabber.setPosition(Grabber.POSITIONS.PUSHTHROUGH)
                Handler(Looper.getMainLooper()).postDelayed({
                    Grabber.setPosition(Grabber.POSITIONS.CLAMPDOWN)
                }, 1000)
                manipState = Grabber.STATES.DEPOSIT
            }
            Grabber.STATES.DEPOSIT -> {
                Grabber.setPosition(Grabber.POSITIONS.TRANSITION)
                Handler(Looper.getMainLooper()).postDelayed({
                    Grabber.setPosition(Grabber.POSITIONS.ZERO)
                }, 500)
                manipState = Grabber.STATES.RETURN
            }
            Grabber.STATES.HORIZONTALDEPOSIT -> {
                Grabber.setPosition(Grabber.POSITIONS.HORIZONTALDEPO)
            }
            Grabber.STATES.RETURN -> {
                Grabber.setPosition(Grabber.POSITIONS.COLLECTION)
                Grabber.setPosition(Grabber.POSITIONS.DROP)
                manipState = Grabber.STATES.ORIGIN
            }
        }
    }
    @JvmStatic fun depoBlockAuto(){
        manipMachine()
        Thread.sleep(1000)
        manipMachine()
        Thread.sleep(2000)
        manipMachine()
        Thread.sleep(2000)
        Grabber.setPosition(Grabber.POSITIONS.DROP)
    }



    @JvmStatic fun Gamepad1Controls() {
        if (opMode.gamepad1.dpad_down && g1dpadDelay.milliseconds() > 150) {
            Grabber.toggle()
            g1dpadDelay.reset()
        } else if (opMode.gamepad1.a && g1btnDelay.milliseconds() > 150) {
            flip *= -1.0
            g1btnDelay.reset()
        } else if (opMode.gamepad1.dpad_up && g1dpadDelay.milliseconds() > 150) {
            FoundationHook.toggle()
            g1dpadDelay.reset()
        }
    }

    @JvmStatic fun Gamepad2Controls() {
        if (g2manual) {
            if (opMode.gamepad2.dpad_up && g2dpadDelay.milliseconds() > 150) {
                Grabber.toggleHorn()
                g2dpadDelay.reset()
            } else if (opMode.gamepad2.dpad_down && g2dpadDelay.milliseconds() > 150) {
                Grabber.toggle()
                g2dpadDelay.reset()
            } else if (opMode.gamepad2.y && g2btnDelay.milliseconds() > 150) {
                Grabber.setPosition(Grabber.POSITIONS.PUSHTHROUGH)
                Handler(Looper.getMainLooper()).postDelayed({
                    Grabber.setPosition(Grabber.POSITIONS.CLAMPDOWN)
                }, 1000)
                g2btnDelay.reset()
            } else if (opMode.gamepad2.b && g2btnDelay.milliseconds() > 150) {
                Grabber.setPosition(Grabber.POSITIONS.TRANSITION)
                Handler(Looper.getMainLooper()).postDelayed({
                    Grabber.setPosition(Grabber.POSITIONS.ZERO)
                }, 750)
                g2btnDelay.reset()
            } else if (opMode.gamepad2.a && g2btnDelay.milliseconds() > 150) {
                Grabber.setPosition(Grabber.POSITIONS.HORIZONTALDEPO)
                g2btnDelay.reset()
            } else if (opMode.gamepad2.x && g2btnDelay.milliseconds() > 150) {
                Grabber.setPosition(Grabber.POSITIONS.ZERO)
                Handler(Looper.getMainLooper()).postDelayed({
                    Grabber.setPosition(Grabber.POSITIONS.COLLECTION)
                }, 500)
                Grabber.setPosition(Grabber.POSITIONS.DROP)
                g2btnDelay.reset()
            } else if (opMode.gamepad2.dpad_right && g2dpadDelay.milliseconds() > 150) {
                Bhorn.toggle()
                g2dpadDelay.reset()
            }
        } else {
            if (opMode.gamepad2.y && g2btnDelay.milliseconds() > 150) {
                manipMachine()
                g2btnDelay.reset()
            } else if (opMode.gamepad2.a && g2btnDelay.milliseconds() > 150) {
                Grabber.setPosition(Grabber.POSITIONS.ZERO)
                g2btnDelay.reset()
            } else if (opMode.gamepad2.b && g2btnDelay.milliseconds() > 150) {
                Grabber.setPosition(Grabber.POSITIONS.HORIZONTALDEPO)
                g2btnDelay.reset()
            }
        }
        if (opMode.gamepad2.right_bumper && g2dpadDelay.milliseconds() > 150) {
            g2manual = false
            g2dpadDelay.reset()
        } else if (opMode.gamepad2.left_bumper && g2dpadDelay.milliseconds() > 150) {
            g2manual = true
            g2dpadDelay.reset()
        }

    }

    @JvmStatic fun intake() {
        when {
            opMode.gamepad1.left_bumper -> Intake.power(1.0)
            opMode.gamepad1.right_bumper -> Intake.power(-1.0)
            else -> Intake.power(0.0)
        }
    }
    @JvmStatic fun lift(){
        //this is down
        if (opMode.gamepad2.right_stick_y > 0.05) {
            Lift.power(opMode.gamepad2.left_stick_y.toDouble() * -1.0)
        //this is up
        } else if (opMode.gamepad2.right_stick_y < 0.05) {
            Lift.power(opMode.gamepad2.left_stick_y.toDouble() * -1.0)
        } else {
            Lift.power(0.0)
        }
    }
    @JvmStatic fun sixArcadeArc() {
        if (abs(opMode.gamepad1.left_stick_y) > .01 || abs(opMode.gamepad1.right_stick_x) > .01) {
            DriveTrain.fl.power = (opMode.gamepad1.left_stick_y.toDouble() + (opMode.gamepad1.right_stick_x * flip)).clip(-1.0,1.0) * flip * (1.0 - opMode.gamepad1.left_trigger)
            DriveTrain.ml.power = (opMode.gamepad1.left_stick_y.toDouble() + (opMode.gamepad1.right_stick_x * flip)).clip(-1.0,1.0) * flip * (1.0 - opMode.gamepad1.left_trigger)
            DriveTrain.bl.power = (opMode.gamepad1.left_stick_y.toDouble() + (opMode.gamepad1.right_stick_x * flip)).clip(-1.0,1.0) * flip * (1.0 - opMode.gamepad1.left_trigger)

            DriveTrain.fr.power = (opMode.gamepad1.left_stick_y.toDouble() - (opMode.gamepad1.right_stick_x * flip)).clip(-1.0,1.0) * flip * (1.0 - opMode.gamepad1.left_trigger)
            DriveTrain.mr.power = (opMode.gamepad1.left_stick_y.toDouble() - (opMode.gamepad1.right_stick_x * flip)).clip(-1.0,1.0) * flip * (1.0 - opMode.gamepad1.left_trigger)
            DriveTrain.br.power = (opMode.gamepad1.left_stick_y.toDouble() - (opMode.gamepad1.right_stick_x * flip)).clip(-1.0,1.0) * flip * (1.0 - opMode.gamepad1.left_trigger)
        } else {
            DriveTrain.fl.power = (0.0)
            DriveTrain.ml.power = (0.0)
            DriveTrain.bl.power = (0.0)
            DriveTrain.fr.power = (0.0)
            DriveTrain.mr.power = (0.0)
            DriveTrain.br.power = (0.0)
        }
    }

//    @ObsoleteCoroutinesApi
//    @kotlinx.coroutines.ExperimentalCoroutinesApi
//    @JvmStatic fun odom() = runBlocking {
//
//        val purePursuit = DriveTrain.startIntegration(TestPaths.straightLine, 10.0, TankKinematics(DriveTrain.width), PIDFCoefficients(.01,0.0,.04,1/30.0,.0002), MotionConstraint(30.0, 5.0, 3.0))
//
//        jobList.add(purePursuit)
//
//        opMode.telemetry.addLine("Odometry Started")
//    }

    @JvmStatic fun PurePursuit(time : Double) : Waypoint {
        val deltas = DriveTrain.getPose(time)
        x += deltas.x
        y += deltas.y
        opMode.telemetry.addData("Pose: ", Position(x,y,deltas.ddx))
        return Waypoint(x,y,deltas.dx, deltas.dy, deltas.ddx, 0.0)
    }
}