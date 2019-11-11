package org.firstinspires.ftc.teamcode.Robot

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.lib.Extensions.fuzzyEquals



object Grabber{
    lateinit var grabber : Servo
    lateinit var rotateGrabber : Servo
    lateinit var rotateAssembly : Servo
    lateinit var pushThrough : Servo

    const val start = 0.0
    const val end = 0.6
    const val verticalOffset = .45 //was.45
    private const val horizontalOffset = .77 //was .13
    private const val collectionOffset = -.235 //was -.25
    private const val clampPosition = .23
    private const val transitionPose = .25

    var offset = verticalOffset
    var isClamped = false
    var isHORNED = false


    @JvmStatic fun init(op : OpMode) {
        grabber = op.hardwareMap.get(Servo::class.java, "grabber")
        rotateGrabber = op.hardwareMap.get(Servo::class.java, "rotateGrabber")
        rotateAssembly = op.hardwareMap.get(Servo::class.java, "rotateAssembly")
        pushThrough = op.hardwareMap.get(Servo::class.java, "push")
//        setPosition(POSITIONS.COLLECTION)
        setPosition(POSITIONS.RETURNPUSH)
        setPosition(POSITIONS.DROP)
        isClamped = false
    }

    enum class POSITIONS{
        COLLECTION,
        TRANSITION,
        HORIZONTALDEPO,
        PUSHTHROUGH,
        RETURNPUSH,
        ZERO,
        CLAMPDOWN,
        DROP
    }

    enum class STATES{
        ORIGIN,
        COLLECTION,
        DEPOSIT,
        HORIZONTALDEPOSIT,
        RETURN,
        HORNUP
    }

    @JvmStatic fun setPosition(stage: POSITIONS){
        when (stage){
            POSITIONS.COLLECTION -> {
                offset = collectionOffset
                setGrabberPosition(0.71)
            }
            POSITIONS.TRANSITION -> {

                setGrabberPosition(transitionPose)
            }
            POSITIONS.ZERO -> {
                offset = verticalOffset
                setGrabberPosition(0.2)
            }
            POSITIONS.HORIZONTALDEPO -> {
                offset = horizontalOffset
                setGrabberPosition(.2)
            }
            POSITIONS.RETURNPUSH ->{
                setPusherPosition(1.0)
            }
            POSITIONS.PUSHTHROUGH -> {
                setPusherPosition(0.0)
            }
            POSITIONS.CLAMPDOWN -> {
                grabber.position = clampPosition
            }
            POSITIONS.DROP -> {
                grabber.position = 0.0
            }
            else -> {
                offset = verticalOffset
                setGrabberPosition(0.0)
            }
        }
    }

    @JvmStatic fun setPusherPosition(pos: Double) {
        pushThrough.position = pos
    }

    @JvmStatic fun setGrabberPosition(pos: Double){
        rotateAssembly.position = pos
        rotateGrabber.position = rotateAssembly.position + offset
    }

    @JvmStatic fun toggle() {
        if(isClamped) {
            isClamped = false
            setPosition(POSITIONS.DROP)
        } else {
            isClamped = true
            setPosition(POSITIONS.CLAMPDOWN)
        }
    }

    @JvmStatic fun toggleHorn() {
        if (isHORNED) {
            isHORNED = false
            setPosition(POSITIONS.RETURNPUSH)
        } else {
            isHORNED = true
            setPosition(POSITIONS.PUSHTHROUGH)
        }
    }

    @JvmStatic fun origin(grabberPos : Double, rotateGrabberPos : Double, rotateAssemblyPos : Double) {
        grabber.position = grabberPos
        rotateGrabber.position = rotateGrabberPos
        rotateAssembly.position = rotateAssemblyPos
    }
}

