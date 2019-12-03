package org.firstinspires.ftc.teamcode.OpMode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot.NRobot;

@TeleOp(name = "|||Main|||", group = "TeleOp")
public class TeleOPOdometry extends OpMode {
    NRobot main;
    public void init() {
        main = new NRobot(this);
    }

    public void loop() {
        main.controls();
    }
}
