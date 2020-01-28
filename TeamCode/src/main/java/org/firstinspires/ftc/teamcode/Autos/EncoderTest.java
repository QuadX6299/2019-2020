package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Modules.Robot;

@Autonomous(name = "enc test", group = "Autonomous")
public class EncoderTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot r = new Robot(this);
        waitForStart();
        Robot.driveTrain.encoderStraight(10.0,.5);
    }
}
