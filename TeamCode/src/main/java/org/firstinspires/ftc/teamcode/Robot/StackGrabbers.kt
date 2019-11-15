package org.firstinspires.ftc.teamcode.Robot

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo

object StackGrabbers {
    lateinit var leftClamp : Servo
    lateinit var rightClamp : Servo

    var down : Boolean = false;

    @JvmStatic fun init(op : OpMode) {
        leftClamp = op.hardwareMap.get(Servo::class.java, "leftClamp")
        rightClamp = op.hardwareMap.get(Servo::class.java, "rightClamp")
        down = false
        origin()
    }

    @JvmStatic fun origin() {
        leftClamp.position = 1.0
        rightClamp.position = 0.0
    }

    @JvmStatic fun toggle() {
        if (down) {
            down = false
            leftClamp.position  = 1.0
            rightClamp.position = 0.0
        } else {
            down = true
            leftClamp.position = 0.0
            rightClamp.position = 1.0
        }
    }

}