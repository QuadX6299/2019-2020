package org.firstinspires.ftc.teamcode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.OpMode.AutoTransitioner;
import org.firstinspires.ftc.teamcode.Robot.DriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Grabber;

import java.util.ArrayList;
import java.util.List;

@Autonomous(name = "AutoTest", group = "Auto")

public class AutoTest extends LinearOpMode {
    //TensorFlowDetection vision;


    @Override
    public void runOpMode() throws InterruptedException {
        double deltaT = 0;
        Robot.init(this);
        //vision = new TensorFlowDetection(this);
        waitForStart();

        wait(10000);
        AutoTransitioner.transitionOnStop(this, "TeleOp");
       //TODO implement -> dt.moveEncoder(0.5, 200, 1000);
    }
}