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

@TeleOp(name = "test", group = "TeleOp")
public class TeleOPTEST extends OpMode {

    BitMap vision;
    ElapsedTime i;
    public void init() {
        Robot.init(this);
    }

    public void loop() {
//        DriveTrain.turnPID(0.01, 0.0, 0.0, true, 90, 1500);
        if (gamepad1.x) {
            while (gamepad1.x) {
                Bhorn.toggle();
            }
        }
    }
}