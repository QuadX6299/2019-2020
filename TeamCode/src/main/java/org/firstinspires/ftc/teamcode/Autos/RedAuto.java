package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.ftccommon.internal.RunOnBoot;
import org.firstinspires.ftc.teamcode.Modules.Robot;
import org.firstinspires.ftc.teamcode.Modules.Vision.NewBitMap;
import org.firstinspires.ftc.teamcode.R;

@Autonomous(name = "Red1StoneFoundation", group = "Autonomous")
public class RedAuto extends LinearOpMode {

    States state = States.TOBLOCK;
    String visionPose = "C";

    double offset = 23.0;

    enum States {
        TOBLOCK,
        COLLECT,
        DROPFIRSTOFF,
        DRIVETOFOUNDATION,
        DEPOSITBLOCK,
        DEPOSITFOUNDATION,
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
        Robot.driveTrain.encoderStraight(10.0, .5);


        while (!isStopRequested()) {
            switch (state) {
                case TOBLOCK:
                    Robot.autoGrabberRight.rotatorDown();
                    Thread.sleep(200);
                    Robot.driveTrain.encoderStraight(22, .5);
                    Thread.sleep(200);
                    Robot.driveTrain.turnPID(.25, false, (Math.PI)/2.0);
                    Thread.sleep(300);
                    if (visionPose.equals("C")) {
                        Robot.driveTrain.encoderStraight(8.5,1.0);
                    } else if (visionPose.equals("L")) {
                        Robot.driveTrain.encoderStraight(16.0,1.0);
                    }
                    Thread.sleep(200);
                    Robot.driveTrain.encoderStrafe(6.5,1.0);
                    Thread.sleep(200);
                    state = States.COLLECT;
                    break;
                case COLLECT:
                    Robot.autoGrabberRight.grabberDown();
                    Thread.sleep(400);
                    Robot.autoGrabberRight.hold();
                    Thread.sleep(400);
                    Robot.driveTrain.encoderStrafe(-10.5, 1.0);
                    Robot.driveTrain.turnPIDAuto(.25, (Math.PI / 2.0) - Math.toRadians(2));
                    state = States.DROPFIRSTOFF;
                    break;
                case DRIVETOFOUNDATION:
                    if (visionPose.equals("C")) {
                        Robot.driveTrain.encoderStraight(-90,1.0);
                    } else if (visionPose.equals("L")) {
                        Robot.driveTrain.encoderStraight(-95,1.0);
                    } else {
                        Robot.driveTrain.encoderStraight(-80,1.0);
                    }
                    Thread.sleep(100);
                    state = States.DEPOSITBLOCK;
                    break;
                case DEPOSITBLOCK:
                    Robot.driveTrain.encoderStrafe(9,1.0);
                    Robot.autoGrabberRight.rotatorDown();
                    Thread.sleep(50);
                    Robot.autoGrabberRight.dropBlock();
                    Thread.sleep(50);
                    Robot.autoGrabberRight.store();
                    Thread.sleep(100);
                    Robot.autoGrabberRight.store();
                    Robot.driveTrain.encoderStrafe(-9,1.0);
                    Robot.driveTrain.turnPID(.25, false, Math.PI);
                    Robot.foundationHooks.up();
                    Robot.driveTrain.encoderStraight(-10, 1.0);
                    Robot.foundationHooks.down();
                    state = States.DEPOSITFOUNDATION;
                    break;
                case DEPOSITFOUNDATION:
                    Robot.driveTrain.encoderStraight(15, 1.0);
                    Robot.driveTrain.turnPID(.5, true, (3 * Math.PI) / 4.0);
                    Robot.driveTrain.encoderStraight(12, 1.0);
                    Robot.driveTrain.turnPID(.5, true, (Math.PI) / 2.0);
                    Robot.foundationHooks.up();
                    Robot.driveTrain.encoderStraight(-6,1.0);
                    state = States.PARK;
                    break;
                case PARK:
                    Robot.driveTrain.encoderStrafe(14,.5);
                    Robot.driveTrain.turnPID(.1, false, (Math.PI) / 2.0);
                    Robot.driveTrain.encoderStraight(40,.6);
                    state = RedAuto.States.DONE;
                    break;
                case PARKSIMPLE:
                    Robot.driveTrain.encoderStraight(17, .5);
                    state = RedAuto.States.DONE;
                    break;
                case FOUNDATIONQUICK:
                    Robot.driveTrain.encoderStrafe(14, 1.0);
                    Thread.sleep(200);
                    Robot.driveTrain.encoderStraight(12, 1.0);
                    Robot.foundationHooks.up();
                    Thread.sleep(200);
                    Robot.driveTrain.turnPID(.3, false, Math.PI);
                    Robot.driveTrain.encoderStraight(-15,1.0);
                    Robot.driveTrain.encoderStraight(-6,1.0);
                    Robot.foundationHooks.down();
                    Thread.sleep(400);
                    Robot.driveTrain.encoderStraight(15, 1.0);
                    Robot.driveTrain.turnPID(.5, true, (3 * Math.PI) / 4.0);
                    Robot.driveTrain.encoderStraight(12, 1.0);
                    Robot.driveTrain.turnPID(.5, true, (Math.PI) / 2.0);
                    Robot.foundationHooks.up();
                    Robot.driveTrain.encoderStraight(-9,1.0);
                    Robot.driveTrain.encoderStrafe(-23.0,1.0);
                    Robot.driveTrain.encoderStraight(30,1.0);;
                    state = States.DONE;
                    break;
                case DONE:
                    requestOpModeStop();
                    break;
            }


        }
    }
}
