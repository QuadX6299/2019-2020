package org.firstinspires.ftc.teamcode.Robot

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo

object Bhorn {

    private lateinit var bhorn : Servo
    var isDown : Boolean = false
    @JvmStatic
    val pose : Double get() = bhorn.position

    @JvmStatic fun init(op : OpMode) {
        bhorn = op.hardwareMap.get(Servo::class.java, "bhorn")
        bhorn.position = 1.0
        isDown = false
    }

    @JvmStatic fun toggle() {
        bhorn.position = if (isDown) {
            isDown = false
            1.0
        } else {
            isDown = true
            0.0
        }
    }
}