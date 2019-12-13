package org.firstinspires.ftc.teamcode.Robot

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.openftc.revextensions2.ExpansionHubServo

class NAutoBlockGrabbers constructor(Op : OpMode) {
    val BlockGrabberLeft : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "BlockGrabberLeft")
    val BlockGrabberRight : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "BlockGrabberRight")

    var down : Boolean = false

    init {
        origin()
    }

    fun origin() {
        BlockGrabberLeft.position = 1.0
        BlockGrabberRight.position = 0.0
    }

    fun down() {
        down = true
        BlockGrabberLeft.position = 0.0
        BlockGrabberRight.position = 1.0
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