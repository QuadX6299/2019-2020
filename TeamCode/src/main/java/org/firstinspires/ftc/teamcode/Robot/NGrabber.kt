package org.firstinspires.ftc.teamcode.Robot

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.openftc.revextensions2.ExpansionHubServo

class NGrabber constructor(Op : OpMode, moveOnInit : Boolean = true) {
    val Grabber : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "grabber")
    val RotateGrabber : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "rotateGrabber")
    val RotateAssembly : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "rotateAssembly")
    val Horn : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "push")

    var offset = ORIENTATIONS.VERTICAL
    var hornDown = false
    var grabberDown = false

    init {
        if (moveOnInit) {
            resetPositions()
            setAssemblyPosition(POSITIONS.COLLECTION)
        }
    }


    enum class ORIENTATIONS(val difference : Double) {
        HORIZONTAL(.77),
        VERTICAL(.45),
        COLLECTION(-.235),
        CLAMP(.23),
        TRANSITION(.25)
    }

    enum class POSITIONS {
        COLLECTION,
        TRANSITION,
        HORIZONTALDEPO,
        HORNDOWN,
        HORNUP,
        VERTICALDEPO,
        CLAMPDOWN,
        DROP,
        CAP
    }

    fun setAssemblyPosition(pose : POSITIONS) {
        when (pose) {
            POSITIONS.COLLECTION -> {
                offset = ORIENTATIONS.COLLECTION
                setManipulatorPosition(.71)
            }
            POSITIONS.TRANSITION -> {
                setManipulatorPosition(.25)
            }
            POSITIONS.VERTICALDEPO -> {
                offset = ORIENTATIONS.VERTICAL
                setManipulatorPosition(.2)
            }
            POSITIONS.HORIZONTALDEPO -> {
                offset = ORIENTATIONS.HORIZONTAL
                setManipulatorPosition(.2)
            }
            /* Clamp Position Switch */
            POSITIONS.CLAMPDOWN -> {
                Grabber.position = .23
            }
            POSITIONS.DROP -> {
                Grabber.position = 0.0
            }
            /* Horn Position Switch */
            POSITIONS.HORNDOWN -> {
                Horn.position = 0.0
            }
            POSITIONS.HORNUP -> {
                Horn.position = 1.0
            }
            POSITIONS.CAP -> {
                RotateAssembly.position = .23
                RotateGrabber.position = 0.28
            }
        }
    }

    fun setManipulatorPosition(pos: Double){
        RotateAssembly.position = pos
        RotateGrabber.position = RotateAssembly.position + offset.difference
    }

    fun resetPositions() {
        setAssemblyPosition(POSITIONS.DROP)
        setAssemblyPosition(POSITIONS.COLLECTION)
    }

    fun toggleHorn() {
        if (hornDown) {
            hornDown = false
            setAssemblyPosition(POSITIONS.HORNUP)
        } else {
            hornDown = true
            setAssemblyPosition(POSITIONS.HORNDOWN)
        }
    }

    fun toggleGrabber() {
        if (grabberDown) {
            grabberDown = false
            setAssemblyPosition(POSITIONS.DROP)
        } else {
            grabberDown = true
            setAssemblyPosition(POSITIONS.CLAMPDOWN)
        }
    }
}