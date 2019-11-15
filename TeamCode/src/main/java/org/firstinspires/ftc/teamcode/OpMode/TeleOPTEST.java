package org.firstinspires.ftc.teamcode.OpMode;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HerculesLibraries.Vision.BitMap;
import org.firstinspires.ftc.teamcode.HerculesLibraries.Vision.NewBitMap;
import org.firstinspires.ftc.teamcode.Robot.Bhorn;
import org.firstinspires.ftc.teamcode.Robot.DriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Grabber;
import org.firstinspires.ftc.teamcode.Robot.Lift;
import org.firstinspires.ftc.teamcode.Robot.Sensors.IMU;
import org.firstinspires.ftc.teamcode.Robot.Sensors.RangeSensor;
import org.firstinspires.ftc.teamcode.Robot.StackGrabbers;

@TeleOp(name = "ClampTest", group = "TeleOp")
public class TeleOPTEST extends OpMode {


    public void init() {
        StackGrabbers.init(this);
    }

    public void loop() {
        if (gamepad1.a) {
            while (gamepad1.a) {
                StackGrabbers.toggle();
            }
        }
    }
}