package org.firstinspires.ftc.teamcode.Modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.openftc.revextensions2.ExpansionHubServo

class FoundationHooks constructor(Op : OpMode) {
    val HookLeft : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "leftHook")
    val HookRight : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "rightHook")

    var down : Boolean = false

    init {
        origin()
    }

    fun origin() {
        HookLeft.position = 0.33
        HookRight.position = 0.52
    }

    fun hook() {
        HookLeft.position = .5
        HookRight.position = .52
    }

    fun down() {
        down = true
        origin()
    }

    fun up() {
        down = false
        HookLeft.position = 0.7
        HookRight.position = 0.15
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