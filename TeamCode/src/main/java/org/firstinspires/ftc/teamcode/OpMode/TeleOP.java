package org.firstinspires.ftc.teamcode.OpMode;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HerculesLibraries.Vision.BitMap;
import org.firstinspires.ftc.teamcode.Robot.DriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Grabber;
import org.firstinspires.ftc.teamcode.Robot.Lift;

@TeleOp(name = "TeleOp", group = "TeleOp")
public class TeleOP extends OpMode {
//    boolean ooga = true;
//    BitMap vision;
//    Bitmap b;
    public void init() {
        Robot.init(this);
//        vision = new BitMap(this);
//        try {
//            b = vision.bitmap();
//        } catch (Exception ignored) {
//
//        }
    }

    public void loop() {
//        Grabber.origin();
        Robot.lift();
        Robot.intake();
        Robot.Gamepad1Controls();
        Robot.Gamepad2Controls();
        Robot.sixArcadeArc();
//        telemetry.addData("Encoder Ticks: ", Lift.liftMotor.getCurrentPosition());
//        telemetry.addData("In: ", Lift.liftMotor.getCurrentPosition()/Lift.cpi);/
//            telemetry.addData("X: ", b.getWidth());
//            telemetry.addData("Y: ", b.getHeight());
    }

    @Override
    public void stop() {
        Robot.cancelOdometry();
        super.stop();
    }
}