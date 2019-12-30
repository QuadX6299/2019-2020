package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot.NDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.NFoundationHook;
import org.firstinspires.ftc.teamcode.Robot.NRobot;
@Autonomous(name = "RedFoundation", group = "Autonomous")

public class RedFoundation extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        NDriveTrain r = new NDriveTrain(this);
        NFoundationHook f = new NFoundationHook(this);
        waitForStart();

        r.moveEncoder(-0.5,20.0,2000);
        r.strafeEncoder(0.5,2.5,false,1000);
        f.down();


    }
}