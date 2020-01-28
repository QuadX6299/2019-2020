package org.firstinspires.ftc.teamcode.Modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.openftc.revextensions2.ExpansionHubServo

class AutoGrabberLeft constructor(Op: OpMode) {
    val grabberLeft: ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "grabberLeft")
    val rotatorLeft: ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "rotateLeft")


    init {
        store()
    }

    fun store() {
        grabberLeft.position = 0.8
        rotatorLeft.position = 0.0
    }

    fun clamp() {
        rotatorLeft.position = 1.0
        grabberLeft.position = .6
    }

    fun rotatorDown() {
        rotatorLeft.position = 1.0
        grabberLeft.position = .2
    }

    fun dropBlock() {
        grabberLeft.position = 0.2
    }

    fun grabberDown() {
        grabberLeft.position = .62
    }

    fun hold() {
        rotatorLeft.position = .6
    }

}