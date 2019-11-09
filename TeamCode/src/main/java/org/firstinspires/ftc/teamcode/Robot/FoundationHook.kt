package org.firstinspires.ftc.teamcode.Robot

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo

object FoundationHook {
    lateinit var leftHook : Servo
    lateinit var rightHook : Servo

    var down : Boolean = false;

    @JvmStatic fun init(op : OpMode) {
        leftHook = op.hardwareMap.get(Servo::class.java, "leftHook")
        rightHook = op.hardwareMap.get(Servo::class.java, "rightHook")
        down = false
        origin()
    }

    @JvmStatic fun origin() {
        leftHook.position = 1.0
        rightHook.position = 0.0
    }

    @JvmStatic fun toggle() {
        if (down) {
            down = false
            leftHook.position  = 1.0
            rightHook.position = 0.0
        } else {
            down = true
            leftHook.position = 0.0
            rightHook.position = 1.0
        }
    }

}