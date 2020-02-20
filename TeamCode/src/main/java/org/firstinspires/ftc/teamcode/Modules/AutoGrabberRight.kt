package org.firstinspires.ftc.teamcode.Modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.openftc.revextensions2.ExpansionHubServo

class AutoGrabberRight constructor(Op: OpMode) {
    val grabberRight : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "grabber")
    val rotatorRight : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "rotate")


    init {
        store()
    }

    fun store() {
        grabberRight.position = 0.0
        rotatorRight.position = 0.0
    }

    fun clamp() {
        grabberRight.position = .1
    }

    fun rotatorDown() {
        rotatorRight.position = .75
        grabberRight.position = .69
    }

    fun dropBlock() {
        grabberRight.position = 0.7
    }

    fun hold() {
        rotatorRight.position = .45
    }

    fun tilt() {
        rotatorRight.position = .6
    }

}