package org.firstinspires.ftc.teamcode.OpMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot.DriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Intake;

@Autonomous (name = "Cheemsborger", group = "Auto")
public class OogaAuto extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        Robot.init(this);
        waitForStart();
        Intake.unfold();
        DriveTrain.setPower(.2,.2);
        Thread.sleep(400);
    }
}
