package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HerculesLibraries.Vision.NewBitMap;
import org.firstinspires.ftc.teamcode.Robot.NDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.NDriveTrain4Mecanum;
import org.firstinspires.ftc.teamcode.Robot.NRobot;

@Autonomous(name = "Drive Test", group = "Autonomous")
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

        waitForStart();

        NRobot.DriveTrain.encoderDrive(20, .75);
        NRobot.DriveTrain.encoderDriveStrafe(20,.75,true);
        NRobot.DriveTrain.turnPID(.2, false,(Math.PI) / 2.0);

    }
}
