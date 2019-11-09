package org.firstinspires.ftc.teamcode.Robot.Sensors

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit

object RangeSensor {

    private lateinit var rangeSensor : ModernRoboticsI2cRangeSensor

    fun init(op : OpMode) {
        rangeSensor = op.hardwareMap.get(ModernRoboticsI2cRangeSensor::class.java, "range")
    }

    @JvmStatic fun distance() : Double {
        return rangeSensor.getDistance(DistanceUnit.CM)
    }

    @JvmStatic fun isBlockThere() : Boolean {
        //in inches, may need to put in cm
        return (distance() < 5)
    }





}
