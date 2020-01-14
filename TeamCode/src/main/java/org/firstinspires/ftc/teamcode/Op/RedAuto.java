package org.firstinspires.ftc.teamcode.Op;

import android.os.Handler;
import android.os.Looper;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HerculesLibraries.Vision.NewBitMap;
import org.firstinspires.ftc.teamcode.Robot.NRobot;
import org.firstinspires.ftc.teamcode.Robot.Sensors.Clock;

@Autonomous(name = "Red Auto", group = "Autonomous")
public class RedAuto extends LinearOpMode {
    NRobot r;
    States state = States.FOUNDATIONQUICK;
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
        DEPOSITBLOCKSHORT,
        CLAMP,
        FOUNDATIONQUICK,
        DEPOSITFOUNDATION,
        PARK,
        PARKSIMPLE,
        DONE
    }


    @Override
    public void runOpMode() throws InterruptedException {
        r = new NRobot(this);
        waitForStart();
        while (!isStopRequested() && opModeIsActive()) {
            switch (state) {
                case TOBLOCK:
                    NRobot.AutoGrabber.rotatorDown();
                    Thread.sleep(200);
                    NRobot.DriveTrain.encoderDrive(22, .5);
                    Thread.sleep(200);
                    NRobot.DriveTrain.turnPID(.25, false, (Math.PI)/2.0);
                    Thread.sleep(300);
                    if (visionPose.equals("C")) {
                        NRobot.DriveTrain.encoderDrive(8.5,1.0);
                    } else if (visionPose.equals("L")) {
                        NRobot.DriveTrain.encoderDrive(16.0,1.0);
                    }
                    Thread.sleep(200);
                    NRobot.DriveTrain.encoderDriveStrafe(6.5,1.0, true);
                    Thread.sleep(200);
                    state = States.COLLECT;
                    break;
                case COLLECT:
                    NRobot.AutoGrabber.grabberDown();
                    Thread.sleep(400);
                    NRobot.AutoGrabber.hold();
                    Thread.sleep(400);
                    NRobot.DriveTrain.encoderDriveStrafe(10.5, 1.0, false);
                    NRobot.DriveTrain.turnPIDAuto(.25, (Math.PI / 2.0) - Math.toRadians(2));
                    state = States.DRIVETOFOUNDATION;
                    break;
                case DRIVETOFOUNDATION:
                    if (visionPose.equals("C")) {
                        NRobot.DriveTrain.encoderDrive(-90,-1.0);
                    } else if (visionPose.equals("L")) {
                        NRobot.DriveTrain.encoderDrive(-95,-1.0);
                    } else {
                        NRobot.DriveTrain.encoderDrive(-80,-1.0);
                    }
                    Thread.sleep(100);
                    state = States.DEPOSITBLOCK;
                    break;
                case DEPOSITBLOCK:
                    NRobot.DriveTrain.encoderDriveStrafe(9,1.0,true);
                    NRobot.AutoGrabber.rotatorDown();
                    Thread.sleep(50);
                    NRobot.AutoGrabber.dropBlock();
                    Thread.sleep(50);
                    NRobot.AutoGrabber.store();
                    Thread.sleep(100);
                    NRobot.AutoGrabber.store();
                    NRobot.DriveTrain.encoderDriveStrafe(9,1.0,false);
                    NRobot.DriveTrain.turnPID(.25, false, Math.PI);
                    NRobot.FoundationHook.up();
                    NRobot.DriveTrain.encoderDrive(-10, -1.0);
                    NRobot.FoundationHook.down();
                    state = States.DEPOSITFOUNDATION;
                    break;
                case DEPOSITFOUNDATION:
                    NRobot.DriveTrain.encoderDrive(15, 1.0);
                    NRobot.DriveTrain.turnPID(.5, true, (3 * Math.PI) / 4.0);
                    NRobot.DriveTrain.encoderDrive(12, 1.0);
                    NRobot.DriveTrain.turnPID(.5, true, (Math.PI) / 2.0);
                    NRobot.FoundationHook.up();
                    NRobot.DriveTrain.encoderDrive(-6,1.0);
                    state = States.PARK;
                    break;
                case PARK:
                    NRobot.DriveTrain.encoderDriveStrafe(14,.5, true);
                    NRobot.DriveTrain.turnPID(.1, false, (Math.PI) / 2.0);
                    NRobot.DriveTrain.encoderDrive(40,.6);
                    state = RedAuto.States.DONE;
                    break;
                case PARKSIMPLE:
                    NRobot.DriveTrain.encoderDrive(17, .5);
                    state = RedAuto.States.DONE;
                    break;
                case FOUNDATIONQUICK:
                    NRobot.DriveTrain.encoderDriveStrafe(14, 1.0, true);
                    Thread.sleep(200);
                    NRobot.DriveTrain.encoderDrive(12, 1.0);
                    NRobot.FoundationHook.up();
                    Thread.sleep(200);
                    NRobot.DriveTrain.turnPID(.3, false, Math.PI);
                    NRobot.DriveTrain.encoderDrive(-15,-1.0);
                    NRobot.DriveTrain.encoderDrive(-6,-1.0);
                    NRobot.FoundationHook.down();
                    Thread.sleep(400);
                    NRobot.DriveTrain.encoderDrive(15, 1.0);
                    NRobot.DriveTrain.turnPID(.5, true, (3 * Math.PI) / 4.0);
                    NRobot.DriveTrain.encoderDrive(12, 1.0);
                    NRobot.DriveTrain.turnPID(.5, true, (Math.PI) / 2.0);
                    NRobot.FoundationHook.up();
                    NRobot.DriveTrain.encoderDrive(-9,-1.0);
                    NRobot.DriveTrain.encoderDriveStrafe(23.0,1.0,false);
                    NRobot.DriveTrain.encoderDrive(30,1.0);;
                    state = States.DONE;
                    break;
                case DONE:
                    requestOpModeStop();
                    break;
            }
        }
    }
}
