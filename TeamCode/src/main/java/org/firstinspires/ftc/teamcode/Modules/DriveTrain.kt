package org.firstinspires.ftc.teamcode.Modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.PIDFCoefficients
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType
import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint
import org.firstinspires.ftc.teamcode.Lib.Odometry.Odom1
import org.firstinspires.ftc.teamcode.Lib.Path.Follower
import org.firstinspires.ftc.teamcode.Lib.Path.goToPosition
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D
import org.firstinspires.ftc.teamcode.Lib.Util.*
import org.firstinspires.ftc.teamcode.Modules.Meta.Clock
import org.firstinspires.ftc.teamcode.Modules.Meta.GoBILDA435
import org.openftc.revextensions2.ExpansionHubEx
import org.openftc.revextensions2.ExpansionHubMotor
import org.openftc.revextensions2.RevBulkData
import kotlin.math.PI


class DriveTrain constructor(opMode: OpMode) : Odom1(offsets) {
    var lastPose2D : Pose2D = Pose2D(0.0,0.0,0.0)
    var lastTime = Clock.system().seconds()

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
        // Left Right Center
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
            it.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        }
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
        val bulkData: RevBulkData = hubR.bulkInputData
                ?: return listOf(0.0, 0.0, 0.0, 0.0)

        val wheelPositions: MutableList<Double> = mutableListOf()

        encoders.forEach {
            wheelPositions.add(bulkData.getMotorCurrentPosition(it).e2iOdom())
        }
        return wheelPositions
    }

    fun getOdomVelocities() : List<Double> {
        val bulkData: RevBulkData = hubR.bulkInputData
                ?: return listOf(0.0,0.0,0.0)

        val wheelVelocities: MutableList<Double> = mutableListOf()

        encoders.forEach {
            wheelVelocities.add(bulkData.getMotorVelocity(it).e2iOdom())
        }
        return wheelVelocities
    }

    private fun internalUpdate() : Pose2D {
        update()
        return poseEstimate
    }

    fun goToWaypoint(waypoint: Waypoint) {
        val (flp,blp,frp,brp) = goToPosition(internalUpdate(), Pose2D(0.0,0.0,0.0), waypoint, .2, true)
        fl.power = flp
        fr.power = frp
        bl.power = blp
        br.power = brp
    }

    fun followUpdate(follower: Follower) {
        update()
        val (flp,blp,frp,brp) = follower.update(poseEstimate)
        fl.power = flp
        fr.power = frp
        bl.power = blp
        br.power = brp
    }

    fun List<ExpansionHubMotor>.runToPose(encoder : Double) {
        this.forEach {
            it.mode = DcMotor.RunMode.RUN_TO_POSITION
            it.targetPosition = encoder.toInt()
        }
    }

    fun setPower(p0 : Double, p1 : Double) {
        left.forEach {
            it.power = p0
        }
        right.forEach {
            it.power = p1
        }
    }

    fun motorsBusy() : Boolean {
        var busy = true
        all.forEach {
            busy = busy && it.isBusy
        }
        return busy
    }

    fun encoderDrive(inches : Double, power : Double) {
        val (l,r) = getEncoderAverage()
        val newL = l + inches * odomTpr
        val newR = r + inches * odomTpr
        left.runToPose(newL)
        right.runToPose(newR)
        setPower(power, power)
        while (motorsBusy()) {
            val enc = getEncoderAverage()

        }
        setPower(0.0,0.0)
    }

    fun getEncoderAverage(): List<Double> {


        return listOf(encoders[0].toDouble(),encoders[1].toDouble())

    }
}