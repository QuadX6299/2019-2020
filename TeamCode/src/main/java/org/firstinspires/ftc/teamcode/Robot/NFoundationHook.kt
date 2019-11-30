package org.firstinspires.ftc.teamcode.Robot

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.openftc.revextensions2.ExpansionHubServo

class NFoundationHook constructor(Op : OpMode) {
    val HookLeft : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "leftHook")
    val HookRight : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "rightHook")

    var down : Boolean = false

    init {
        origin()
    }

    fun origin() {
        HookLeft.position = 1.0
        HookRight.position = 0.0
    }

    fun down() {
        down = true
        HookLeft.position = 0.0
        HookRight.position = 1.0
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