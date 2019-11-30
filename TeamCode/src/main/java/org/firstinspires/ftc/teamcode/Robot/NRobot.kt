package org.firstinspires.ftc.teamcode.Robot

import android.graphics.Path
import com.qualcomm.robotcore.eventloop.opmode.OpMode

class NRobot constructor(val Op : OpMode) {

    companion object Modules {
        lateinit var DriveTrain : NDriveTrain

        fun init(Op : OpMode) {
            DriveTrain = NDriveTrain(Op)
        }
    }

    init {
        init(Op)
    }


}