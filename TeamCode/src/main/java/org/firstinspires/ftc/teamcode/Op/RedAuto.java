package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HerculesLibraries.Vision.NewBitMap;
import org.firstinspires.ftc.teamcode.Robot.NDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.NRobot;
import org.firstinspires.ftc.teamcode.Robot.Sensors.Clock;

@TeleOp(name = "Red Auto", group = "TeleOp")
public class RedAuto extends LinearOpMode {
    NRobot r;
    States state = States.TOBLOCK;
    String visionPose = "C";

    enum States {
        BLUEVISION,
        REDVISION,
        TOBLOCK,
        BLOCKPICKERALIGN,
        COLLECT,
        BACKUPANDTURN,
        DRIVETOFOUNDATION,
        DEPOSITBLOCK,
        CLAMP,
        DEPOSITFOUNDATION,
        PARK,
        DONE
    }


    @Override
    public void runOpMode() throws InterruptedException {
        r = new NRobot(this);
        while (!isStarted()) {
            visionPose = NewBitMap.redVision();
            telemetry.addData("Position: ", visionPose);
            telemetry.update();
        }
        waitForStart();
        while (!isStopRequested()) {
            switch (state) {
                case TOBLOCK:
                    NRobot.DriveTrain.encoderDrive(23, .5);
                    Thread.sleep(200);
                    NRobot.DriveTrain.turnPID(.25, false, (Math.PI)/2.0);
                    Thread.sleep(200);
//                    if (visionPose.equals("C")) {
//                        NRobot.DriveTrain.encoderDrive(3, .75);
//                    } else if (visionPose.equals("L")) {
//                        NRobot.DriveTrain.encoderDrive(8, .75);
//                    }
                    state = States.DRIVETOFOUNDATION;
                    break;
                case COLLECT:
                    break;
                case DRIVETOFOUNDATION:
                    NRobot.DriveTrain.encoderDrive(-80,-.75);
                    NRobot.FoundationHook.up();
                    Thread.sleep(200);
                    NRobot.DriveTrain.turnPID(.27, false, Math.PI);
                    NRobot.DriveTrain.encoderDrive(-6, -.75);
                    NRobot.FoundationHook.down();
                    state = States.DEPOSITFOUNDATION;
                    break;
                case DEPOSITBLOCK:
                    break;
                case DEPOSITFOUNDATION:
                    NRobot.DriveTrain.encoderDrive(12, 1.0);
                    Thread.sleep(200);
                    NRobot.DriveTrain.turnPID(.5, false, (7 * Math.PI) / 4.0);
                    state = States.DONE;
                    break;
                case DONE:
                    requestOpModeStop();
                    break;
            }
        }
    }
}
