package org.firstinspires.ftc.teamcode.Robot

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.openftc.revextensions2.ExpansionHubServo

class NAutoGrabber constructor(Op: OpMode) {
    val grabber : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "blockpicker")
    val rotator : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "rotate")

    init {
        up()
    }

    fun down() {
        grabber.position = 0.0
        rotator.position = 0.0
    }

    fun up() {
        rotator.position = 1.0
        grabber.position = 1.0
    }
}