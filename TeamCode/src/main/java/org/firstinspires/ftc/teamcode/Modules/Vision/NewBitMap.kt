package org.firstinspires.ftc.teamcode.Modules.Vision

import android.graphics.Bitmap

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.vuforia.Frame
import com.vuforia.Image
import com.vuforia.PIXEL_FORMAT
import com.vuforia.Vuforia

import org.firstinspires.ftc.robotcore.external.ClassFactory
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.Parameters

import java.util.ArrayList
import java.util.Collections
import java.util.concurrent.BlockingQueue

import android.graphics.Color.blue
import android.graphics.Color.green
import android.graphics.Color.red
import com.qualcomm.robotcore.eventloop.opmode.OpMode

class NewBitMap constructor(val opMode: OpMode) {


    // importing vuforia class for taking an image
    lateinit var vuforia: VuforiaLocalizer



    init {
        val cameraMonitorViewId = opMode.hardwareMap.appContext.resources.getIdentifier("cameraMonitorViewId", "id", opMode.hardwareMap.appContext.packageName)

        val params = VuforiaLocalizer.Parameters(cameraMonitorViewId)

        params.vuforiaLicenseKey = "AQvLCbX/////AAABmTGnnsC2rUXvp1TAuiOSac0ZMvc3GKI93tFoRn4jPzB3uSMiwj75PNfUU6MaVsNZWczJYOep8LvDeM/3hf1+zO/3w31n1qJTtB2VHle8+MHWNVbNzXKLqfGSdvXK/wYAanXG2PBSKpgO1Fv5Yg27eZfIR7QOh7+J1zT1iKW/VmlsVSSaAzUSzYpfLufQDdE2wWQYrs8ObLq2kC37CeUlJ786gywyHts3Mv12fWCSdTH5oclkaEXsVC/8LxD1m+gpbRc2KC0BXnlwqwA2VqPSFU91vD8eCcD6t2WDbn0oJas31PcooBYWM6UgGm9I2plWazlIok72QG/kOYDh4yXOT4YXp1eYh864e8B7mhM3VclQ"
        params.cameraName = opMode.hardwareMap.get(WebcamName::class.java, "Webcam 1")
        vuforia = ClassFactory.getInstance().createVuforia(params)
        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true) //enables RGB565 format for the image
        vuforia.frameQueueCapacity = 4 //tells VuforiaLocalizer to only store one frame at a time
        vuforia.enableConvertFrameToBitmap()
    }

    @Throws(InterruptedException::class)
    fun getImage() : Bitmap {

        val frame = vuforia.frameQueue.take()
        var rgb: Image = frame.getImage(1)
        val numImages = frame.numImages


        for (i in 0 until numImages) {
            val fmt = frame.getImage(i.toInt()).format
            if (fmt == PIXEL_FORMAT.RGB565) {
                rgb = frame.getImage(i.toInt())
                break
            }
        }
        val bm = Bitmap.createBitmap(rgb.width, rgb.height, Bitmap.Config.RGB_565)
        bm.copyPixelsFromBuffer(rgb.pixels)

//        opMode.telemetry.addData("Image width", bm.getWidth())
//        opMode.telemetry.addData("Image height", bm.getHeight())
//        opMode.telemetry.update()

        frame.close()

        return bm
    }

    fun getImageHeight():Double{
        return getImage().height.toDouble()
    }

    fun getImageWeidth():Double{
        return getImage().width.toDouble()
    }


    @Throws(InterruptedException::class)
    fun blueVision(): String {
        val bitmap = getImage()
        val xValues = ArrayList<Int>()
        //x : 800
        //y : 448




        //counters for whether a pixel is gold, otherwise assign it to black
        var black = 0
        var gold = 0
        val left: Int = (200 * .625).toInt()
        val right: Int = (900 * .625).toInt()
        val bottom: Int = (110 * .625).toInt()
        val top: Int = (0 * .625).toInt()
        //col range 145..830
        //row range 110..320
        for (colNum in left..right) {

            // scan rows 120-240 to block out cubes from crater
            //NEED TO TEST WHICH ROWS TO LOOP THROUGH TO GET MOST EFFICIENT RESULT
            for (rowNum in top..bottom) {
                val pixel = bitmap.getPixel(colNum, rowNum)

                // receive R, G, and B values for each pixel
                val redPixel = red(pixel)
                val greenPixel = green(pixel)
                val bluePixel = blue(pixel)

                //checking if the pixel meets the thresholds to be assigned a gold value
                if (redPixel <= 25 && greenPixel <= 25 && bluePixel <= 25) {
                    black++
                    xValues.add(colNum)
                }
            }

        }

        var avgX = 0.0
        for (x in xValues) {
            avgX += x
        }
//
        avgX /= xValues.size


        //assigning a boolean that determines whether or not the specific frame is black
        //if there are more black pixels, it is black, otherwise it's gold
        //BLACK IS TRUE, GOLD IS FALSE


        if (avgX <= 220){
            return "L"
        } else if (avgX > 220 && avgX <=400){
            return  "C"
        } else if (avgX > 400){
            return "R"
        }

        return "C"
    }

    @Throws(InterruptedException::class)
    fun getAvgXRed(): Double {
        val bitmap = getImage()
        val xValues = ArrayList<Int>()
        //x : 800
        //y : 448




        //counters for whether a pixel is gold, otherwise assign it to black
        var black = 0
        var gold = 0
        val left: Int = (25 * .625).toInt()
        val right: Int = (730 * .625).toInt()
        val bottom: Int = (100 * .625).toInt()
        val top: Int = (10 * .625).toInt()
        //col range 145..830
        //row range 110..320
        for (colNum in left..right) {

            // scan rows 120-240 to block out cubes from crater
            //NEED TO TEST WHICH ROWS TO LOOP THROUGH TO GET MOST EFFICIENT RESULT
            for (rowNum in top..bottom) {
                val pixel = bitmap.getPixel(colNum, rowNum)

                // receive R, G, and B values for each pixel
                val redPixel = red(pixel)
                val greenPixel = green(pixel)
                val bluePixel = blue(pixel)

                //checking if the pixel meets the thresholds to be assigned a gold value
                if (redPixel <= 25 && greenPixel <= 25 && bluePixel <= 25) {
                    black++
                    xValues.add(colNum)
                }
            }

        }

        var avgX = 0.0
        for (x in xValues) {
            avgX += x
        }
//
        avgX /= xValues.size


        //assigning a boolean that determines whether or not the specific frame is black
        //if there are more black pixels, it is black, otherwise it's gold
        //BLACK IS TRUE, GOLD IS FALSE




        return avgX
    }


    @Throws(InterruptedException::class)
    fun redVision(): String {
        val bitmap = getImage()
        val xValues = ArrayList<Int>()
        //x : 800
        //y : 448




        //counters for whether a pixel is gold, otherwise assign it to black
        var black = 0
        var gold = 0
        val left: Int = (25 * .625).toInt()
        val right: Int = (730 * .625).toInt()
        val bottom: Int = (100 * .625).toInt()
        val top: Int = (10 * .625).toInt()
        //col range 145..830
        //row range 110..320
        for (colNum in left..right) {

            // scan rows 120-240 to block out cubes from crater
            //NEED TO TEST WHICH ROWS TO LOOP THROUGH TO GET MOST EFFICIENT RESULT
            for (rowNum in top..bottom) {
                val pixel = bitmap.getPixel(colNum, rowNum)

                // receive R, G, and B values for each pixel
                val redPixel = red(pixel)
                val greenPixel = green(pixel)
                val bluePixel = blue(pixel)

                //checking if the pixel meets the thresholds to be assigned a gold value
                if (redPixel <= 25 && greenPixel <= 25 && bluePixel <= 25) {
                    black++
                    xValues.add(colNum)
                }
            }

        }

        var avgX = 0.0
        for (x in xValues) {
            avgX += x
        }
//
        avgX /= xValues.size


        //assigning a boolean that determines whether or not the specific frame is black
        //if there are more black pixels, it is black, otherwise it's gold
        //BLACK IS TRUE, GOLD IS FALSE


        if (avgX <= 100){
            return "L"
        } else if (avgX > 100 && avgX <= 350){
            return  "C"
        } else if (avgX > 350){
            return "R"
        }
        return "C"

//        return avgX
    }

    @Throws(InterruptedException::class)
    fun getAvgXBlue(): Double {
        val bitmap = getImage()
        val xValues = ArrayList<Int>()
        //x : 800
        //y : 448




        //counters for whether a pixel is gold, otherwise assign it to black
        var black = 0
        var gold = 0
        val left: Int = (200 * .625).toInt()
        val right: Int = (900 * .625).toInt()
        val bottom: Int = (110 * .625).toInt()
        val top: Int = (0 * .625).toInt()
        //col range 145..830
        //row range 110..320
        for (colNum in left..right) {

            // scan rows 120-240 to block out cubes from crater
            //NEED TO TEST WHICH ROWS TO LOOP THROUGH TO GET MOST EFFICIENT RESULT
            for (rowNum in top..bottom) {
                val pixel = bitmap.getPixel(colNum, rowNum)

                // receive R, G, and B values for each pixel
                val redPixel = red(pixel)
                val greenPixel = green(pixel)
                val bluePixel = blue(pixel)

                //checking if the pixel meets the thresholds to be assigned a gold value
                if (redPixel <= 25 && greenPixel <= 25 && bluePixel <= 25) {
                    black++
                    xValues.add(colNum)
                }
            }

        }

        var avgX = 0.0
        for (x in xValues) {
            avgX += x
        }
//
        avgX /= xValues.size


        //assigning a boolean that determines whether or not the specific frame is black
        //if there are more black pixels, it is black, otherwise it's gold
        //BLACK IS TRUE, GOLD IS FALSE




        return avgX
    }





    fun vufConvertToBitmap(frame: Frame): Bitmap? {
        return vuforia.convertFrameToBitmap(frame)
    }

}