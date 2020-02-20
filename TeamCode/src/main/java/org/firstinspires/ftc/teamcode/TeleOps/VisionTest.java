package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Modules.Vision.NewBitMap;

@TeleOp (name = "vision test", group = "TeleOp")
public class VisionTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        NewBitMap n = new NewBitMap(this);
        waitForStart();
        while (opModeIsActive()) {

            telemetry.addData("S pose", n.getAvgXBlue());
            telemetry.addData("Block Pose: ", n.blueVision());
            telemetry.update();
        }
    }
}
