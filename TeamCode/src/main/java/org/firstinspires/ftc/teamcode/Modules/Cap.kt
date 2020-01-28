package org.firstinspires.ftc.teamcode.Modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.openftc.revextensions2.ExpansionHubServo

class Cap constructor(Op : OpMode) {

    val Cap : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "cap")

    var down : Boolean = false

    init {
        origin()
    }

    fun origin() {
        Cap.position = 0.0

    }

    fun down() {
        down = true
        Cap.position = 0.7

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