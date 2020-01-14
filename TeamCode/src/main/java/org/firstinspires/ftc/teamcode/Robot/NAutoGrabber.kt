package org.firstinspires.ftc.teamcode.Robot

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.openftc.revextensions2.ExpansionHubServo

class NAutoGrabber constructor(Op: OpMode) {
    val grabber : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "grabber")
    val rotator : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "rotate")

    init {
        store()
    }

    fun store() {
        grabber.position = 0.8
        rotator.position = 0.0
    }

    fun clamp() {
        rotator.position = 1.0
        grabber.position = .6
    }

    fun rotatorDown() {
        rotator.position = 1.0
        grabber.position = .2
    }

    fun dropBlock() {
        grabber.position = 0.2
    }

    fun grabberDown() {
        grabber.position = .62
    }

    fun hold() {
        rotator.position = .6
    }

}