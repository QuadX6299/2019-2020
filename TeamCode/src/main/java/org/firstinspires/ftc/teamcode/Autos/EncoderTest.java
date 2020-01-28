package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Modules.Robot;
import org.firstinspires.ftc.teamcode.Modules.Vision.NewBitMap;

@Autonomous(name = "enc test", group = "Autonomous")
public class EncoderTest extends LinearOpMode {

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
        NewBitMap nbb = new NewBitMap(this);
        while (!isStarted()) {
            visionPose = nbb.blueVision();
            telemetry.addData("Position: ", visionPose);
            telemetry.update();
        }

        Robot r = new Robot(this);
        waitForStart();
        Robot.driveTrain.encoderStraight(10.0,.5);


        while (!isStopRequested()) {
            switch (state) {
                case TOBLOCK:
                    Robot.autoGrabberRight.rotatorDown();
                    Thread.sleep(200);
                    Robot.driveTrain.encoderStraight(22.0,.75);
                    Thread.sleep(200);
                    NRobot.DriveTrain.turnPID(.25, false, (Math.PI)/2.0);
                    Thread.sleep(300);
                    if (visionPose.equals("C")) {
                        Robot.driveTrain.encoderStraight(7.0,1.0);
                    } else if (visionPose.equals("L")) {
                        Robot.driveTrain.encoderStraight(14.0,1.0);
                    } else {
                        Robot.driveTrain.encoderStraight(-4.0,1.0);
                    }
                    Thread.sleep(300);
                    Robot.driveTrain.encoderStrafe(4.0,1.0);
                    Thread.sleep(200);
                    state = States.COLLECT;
                    break;
                case COLLECT:
                    Robot.autoGrabberRight.grabberDown();
                    Thread.sleep(400);
                    Robot.autoGrabberRight.hold();
                    Thread.sleep(400);
                    Robot.driveTrain.encoderStrafe(-10.5,.8);
                    NRobot.DriveTrain.turnPIDAuto(.4, Math.PI / 2.0);
                    Thread.sleep(200);
                    state = States.DROPFIRSTOFF;
                    break;
                case DROPFIRSTOFF:
                    if (visionPose.equals("C")) {
                        Robot.driveTrain.encoderStraight(40.0,.75);
                    } else if (visionPose.equals("L")) {
                        Robot.driveTrain.encoderStraight(33.0,.75);
                    } else {
                        Robot.driveTrain.encoderStraight(47.0,.75);
                    }
                    Robot.autoGrabberRight.rotatorDown();
                    Robot.autoGrabberRight.dropBlock();
                    Thread.sleep(400);
                    state = States.DRIVETOSECOND;
                    break;
                case DRIVETOSECOND:
                    if (visionPose.equals("C")) {
                        Robot.driveTrain.encoderStraight(-(40 + offset),.75);
                    } else if (visionPose.equals("L")) {
                        Robot.driveTrain.encoderStraight(-(33 + offset),.75);
                    } else {
                        Robot.driveTrain.encoderStraight(-(47 + offset),.75);

                    }
                    NRobot.DriveTrain.turnPIDAuto(.25, Math.PI / 2.0);
                    state = States.COLLECTSECOND;
                    break;
                case COLLECTSECOND:
                    Thread.sleep(300);
                    Robot.driveTrain.encoderStrafe(10.0,1.0);
                    Robot.autoGrabberRight.clamp();
                    Thread.sleep(400);
                    Robot.autoGrabberRight.hold();
                    Robot.driveTrain.encoderStrafe(-12.0,1.0);
                    state = States.DROPSECONDOFF;
                    break;
                case DROPSECONDOFF:
                    NRobot.DriveTrain.turnPIDAuto(.2, Math.PI / 2.0);
                    if (visionPose.equals("C")) {
                        Robot.driveTrain.encoderStraight(40 + offset,.75);

                    } else if (visionPose.equals("L")) {
                        Robot.driveTrain.encoderStraight(33 + offset,.75);

                    } else {
                        Robot.driveTrain.encoderStraight(47 + offset,.75);

                    }
                    Robot.autoGrabberRight.dropBlock();
                    Thread.sleep(300);
                    state = States.PARK;
                    break;
                case FOUNDATIONQUICK:
                    Robot.driveTrain.encoderStrafe(-14.0,1.0);
                    Thread.sleep(200);
                    Robot.driveTrain.encoderStraight(12.0,1.0);

                    Robot.foundationHooks.up();
                    Thread.sleep(200);
                    NRobot.DriveTrain.turnPID(.3, false, Math.PI);
                    Robot.driveTrain.encoderStraight(-15.0,1.0);
                    Robot.driveTrain.encoderStraight(-6.0,1.0);
                    Robot.foundationHooks.down();
                    Thread.sleep(400);
                    Robot.driveTrain.encoderStraight(15.0,1.0);
                    NRobot.DriveTrain.turnPID(.5, false, (5 * Math.PI) / 4.0);
                    Robot.driveTrain.encoderStraight(12.0,1.0);
                    NRobot.DriveTrain.turnPID(.5, false, (3 *Math.PI) / 2.0);
                    Robot.foundationHooks.up();
                    Robot.driveTrain.encoderStraight(-9.0,1.0);
                    Robot.driveTrain.encoderStrafe(23.0,1.0);
                    Robot.driveTrain.encoderStraight(30.0,1.0);

                    state = States.DONE;
                    break;
                case PARK:
                    Robot.autoGrabberRight.store();
                    Thread.sleep(500);
                    Robot.driveTrain.encoderStraight(-10.0,1.0);

                    state = States.DONE;
                    break;
                case DONE:
                    requestOpModeStop();
                    break;
            }


    }
}
