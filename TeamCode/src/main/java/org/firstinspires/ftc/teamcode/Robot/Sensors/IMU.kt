package org.firstinspires.ftc.teamcode.Robot.Sensors

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.robotcore.external.navigation.*
import org.firstinspires.ftc.teamcode.lib.Coords.Point
import org.firstinspires.ftc.teamcode.lib.Extensions.dot
import org.firstinspires.ftc.teamcode.lib.Extensions.limitAngle
import org.firstinspires.ftc.teamcode.lib.Extensions.r2d
import kotlin.math.*

object IMU {
    private lateinit var imu : BNO055IMU

    fun init(op : OpMode) {
        imu = op.hardwareMap.get(BNO055IMU::class.java, "imu")
        val params = BNO055IMU.Parameters()
        params.angleUnit = BNO055IMU.AngleUnit.RADIANS
        params.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC
        imu.initialize(params)
    }

    @JvmStatic
    fun heading() : Double {
        return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS).firstAngle.toDouble().limitAngle()
    }

    @JvmStatic
    fun headingPrimitive() : Double {
        return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle.toDouble()
    }

    @JvmStatic
    fun imuOrientation() : Orientation {
        return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.RADIANS)
    }

    @JvmStatic
    fun getAbsDifference(angle : Double) : Double {
        val head = headingPrimitive()
        return atan2(sin(head-angle), cos(head-angle))
    }


    fun startAccel(pollingMs : Int) {
        imu.startAccelerationIntegration(Position(), Velocity(), pollingMs)
    }
}