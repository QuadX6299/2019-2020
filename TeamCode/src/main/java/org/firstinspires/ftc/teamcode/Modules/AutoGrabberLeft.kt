package org.firstinspires.ftc.teamcode.Modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.openftc.revextensions2.ExpansionHubServo

class AutoGrabberLeft constructor(Op: OpMode) {
    val grabberLeft: ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "grabberLeft")
    val rotatorLeft: ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "rotatorLeft")


    init {
        store()
    }

    fun store() {
        grabberLeft.position = 1.0
        rotatorLeft.position = 1.0
    }

    fun clamp() {
        rotatorLeft.position = 1.0
        grabberLeft.position = .48
    }

    fun rotatorDown() {
        rotatorLeft.position = 0.0
        grabberLeft.position = 0.0
    }

    fun dropBlock() {
        grabberLeft.position = 0.0
    }

    fun grabberDown() {
        grabberLeft.position = .48
    }

    fun hold() {
        rotatorLeft.position = .57
    }

}