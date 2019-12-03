package org.firstinspires.ftc.teamcode.Robot

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo

object Cap {

    private lateinit var cap : Servo
    var isDown : Boolean = false
    @JvmStatic
    val pose : Double get() = cap.position

    @JvmStatic fun init(op : OpMode) {
        cap = op.hardwareMap.get(Servo::class.java, "cap")
        cap.position = .8
        isDown = false
    }

    @JvmStatic fun toggle() {
        cap.position = if (isDown) {
            isDown = false
            1.0
        } else {
            isDown = true
            0.0
        }
    }

    @JvmStatic fun down() {
        cap.position = 0.0
    }

    @JvmStatic fun up() {
        cap.position = 1.0
    }
}