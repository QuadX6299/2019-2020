package org.firstinspires.ftc.teamcode.OpMode;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HerculesLibraries.Vision.BitMap;
import org.firstinspires.ftc.teamcode.HerculesLibraries.Vision.NewBitMap;
import org.firstinspires.ftc.teamcode.Robot.DriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Grabber;
import org.firstinspires.ftc.teamcode.Robot.Lift;

@TeleOp(name = "Vision", group = "TeleOp")
public class TeleOPVision extends OpMode {


    ElapsedTime i;
    public void init() {
        Robot.init(this);
        i = new ElapsedTime(0);
        i.reset();
    }

    public void loop() {
        if (i.milliseconds() > 500) {
            try {
                telemetry.addData("Position: ", NewBitMap.isShriggaRed());
                telemetry.update();
            } catch (InterruptedException e) {

            }

        }


    }
}