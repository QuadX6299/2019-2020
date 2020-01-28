package org.firstinspires.ftc.teamcode.Modules

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.Lib.Hooks.Routine
import org.firstinspires.ftc.teamcode.Lib.Hooks.SingleRoutine
import org.firstinspires.ftc.teamcode.Lib.Hooks.Singular
import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint
import org.firstinspires.ftc.teamcode.Lib.Odometry.Odom1
import org.firstinspires.ftc.teamcode.Lib.Path.Follower
import org.firstinspires.ftc.teamcode.Lib.Util.clip
import kotlin.*
import kotlin.math.*

class Robot constructor(val opMode: OpMode) {
    var flip = 1.0
    var g1prev : Gamepad = Gamepad()

    companion object Modules {
        lateinit var driveTrain : DriveTrain
        fun init(Op: OpMode) {
            driveTrain = DriveTrain(Op)
        }
    }

    init {
        flip = 1.0
        init(opMode)
    }

    fun g1() {
        if (opMode.gamepad1.a && g1prev.a != opMode.gamepad1.a) {
            flip *= -1.0
        }
        g1prev.copy(opMode.gamepad1)
    }

    fun reset() {
        init(opMode)
    }

    fun controls() {
        sixArcadeArc()
        g1()
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

    fun followPath(path: List<Waypoint>, op: LinearOpMode) {
        val follower = Follower(path, this, opMode)
        while (op.opModeIsActive()) {
            driveTrain.followUpdate(follower)
        }
    }
}