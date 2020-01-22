package org.firstinspires.ftc.teamcode.Modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.PIDFCoefficients
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType
import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint
import org.firstinspires.ftc.teamcode.Lib.Odometry.Odom1
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D
import org.firstinspires.ftc.teamcode.Modules.Meta.GoBILDA435
import org.openftc.revextensions2.ExpansionHubEx
import org.openftc.revextensions2.ExpansionHubMotor
import org.openftc.revextensions2.RevBulkData
import kotlin.math.PI


class DriveTrain constructor(val opMode: OpMode) : Odom1(offsets) {
    var lastWheelPositions = emptyList<Double>()

    val left : List<ExpansionHubMotor>
    val right : List<ExpansionHubMotor>
    val all : List<ExpansionHubMotor>
    val encoders : List<Int>

    val hubL : ExpansionHubEx = opMode.hardwareMap.get(ExpansionHubEx::class.java,  "hubLeft")
    val hubR : ExpansionHubEx = opMode.hardwareMap.get(ExpansionHubEx::class.java, "hubRight")

    val motorType : MotorConfigurationType = MotorConfigurationType.getMotorType(GoBILDA435::class.java)

    val fl : ExpansionHubMotor = opMode.hardwareMap.get(ExpansionHubMotor::class.java, "fl")
    val bl : ExpansionHubMotor = opMode.hardwareMap.get(ExpansionHubMotor::class.java, "bl")

    val fr : ExpansionHubMotor = opMode.hardwareMap.get(ExpansionHubMotor::class.java, "fr")
    val br : ExpansionHubMotor = opMode.hardwareMap.get(ExpansionHubMotor::class.java, "br")

    companion object Constants {
        const val odomRadius = 1.0
        const val odomRatio = 1.0
        const val odomTpr = 8192
        const val wheelRadius = 2.0
        const val gearRatio = 1.0
        const val dtWidth = 18.0
        // Left Center Right
        val offsets: List<Pose2D> = listOf(Pose2D(-6.188,-1.748, 0.0), Pose2D(-6.188, 1.748, 0.0), Pose2D(.006, .033, (-PI) / 2.0))
    }

    init {
        fl.direction = DcMotorSimple.Direction.REVERSE
        bl.direction = DcMotorSimple.Direction.REVERSE

        left = listOf(fl, bl)
        right = listOf(fr, br)
        all = listOf(fl, fr, bl, br)
        encoders = listOf(0,1,3)


        all.forEach {
            it.mode = DcMotor.RunMode.RUN_USING_ENCODER
            it.setPIDFCoefficients(it.mode, PIDFCoefficients(2.0,1.0,5.0,0.0))
        }

        resetEncoders()
    }

    fun Int.e2i() : Double = (wheelRadius * 2.0 * PI * gearRatio * this) / 383.6

    fun Int.e2iOdom() : Double = (odomRadius * 2.0 * PI * this) / odomTpr

    fun resetEncoders() {
        all.forEach {
            it.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            it.mode = DcMotor.RunMode.RUN_USING_ENCODER
        }
    }

    @Throws(InterruptedException::class)
    fun bulkEncoders() {
        val bulkL = hubL.bulkInputData
        val bulkR = hubR.bulkInputData

        if (bulkL == null || bulkR == null) {
            throw InterruptedException("Literally the bulks aren't there")
        }

        val leftEnc = mutableListOf<Int>()
        val rightEnc = mutableListOf<Int>()

        left.forEach {
            bulkL.getMotorCurrentPosition(it)
        }

        right.forEach {
            bulkR.getMotorCurrentPosition(it)
        }
    }

    override fun getWheelPositions(): List<Double> {
        // fl bl br fr
        val bulkData: RevBulkData = hubR.getBulkInputData()
                ?: return listOf(0.0, 0.0, 0.0, 0.0)

        val wheelPositions: MutableList<Double> = ArrayList()

        encoders.forEach {
            wheelPositions.add(bulkData.getMotorCurrentPosition(it).e2iOdom())

        }
        return wheelPositions
    }
}