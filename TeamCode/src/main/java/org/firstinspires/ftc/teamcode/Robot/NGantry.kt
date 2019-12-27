package org.firstinspires.ftc.teamcode.Robot

import android.os.Handler
import android.os.Looper
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.openftc.revextensions2.ExpansionHubServo

class NGantry constructor(Op : OpMode) {
    val gantry : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "gantry")
    val frontClamp: ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "frontClamp")
    val backClamp: ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "backClamp")


    var out : Boolean = false

    init {
        origin()
    }

    fun origin() {
        backClampClose()
        gantryIn()
        Handler(Looper.getMainLooper()).postDelayed({
            frontClampOpen()
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
        frontClamp.position = .52
    }

    fun frontClampClose(){
        frontClamp.position = 0.1
    }

    fun backClampOpen(){
        backClamp.position = .1
    }

    fun backClampClose(){
        backClamp.position = 0.56
    }

    fun gantryOut() {
        gantry.position = .1
    }

    fun gantryIn() {
        gantry.position = .8
    }




}