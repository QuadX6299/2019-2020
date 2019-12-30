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

object NewBitMap {


    // importing vuforia class for taking an image
    lateinit var vuforia: VuforiaLocalizer
    lateinit var opMode: OpMode





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

    @JvmStatic fun getImageHeight():Double{
        return getImage().height.toDouble()
    }

    @JvmStatic fun getImageWeidth():Double{
        return getImage().width.toDouble()
    }


    @Throws(InterruptedException::class)
    @JvmStatic
    fun redVision(): String {
        val bitmap = getImage()
        val xValues = ArrayList<Int>()
        //x : 800
        //y : 448




        //counters for whether a pixel is gold, otherwise assign it to black
        var black = 0
        var gold = 0
        val left: Int = (0 * .416).toInt()
        val right: Int = (1070 * .416).toInt()
        val bottom: Int = (170 * .416).toInt()
        val top: Int = (0 * .416).toInt()
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


        if (avgX <= 375){
            return "L"
        } else if (avgX >= 410 && avgX <= 550){
            return  "C"
        } else if (avgX >= 600){
            return "R"
        }

        return "Cant Find Position"
    }

    @Throws(InterruptedException::class)
    @JvmStatic
    fun getAvgXRed(): Double {
        val bitmap = getImage()
        val xValues = ArrayList<Int>()
        //x : 800
        //y : 448




        //counters for whether a pixel is gold, otherwise assign it to black
        var black = 0
        var gold = 0
        val left: Int = (0 * .416).toInt()
        val right: Int = (1070 * .416).toInt()
        val bottom: Int = (170 * .416).toInt()
        val top: Int = (0 * .416).toInt()
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
    @JvmStatic
    fun blueVision(): String {
        val bitmap = getImage()
        val xValues = ArrayList<Int>()
        //x : 800
        //y : 448




        //counters for whether a pixel is gold, otherwise assign it to black
        var black = 0
        var gold = 0
        val left: Int = (175 * .416).toInt()
        val right: Int = (1300 * .416).toInt()
        val bottom: Int = (200 * .416).toInt()
        val top: Int = (0 * .416).toInt()
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


        if (avgX <= 250){
            return "L"
        } else if (avgX >= 300 && avgX <= 360){
            return  "C"
        } else if (avgX >= 400){
            return "R"
        }
        return "Can't find position"

//        return avgX
    }

    @Throws(InterruptedException::class)
    @JvmStatic
    fun getAvgXBlue(): Double {
        val bitmap = getImage()
        val xValues = ArrayList<Int>()
        //x : 800
        //y : 448




        //counters for whether a pixel is gold, otherwise assign it to black
        var black = 0
        var gold = 0
        val left: Int = (175 * .416).toInt()
        val right: Int = (1300 * .416).toInt()
        val bottom: Int = (200 * .416).toInt()
        val top: Int = (0 * .416).toInt()
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
