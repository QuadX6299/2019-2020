
package org.firstinspires.ftc.teamcode.OpMode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "ServoTesting", group = "TeleOp")
public class ServoTesting extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Servo servo = hardwareMap.get(Servo.class, "servo");
        servo.setPosition(0.0);
        ElapsedTime time = new ElapsedTime();


        waitForStart();
        double prevTime = 0.0;
        time.startTime();


        while (opModeIsActive()) {
            if (gamepad1.dpad_up && time.milliseconds() - prevTime > 150) {
                servo.setPosition(servo.getPosition()+0.05);
                prevTime = time.milliseconds();
            } else if (gamepad1.dpad_down) {
                servo.setPosition(servo.getPosition()-0.05);
                prevTime = time.milliseconds();

            }
        }
    }
}