package org.firstinspires.ftc.teamcode.Robot

import android.os.Handler
import android.os.Looper
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo

class NBhorn constructor(Op : OpMode) {
    val bhorn : Servo = Op.hardwareMap.get(Servo::class.java,"bhorn")
    var isDown : Boolean = false

    init {
        bhorn.position = .8
        isDown = false
    }

    fun down() {
        isDown = true
        bhorn.position = 0.0
    }

    fun up() {
        isDown = false
        bhorn.position = 1.0
    }

    fun asyncMacro(delay : Long) {
        down()
        Handler(Looper.getMainLooper()).postDelayed({
            bhorn.position = 1.0
        }, delay)
    }

    fun toggleBhorn() {
        if (isDown) {
            isDown = false
            up()
        } else {
            isDown = true
            down()
        }
    }
}