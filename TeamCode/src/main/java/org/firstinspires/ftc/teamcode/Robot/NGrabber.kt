package org.firstinspires.ftc.teamcode.Robot

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.openftc.revextensions2.ExpansionHubServo

class NGrabber constructor(Op : OpMode) {
    val Grabber : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "grabber")
    val RotateGrabber : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "rotateGrabber")
    val RotateAssembly : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "rotateAssembly")
    val Horn : ExpansionHubServo = Op.hardwareMap.get(ExpansionHubServo::class.java, "push")

    var offset2 = ORIENTATIONS.VERTICAl

    enum class ORIENTATIONS(val offset : Double){
        HORIZONTAL(.77),
        VERTICAl(.45),
        COLLECTION(-.235),
        CLAMP(.23),
        TRANSITION(.25)
    }


}