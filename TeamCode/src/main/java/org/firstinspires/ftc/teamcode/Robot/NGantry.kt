package org.firstinspires.ftc.teamcode.Robot

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.openftc.revextensions2.ExpansionHubServo

class NGantry constructor(Op : OpMode) {
    val gantry : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "gantry")

    var down : Boolean = false

    init {
        origin()
    }

    fun origin() {
        gantry.position = 1.0
    }

    fun down() {
        down = true
        gantry.position = 0.0
    }

    fun up() {
        down = false
        origin()
    }

    fun toggle() {
        down = if (down) {
            up()
            false
        } else {
            down()
            true
        }
    }
}