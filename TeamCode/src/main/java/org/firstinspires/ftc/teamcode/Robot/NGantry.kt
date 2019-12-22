package org.firstinspires.ftc.teamcode.Robot

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
        gantry.position = 0.0
        frontClampOpen()
        backClampClose()
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
        frontClamp.position = 1.0
    }

    fun frontClampClose(){
        frontClamp.position = 0.0
    }

    fun backClampOpen(){
        frontClamp.position = 1.0
    }

    fun backClampClose(){
        frontClamp.position = 0.0
    }

    fun gantryOut() {
        gantry.position = 1.0
    }




}