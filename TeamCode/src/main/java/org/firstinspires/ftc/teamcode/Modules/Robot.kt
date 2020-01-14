package org.firstinspires.ftc.teamcode.Modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode

class Robot constructor(val opMode: OpMode) {
    var flip = 1.0

    companion object Modules {
        fun init(Op: OpMode) {

        }
    }

    init {
        flip = 1.0
        Modules.init(opMode)
    }

    fun reset() {
        Modules.init(opMode)
    }
}