package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HerculesLibraries.Vision.NewBitMap;
import org.firstinspires.ftc.teamcode.Robot.NDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.NDriveTrain4Mecanum;
import org.firstinspires.ftc.teamcode.Robot.NRobot;

public class DriveTest extends LinearOpMode {

    private ElapsedTime t;
    double lastT = 0.0;
    NRobot Robot;
    private String skyStonePosition = "C";
    //    Handler dispatcher;
    double kP = 0.001;
    double kI = 0.0;
    double kD = 0.0;

    @Override
    public void runOpMode() throws InterruptedException {

        Robot = new NRobot(this);
        t = new ElapsedTime();
//
        Robot.reset();

        while (!isStarted()) {
            skyStonePosition = NewBitMap.blueVision();
            telemetry.addData("Skystone Position: ", skyStonePosition);
            telemetry.update();
        }
        waitForStart();

        NRobot.DriveTrain.moveEncoder(0.5,10.0,2000);


    }
}
