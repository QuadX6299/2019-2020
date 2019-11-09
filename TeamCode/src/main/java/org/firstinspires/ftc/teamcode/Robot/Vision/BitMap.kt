package org.firstinspires.ftc.teamcode.HerculesLibraries.Vision

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

import junit.framework.Test


class BitMap// constructor turns on webcam imaging and sets capacity and format for frame
(private val opMode: OpMode) {

    // importing vuforia class for taking an image
    private val vuforia: VuforiaLocalizer
    companion object {
        // RGB values for finding yellow pixels
        const val RED_THRESHOLD = 140
        const val GREEN_THRESHOLD = 100
        const val BLUE_THRESHOLD = 60

        const val BL_RED = 160
        const val BL_GREEN = 160
        const val BL_BLUE = 160
    }

    // method to actually capture frame
    // go through all images taken in frame and find ones that match correct format
    // create bitmap
    @Throws(InterruptedException::class)
    fun bitmap() : Bitmap {
        val frame = NewBitMap.vuforia.frameQueue.take()
        val numImages = frame.numImages
        var rgb: Image? = null
        for (i in 0 until numImages) {
            val img = frame.getImage(i.toInt())
            val fmt = img.format
            if (fmt == PIXEL_FORMAT.RGB565) {
                rgb = frame.getImage(i.toInt())
                break
            }
        }
        val bm = Bitmap.createBitmap(rgb!!.width, rgb.height, Bitmap.Config.RGB_565)
        bm.copyPixelsFromBuffer(rgb.pixels)
        return bm
        }

    init {

        // variable allows image to show up on robot controller phone
        val cameraMonitorViewId = opMode.hardwareMap.appContext.resources.getIdentifier("cameraMonitorViewId", "id", opMode.hardwareMap.appContext.packageName)

        val parameters = Parameters(cameraMonitorViewId)

        // original vuforia license key
        parameters.vuforiaLicenseKey = "AcD8BwX/////AAABmQfyyiD3b0tXiwsm/UX+fHkiPPZJQu55dY7HGrCBT84yc2dP8K+9mWY/3l3gcOKEmSvG+xB9UTPZRTzLqONEuj4hrYpRZtfz6wDkC4IWUvxdgh3+On8UHBaue+CJveRpqla8XZtgMJUqzE3Mxt4QBk3SFkh815rM08JJ11a4XsZrxD4ZDVI6XcsrBmWFub8E/+weoU5gweajvJcE5tzVyLn7IaaYyshx9CHJdS0ObM29e3tHbVJjpwsU/zuoEEoXNRUL++LR0j8z6KY7WQvnsf0PyZXIpu6/tvFR1/WMn74Rc7IkWdO3sdiRQL3i96/rhOeAvQfjlg1VJhEyWKXqqLfQSJrOQSCKegayB4KFCXZf"

        // hardware mapping of webcam device
        parameters.cameraName = opMode.hardwareMap.get(WebcamName::class.java, "Webcam 1")

        // start vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters)

        // set RGB format to 565
        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true)

        // allowing the frame to only be 4 images at a time
        vuforia.frameQueueCapacity = 4

    }

    @Throws(InterruptedException::class)
    fun isShrigga(): Boolean {
        val bitmap = bitmap()
        val xValues = ArrayList<Int>()
        //x : 800
        //y : 448


        //counters for whether a pixel is gold, otherwise assign it to black
        var black = 0
        var gold = 0
        val left : Int = (740*.416).toInt()
        val right : Int = (1900* .416).toInt()
        val top : Int = (1070*.416).toInt()
        val bottom : Int = (750*.416).toInt()

        for (colNum in left..right) {

            for (rowNum in bottom..top) {
                val pixel = bitmap.getPixel(colNum, rowNum)
                val redPixel = red(pixel)
                val greenPixel = green(pixel)
                val bluePixel = blue(pixel)

                //checking if the pixel meets the thresholds to be assigned a gold value
                if (redPixel >= BL_RED && greenPixel >= BL_GREEN && bluePixel >= BL_BLUE) {
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

        opMode.telemetry.addData("~ Black Block Pos: ", avgX)
        return true

    }


    fun vufConvertToBitmap(frame: Frame): Bitmap? {
        return vuforia.convertFrameToBitmap(frame)
    }


}