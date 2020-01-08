package org.firstinspires.ftc.teamcode.Op;

import android.accounts.NetworkErrorException;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HerculesLibraries.Vision.NewBitMap;
import org.firstinspires.ftc.teamcode.Robot.NDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.NRobot;
import org.firstinspires.ftc.teamcode.Robot.Sensors.Clock;

@TeleOp(name = "Blue Auto", group = "TeleOp")
public class BlueAuto extends LinearOpMode {
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
        PARKSIMPLE,
        DONE
    }


    @Override
    public void runOpMode() throws InterruptedException {
        r = new NRobot(this);
        while (!isStarted()) {
            visionPose = NewBitMap.blueVision();
            telemetry.addData("Position: ", visionPose);
            telemetry.update();
        }
        waitForStart();
        while (!isStopRequested()) {
            switch (state) {
                case TOBLOCK:
                    NRobot.AutoGrabber.rotatorDown();
                    Thread.sleep(1000);
                    NRobot.DriveTrain.encoderDrive(23, .5);
                    Thread.sleep(200);
                    NRobot.DriveTrain.turnPID(.25, false, (Math.PI)/2.0);
                    Thread.sleep(300);
//                    if (visionPose.equals("C")) {
//                        NRobot.DriveTrain.encoderDrive(3, .75);
//                    } else if (visionPose.equals("L")) {
//                        NRobot.DriveTrain.encoderDrive(8, .75);
//                    }
                    NRobot.DriveTrain.encoderDrive(-4,-.75);
                    Thread.sleep(300);
                    NRobot.DriveTrain.encoderDriveStrafe(4,.75, true);
                    Thread.sleep(200);
                    state = States.COLLECT;
                    break;
                case COLLECT:
                    NRobot.AutoGrabber.grabberDown();
                    Thread.sleep(700);
                    NRobot.AutoGrabber.hold();
                    Thread.sleep(700);
                    NRobot.DriveTrain.encoderDriveStrafe(7, .75, false);
                    NRobot.DriveTrain.turnPIDAuto(.2, Math.PI / 2.0);
                    Thread.sleep(400);
                    state = States.DRIVETOFOUNDATION;
                    break;
                case DRIVETOFOUNDATION:
                    NRobot.DriveTrain.encoderDrive(80,.5);
                    state = States.DEPOSITBLOCK;
                    break;
                case DEPOSITBLOCK:
                    NRobot.DriveTrain.encoderDriveStrafe(12,.75,true);
                    NRobot.AutoGrabber.dropBlock();
                    Thread.sleep(100);
                    NRobot.AutoGrabber.store();
                    NRobot.FoundationHook.up();
                    Thread.sleep(200);
                    NRobot.DriveTrain.turnPID(.27, false, Math.PI);
                    NRobot.DriveTrain.encoderDrive(-7, -.75);
                    NRobot.FoundationHook.down();
                    state = States.DEPOSITFOUNDATION;
                    break;

                case DEPOSITFOUNDATION:
                    NRobot.DriveTrain.encoderDrive(12, 1.0);
                    Thread.sleep(200);
                    NRobot.DriveTrain.turnPID(.5, false, (5 * Math.PI) / 4.0);
                    NRobot.DriveTrain.encoderDrive(10, 1.0);
                    NRobot.DriveTrain.turnPID(.5, false, (3 * Math.PI) / 2.0);
                    NRobot.FoundationHook.up();
                    NRobot.DriveTrain.encoderDrive(-6,.5);
                    state = States.PARK;
                    break;
                case PARK:
                    NRobot.DriveTrain.encoderDriveStrafe(14,.5, false);
                    NRobot.DriveTrain.turnPID(.1, false, (3 * Math.PI) / 2.0);
                    NRobot.DriveTrain.encoderDrive(40,.6);
                    state = States.DONE;
                    break;
                case PARKSIMPLE:
                    NRobot.DriveTrain.encoderDrive(17, .5);
                    state = States.DONE;
                    break;
                case DONE:
                    requestOpModeStop();
                    break;
            }
        }
    }
}
