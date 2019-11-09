package org.firstinspires.ftc.teamcode.OpMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot.DriveTrain;

@Autonomous (name = "Bot Auto", group = "Auto")
public class TestAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init(this);
        waitForStart();
        while (opModeIsActive()) {
            DriveTrain.turn(.5, true);
        }
    }
}
