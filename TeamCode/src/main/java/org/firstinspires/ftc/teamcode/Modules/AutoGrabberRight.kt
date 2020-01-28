package org.firstinspires.ftc.teamcode.Modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.openftc.revextensions2.ExpansionHubServo

class AutoGrabberRight constructor(Op: OpMode) {
    val grabberRight : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "grabberRight")
    val rotatorRight : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "rotateRight")


    init {
        store()
    }

    fun store() {
        grabberRight.position = 0.8
        rotatorRight.position = 0.0
    }

    fun clamp() {
        rotatorRight.position = 1.0
        grabberRight.position = .6
    }

    fun rotatorDown() {
        rotatorRight.position = 1.0
        grabberRight.position = .2
    }

    fun dropBlock() {
        rotatorRight.position = 0.2
    }

    fun grabberDown() {
        grabberRight.position = .62
    }

    fun hold() {
        rotatorRight.position = .6
    }

}