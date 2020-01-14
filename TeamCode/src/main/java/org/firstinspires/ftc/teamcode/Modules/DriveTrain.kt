package org.firstinspires.ftc.teamcode.Modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.PIDFCoefficients
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType
import org.firstinspires.ftc.teamcode.Modules.Meta.GoBILDA435
import org.openftc.revextensions2.ExpansionHubEx
import org.openftc.revextensions2.ExpansionHubMotor

class DriveTrain constructor(val opMode: OpMode) {
    var lastWheelPositions = emptyList<Double>()

    val left : List<ExpansionHubMotor>
    val right : List<ExpansionHubMotor>
    val all : List<ExpansionHubMotor>

    val hubL : ExpansionHubEx = opMode.hardwareMap.get(ExpansionHubEx::class.java,  "hubLeft")
    val hubR : ExpansionHubEx = opMode.hardwareMap.get(ExpansionHubEx::class.java, "hubRight")

    val motorType : MotorConfigurationType = MotorConfigurationType.getMotorType(GoBILDA435::class.java)

    val fl : ExpansionHubMotor = opMode.hardwareMap.get(ExpansionHubMotor::class.java, "fl")
    val bl : ExpansionHubMotor = opMode.hardwareMap.get(ExpansionHubMotor::class.java, "bl")

    val fr : ExpansionHubMotor = opMode.hardwareMap.get(ExpansionHubMotor::class.java, "fr")
    val br : ExpansionHubMotor = opMode.hardwareMap.get(ExpansionHubMotor::class.java, "br")

    companion object Constants {
        const val wheelRadius = 2.0
        const val gearRatio = 1.0
        const val dtWidth = 18.0
    }

    init {
        fl.direction = DcMotorSimple.Direction.REVERSE
        bl.direction = DcMotorSimple.Direction.REVERSE

        left = listOf(fl, bl)
        right = listOf(fr, br)
        all = listOf(fl, fr, bl, br)

        all.forEach {
            it.mode = DcMotor.RunMode.RUN_USING_ENCODER
            it.setPIDFCoefficients(it.mode, PIDFCoefficients(60.0,.5,20.0,0.0))
        }
    }
}