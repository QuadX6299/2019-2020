package org.firstinspires.ftc.teamcode.Modules

import android.os.Handler
import android.os.Looper
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.openftc.revextensions2.ExpansionHubServo

class Gantry constructor(Op : OpMode) {
    val gantry : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "gantry")
    val frontClamp: ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "frontClamp")
    val backClamp: ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "backClamp")


    var out : Boolean = false

    var frontClampDown : Boolean = false
    var backClampDown : Boolean = true

    init {
        origin()
    }

    fun origin() {
        gantryIn()
        Handler(Looper.getMainLooper()).postDelayed({
            backClampClose()
        }, 700)
    }


    enum class POSITIONS {
        COLLECTION,
        TRANSITION,
        GANTRYOUT,
        DEPOSIT
    }

    fun setAssemblyPosition(pose : POSITIONS) {
        when (pose) {
            POSITIONS.COLLECTION -> {
                origin()
            }
            POSITIONS.TRANSITION -> {
                frontClampClose()
            }
            POSITIONS.GANTRYOUT -> {
                gantryOut()
            }
            POSITIONS.DEPOSIT -> {
                backClampOpen()
            }



        }
    }

    fun frontClampOpen(){
        frontClampDown = false
        frontClamp.position = .52
    }

    fun frontClampClose(){
        frontClampDown = true
        frontClamp.position = 0.1
    }

    fun backClampOpen(){
        backClampDown = false
        backClamp.position = .1
    }

    fun backClampClose(){
        backClampDown = true
        backClamp.position = 0.56
    }

    fun gantryOut() {
        gantry.position = .235
    }

    fun gantryIn() {
        gantry.position = .8
    }

    fun toggleFrontClamp() {
        if (frontClampDown) {
            frontClampDown = false
            frontClampOpen()
        } else {
            frontClampDown = true
            frontClampClose()
        }
    }

    fun toggleBackClamp() {
        if (backClampDown) {
            backClampDown = false
            backClampOpen()
        } else {
            backClampDown = true
            backClampClose()
        }
    }


}