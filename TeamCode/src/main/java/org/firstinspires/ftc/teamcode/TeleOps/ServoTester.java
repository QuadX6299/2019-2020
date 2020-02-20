package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp (name = "s test", group = "TeleOp")
public class ServoTester extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Servo s = hardwareMap.get(Servo.class, "rotatorLeft");

        ElapsedTime pt = new ElapsedTime();
        s.setPosition(0.0);
        waitForStart();

        double position = 0.0;
        while (opModeIsActive()) {
            if (gamepad1.left_bumper && pt.milliseconds() > 100) {
                position += .01;
                s.setPosition(position);
                pt.reset();
            } else if (gamepad1.right_bumper && pt.milliseconds() > 100) {
                position -= .1;
                s.setPosition(position);
                pt.reset();
            }
            telemetry.addData("S pose", position);
            telemetry.update();
        }
    }
}
