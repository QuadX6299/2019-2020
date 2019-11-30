package org.firstinspires.ftc.teamcode.Robot

import android.os.Handler
import android.os.Looper
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.util.ElapsedTime

class NRobot constructor(val opMode: OpMode) {

    companion object Modules {
        lateinit var DriveTrain : NDriveTrain
        lateinit var Grabber : NGrabber
        lateinit var Intake : NIntake
        lateinit var Lift : NLift
        lateinit var FoundationHook : NFoundationHook
        lateinit var OSAsync : Handler

        fun init(Op : OpMode) {
            DriveTrain = NDriveTrain(Op)
            Grabber = NGrabber(Op)
            Intake = NIntake(Op)
            Lift = NLift(Op)
            FoundationHook = NFoundationHook(Op)
            OSAsync = Handler(Looper.getMainLooper())
        }
    }

    init {
        init(opMode)
    }

    fun reset() {
        init(opMode)
    }

    fun g2() {
        if (opMode.gamepad2.dpad_up && opMode.gamepad2.timestamp > 50) {
            Grabber.toggleHorn()
        } else if (opMode.gamepad2.dpad_down && opMode.gamepad2.timestamp > 50) {
            Grabber.toggleGrabber()
        } else if (opMode.gamepad2.y && opMode.gamepad2.timestamp > 50) {
            Grabber.setAssemblyPosition(NGrabber.POSITIONS.HORNDOWN)
            OSAsync.postDelayed({
                Grabber.setAssemblyPosition(NGrabber.POSITIONS.CLAMPDOWN)
            }, 1000)
        }
        //TODO FINISH NRobot.kt
    }
}