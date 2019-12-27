package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot.NRobot;

@TeleOp (name = "pogchamp", group = "TeleOp")
public class Controls extends OpMode {
    NRobot r;

    @Override
    public void init() {
        r = new NRobot(this);
    }

    @Override
    public void loop() {
        r.controls();
    }
}
