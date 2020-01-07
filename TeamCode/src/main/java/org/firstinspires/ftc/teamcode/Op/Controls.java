package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.Robot.NRobot;

@TeleOp (name = "pogchamp", group = "TeleOp")
public class Controls extends OpMode {
    NRobot r;

    @Override
    public void init() {
        r = new NRobot(this);
        NRobot.DriveTrain.setPIDCoefficients(new PIDFCoefficients(25.0,.5,20.0,0.0));
    }

    @Override
    public void loop() {
        r.controls();
        telemetry.addData("Lift:", gamepad2.left_stick_y);
        telemetry.update();
    }
}
