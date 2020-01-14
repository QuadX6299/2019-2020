package org.firstinspires.ftc.teamcode.Op;

import android.accounts.NetworkErrorException;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.teamcode.HerculesLibraries.Vision.NewBitMap;
import org.firstinspires.ftc.teamcode.Robot.NRobot;


@Autonomous(name = "Blue Auto", group = "Autonomous")
public class BlueAuto extends LinearOpMode {
    NRobot r;
    States state = States.TOBLOCK;
    String visionPose = "C";

    double offset = 23.0;

    enum States {
        TOBLOCK,
        COLLECT,
        DROPFIRSTOFF,
        DRIVETOSECOND,
        COLLECTSECOND,
        DROPSECONDOFF,
        PARK,
        FOUNDATIONQUICK,
        PARKSIMPLE,
        DONE
    }


    @Override
    public void runOpMode() throws InterruptedException {
        r = new NRobot(this);
        NewBitMap nbb = new NewBitMap(this);
        while (!isStarted()) {
            visionPose = nbb.blueVision();
            telemetry.addData("Position: ", visionPose);
            telemetry.update();
        }
        waitForStart();
        while (!isStopRequested()) {
            switch (state) {
                case TOBLOCK:
                    NRobot.AutoGrabber.rotatorDown();
                    Thread.sleep(200);
                    NRobot.DriveTrain.encoderDrive(22, .75);
                    Thread.sleep(200);
                    NRobot.DriveTrain.turnPID(.25, false, (Math.PI)/2.0);
                    Thread.sleep(300);
                    if (visionPose.equals("C")) {
                        NRobot.DriveTrain.encoderDrive(7,1.0);
                    } else if (visionPose.equals("L")) {
                        NRobot.DriveTrain.encoderDrive(14,1.0);
                    } else {
                        NRobot.DriveTrain.encoderDrive(-4,-1.0);
                    }
                    Thread.sleep(300);
                    NRobot.DriveTrain.encoderDriveStrafe(4,1.0, true);
                    Thread.sleep(200);
                    state = States.COLLECT;
                    break;
                case COLLECT:
                    NRobot.AutoGrabber.grabberDown();
                    Thread.sleep(400);
                    NRobot.AutoGrabber.hold();
                    Thread.sleep(400);
                    NRobot.DriveTrain.encoderDriveStrafe(10.5, .8, false);
                    NRobot.DriveTrain.turnPIDAuto(.4, Math.PI / 2.0);
                    Thread.sleep(200);
                    state = States.DROPFIRSTOFF;
                    break;
                case DROPFIRSTOFF:
                    if (visionPose.equals("C")) {
                        NRobot.DriveTrain.encoderDrive(40,.75);
                    } else if (visionPose.equals("L")) {
                        NRobot.DriveTrain.encoderDrive(33,.75);
                    } else {
                        NRobot.DriveTrain.encoderDrive(47,.75);
                    }
                    NRobot.AutoGrabber.rotatorDown();
                    NRobot.AutoGrabber.dropBlock();
                    Thread.sleep(400);
                    state = States.DRIVETOSECOND;
                    break;
                case DRIVETOSECOND:
                    if (visionPose.equals("C")) {
                        NRobot.DriveTrain.encoderDrive(-(40 + offset),-.75);
                    } else if (visionPose.equals("L")) {
                        NRobot.DriveTrain.encoderDrive(-(33+offset),-.75);
                    } else {
                        NRobot.DriveTrain.encoderDrive(-(47 + offset),-.75);
                    }
                    NRobot.DriveTrain.turnPIDAuto(.25, Math.PI / 2.0);
                    state = States.COLLECTSECOND;
                    break;
                case COLLECTSECOND:
                    Thread.sleep(300);
                    NRobot.DriveTrain.encoderDriveStrafe(10,1.0, true);
                    NRobot.AutoGrabber.clamp();
                    Thread.sleep(400);
                    NRobot.AutoGrabber.hold();
                    NRobot.DriveTrain.encoderDriveStrafe(12, 1.0, false);
                    state = States.DROPSECONDOFF;
                    break;
                case DROPSECONDOFF:
                    NRobot.DriveTrain.turnPIDAuto(.2, Math.PI / 2.0);
                    if (visionPose.equals("C")) {
                        NRobot.DriveTrain.encoderDrive(40 + offset,.75);
                    } else if (visionPose.equals("L")) {
                        NRobot.DriveTrain.encoderDrive(33 + offset,.75);
                    } else {
                        NRobot.DriveTrain.encoderDrive(47 + offset,.75);
                    }
                    NRobot.AutoGrabber.dropBlock();
                    Thread.sleep(300);
                    state = States.PARK;
                    break;
                case FOUNDATIONQUICK:
                    NRobot.DriveTrain.encoderDriveStrafe(14, 1.0, false);
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
                    NRobot.DriveTrain.turnPID(.5, false, (5 * Math.PI) / 4.0);
                    NRobot.DriveTrain.encoderDrive(12, 1.0);
                    NRobot.DriveTrain.turnPID(.5, false, (3 *Math.PI) / 2.0);
                    NRobot.FoundationHook.up();
                    NRobot.DriveTrain.encoderDrive(-9,-1.0);
                    NRobot.DriveTrain.encoderDriveStrafe(23.0,1.0,true);
                    NRobot.DriveTrain.encoderDrive(30,1.0);
                    state = States.DONE;
                    break;
                case PARK:
                    NRobot.AutoGrabber.store();
                    Thread.sleep(500);
                    NRobot.DriveTrain.encoderDrive(-10,-1.0);
                    state = States.DONE;
                    break;
                case DONE:
                    requestOpModeStop();
                    break;
            }
        }
    }
}
