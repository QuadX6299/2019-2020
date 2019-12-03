package org.firstinspires.ftc.teamcode.OpMode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.Arrays;

@TeleOp(name = "Test", group = "TeleOp")
public class GamepadToggle extends OpMode {
    public void init() {

    }

    public void loop() {
        try {
            byte[] b = gamepad1.toByteArray();
            telemetry.addData("", Arrays.toString(b));
        } catch (Exception e) {
            telemetry.addLine("err");
        }

    }
}
