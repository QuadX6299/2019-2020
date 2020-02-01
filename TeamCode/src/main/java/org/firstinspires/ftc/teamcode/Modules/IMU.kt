package org.firstinspires.ftc.teamcode.Modules

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference
import org.firstinspires.ftc.robotcore.external.navigation.Orientation
import org.firstinspires.ftc.teamcode.Lib.Util.limitAngle2
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

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
        return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS).firstAngle.toDouble().limitAngle2()
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
}