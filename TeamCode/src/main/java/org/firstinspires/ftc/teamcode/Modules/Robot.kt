package org.firstinspires.ftc.teamcode.Modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.Lib.Odometry.Odom1
import org.firstinspires.ftc.teamcode.Lib.Util.clip
import kotlin.math.*

class Robot constructor(val opMode: OpMode) {
    var flip = 1.0

    companion object Modules {
        lateinit var driveTrain : DriveTrain
        fun init(Op: OpMode) {
            driveTrain = DriveTrain(Op)
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
        sixArcadeArc()
        driveTrain.update()
        opMode.telemetry.addData("Perceived Location", driveTrain.poseEstimate)
        opMode.telemetry.update()
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
}