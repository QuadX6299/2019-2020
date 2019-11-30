package org.firstinspires.ftc.teamcode.Robot

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.PIDFCoefficients
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType
import org.firstinspires.ftc.teamcode.lib.Coords.Position
import org.firstinspires.ftc.teamcode.Robot.Meta.Constants
import org.firstinspires.ftc.teamcode.Robot.Sensors.IMU
import org.firstinspires.ftc.teamcode.lib.Coords.Point
import org.firstinspires.ftc.teamcode.lib.Extensions.plus
import org.firstinspires.ftc.teamcode.lib.Extensions.toVector
import org.openftc.revextensions2.ExpansionHubEx
import org.openftc.revextensions2.ExpansionHubMotor
import org.openftc.revextensions2.RevBulkData
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class NDriveTrain constructor(val Op : OpMode) {
    var position : Position = Position(0.0,0.0,0.0)
    var lastPosition : Position? = null
    var lastWheelPositions = emptyList<Double>()
    var lastHeading : Double? = null

    val motorType : MotorConfigurationType = Constants.MOTOR_CONFIG

    lateinit var left : List<ExpansionHubMotor>
    lateinit var right : List<ExpansionHubMotor>
    lateinit var all : List<ExpansionHubMotor>

    lateinit var hubL : ExpansionHubEx
    lateinit var hubR : ExpansionHubEx

    init {
        hubL = Op.hardwareMap.get(ExpansionHubEx::class.java, "hubLeft")
        hubR = Op.hardwareMap.get(ExpansionHubEx::class.java, "hubRight")

        val fl : ExpansionHubMotor = Op.hardwareMap.get(ExpansionHubMotor::class.java, "fl")
        val ml : ExpansionHubMotor = Op.hardwareMap.get(ExpansionHubMotor::class.java, "ml")
        val bl : ExpansionHubMotor = Op.hardwareMap.get(ExpansionHubMotor::class.java, "bl")

        val fr : ExpansionHubMotor = Op.hardwareMap.get(ExpansionHubMotor::class.java, "fr")
        val mr : ExpansionHubMotor = Op.hardwareMap.get(ExpansionHubMotor::class.java, "mr")
        val br : ExpansionHubMotor = Op.hardwareMap.get(ExpansionHubMotor::class.java, "br")

        fr.direction = DcMotorSimple.Direction.REVERSE
        br.direction = DcMotorSimple.Direction.REVERSE
        mr.direction = DcMotorSimple.Direction.REVERSE

        left = listOf(fl,ml,bl)
        right = listOf(fr,mr,br)
        all = listOf(fl,fr,ml,mr,bl,br)

        all.forEach {
            it.mode = DcMotor.RunMode.RUN_USING_ENCODER
            it.setPIDFCoefficients(it.mode, PIDFCoefficients(35.0,0.0,30.0,0.0))
        }


    }

    fun refresh() {
        val encoderPose = getEncoderAverage()
        val heading = IMU.heading()
        if (lastWheelPositions.isNotEmpty()) {
            val delta = encoderPose.zip(lastWheelPositions).map { it.first - it.second }
            val (l, r) = encoderPose
            val dist = (l + r) / 2.0
            val fullAngleDelta = {
                var leftover = heading % (2.0 * PI)
                leftover = (leftover + 2 * PI) % (2 * PI)
                if (leftover > PI) {
                    leftover -= (2*PI)
                }
                leftover
            }
            //poseEstimate = fieldPosition
            //robot pose delta = distance, heading
            val newPose = {
                val dtheta = fullAngleDelta()
                val (sineTerm, cosTerm) = if (dtheta < .000001) {
                    1.0 - dtheta * dtheta / 6.0 to dtheta / 2.0
                } else {
                    sin(dtheta) / dtheta to (1 - cos(dtheta)) / dtheta
                }

                val fieldPositionDelta = Point(
                        sineTerm * dist,
                        cosTerm * dist
                )
                val rotatedAngle = fieldPositionDelta.rotate(position.heading)
                val fieldPoseDelta = Position(rotatedAngle.x, rotatedAngle.y, fullAngleDelta())

                val norm = {
                    var leftover = (position.heading + fieldPoseDelta.heading) % (2.0 * PI)
                    leftover = (leftover + 2 * PI) % (2 * PI)
                    leftover
                }
                Position(dist + fieldPoseDelta.x, fieldPoseDelta.y, norm())
            }
            position = newPose()
            lastWheelPositions = encoderPose
            lastHeading = heading
        }
    }

    fun Double.e2i() : Double = (Constants.WHEEL_RAD * 2.0 * PI * Constants.GEAR_RATIO * this) / motorType.ticksPerRev

    fun Double.rpmToVel() : Double = (this * Constants.GEAR_RATIO * 2.0 * PI * Constants.WHEEL_RAD) / 60.0

    fun getMaxRpm() : Double {
        return motorType.maxRPM * motorType.achieveableMaxRPMFraction
    }

    fun getTps() : Double {
        return (motorType.maxRPM * motorType.ticksPerRev) / 60.0
    }

    fun getF() : Double {
        return 32767 / getTps()
    }

    fun getEncoderAverage() : List<Double> {
        var leftC : Int = 0
        var rightC : Int = 0
        var leftSum : Double = 0.0
        var rightSum : Double = 0.0

        val bulkL : RevBulkData = hubL.bulkInputData
        val bulkR : RevBulkData = hubR.bulkInputData

        left.forEach {
            if (bulkL.getMotorCurrentPosition(it) != 0) {
                leftC++
                leftSum += bulkL.getMotorCurrentPosition(it).toDouble().e2i()
            }
        }
        right.forEach {
            if (bulkR.getMotorCurrentPosition(it) != 0) {
                rightC++
                rightSum += bulkR.getMotorCurrentPosition(it).toDouble().e2i()
            }
        }

        return listOf(leftSum / leftC.toDouble(), rightSum / rightC.toDouble())
    }

    fun getVelocityAverage() : List<Double> {
        var leftC : Int = 0
        var rightC : Int = 0
        var leftSum : Double = 0.0
        var rightSum : Double = 0.0

        val bulkL : RevBulkData = hubL.bulkInputData
        val bulkR : RevBulkData = hubR.bulkInputData

        left.forEach {
            if (bulkL.getMotorCurrentPosition(it) != 0) {
                leftC++
                leftSum += bulkL.getMotorVelocity(it).toDouble().e2i()
            }
        }
        right.forEach {
            if (bulkR.getMotorCurrentPosition(it) != 0) {
                rightC++
                rightSum += bulkR.getMotorVelocity(it).toDouble().e2i()
            }
        }

        return listOf(leftSum / leftC.toDouble(), rightSum / rightC.toDouble())
    }

    fun resetEncoders() {
        all.forEach {
            it.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            it.mode = DcMotor.RunMode.RUN_USING_ENCODER
        }
    }

    fun reverse() {
        all.forEach {
            it.direction = if (it.direction == DcMotorSimple.Direction.REVERSE) {
                DcMotorSimple.Direction.FORWARD
            } else {
                DcMotorSimple.Direction.REVERSE
            }
        }
    }

    fun setZeroPowerBehavior(type : DcMotor.ZeroPowerBehavior) {
        all.forEach {
            it.zeroPowerBehavior = type
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

    fun turnPrimitive(power : Double, right : Boolean) {
        if (right) setPower(power, -power) else setPower(-power, power)
    }
}