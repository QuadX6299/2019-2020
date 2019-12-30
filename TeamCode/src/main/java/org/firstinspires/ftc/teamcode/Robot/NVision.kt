package org.firstinspires.ftc.teamcode.Robot

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo
import com.vuforia.Image
import com.vuforia.PIXEL_FORMAT
import com.vuforia.Vuforia
import org.firstinspires.ftc.robotcore.external.ClassFactory
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer
import org.firstinspires.ftc.teamcode.HerculesLibraries.Vision.NewBitMap
import java.util.ArrayList

class NVision constructor(Op : OpMode) {
    lateinit var vuforia: VuforiaLocalizer
    lateinit var opMode: OpMode

    init {

        NewBitMap.opMode = Op
        val cameraMonitorViewId = NewBitMap.opMode.hardwareMap.appContext.resources.getIdentifier("cameraMonitorViewId", "id", NewBitMap.opMode.hardwareMap.appContext.packageName)

        val params = VuforiaLocalizer.Parameters(cameraMonitorViewId)

        params.vuforiaLicenseKey = "AQvLCbX/////AAABmTGnnsC2rUXvp1TAuiOSac0ZMvc3GKI93tFoRn4jPzB3uSMiwj75PNfUU6MaVsNZWczJYOep8LvDeM/3hf1+zO/3w31n1qJTtB2VHle8+MHWNVbNzXKLqfGSdvXK/wYAanXG2PBSKpgO1Fv5Yg27eZfIR7QOh7+J1zT1iKW/VmlsVSSaAzUSzYpfLufQDdE2wWQYrs8ObLq2kC37CeUlJ786gywyHts3Mv12fWCSdTH5oclkaEXsVC/8LxD1m+gpbRc2KC0BXnlwqwA2VqPSFU91vD8eCcD6t2WDbn0oJas31PcooBYWM6UgGm9I2plWazlIok72QG/kOYDh4yXOT4YXp1eYh864e8B7mhM3VclQ"
        params.cameraName = Op.hardwareMap.get(WebcamName::class.java, "Webcam 1")
        NewBitMap.vuforia = ClassFactory.getInstance().createVuforia(params)
        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true) //enables RGB565 format for the image
        NewBitMap.vuforia.frameQueueCapacity = 4 //tells VuforiaLocalizer to only store one frame at a time
        NewBitMap.vuforia.enableConvertFrameToBitmap()
    }

}