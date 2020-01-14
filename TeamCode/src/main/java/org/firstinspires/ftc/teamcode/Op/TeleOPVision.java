package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HerculesLibraries.Vision.NewBitMap;
import org.firstinspires.ftc.teamcode.Robot.NRobot;

@TeleOp (name = "RedVision", group = "TeleOp")
public class TeleOPVision extends OpMode {

    NRobot r;
    NewBitMap nbb;
    @Override
    public void init() {
        r = new NRobot(this);
        nbb = new NewBitMap(this);
    }

    @Override
    public void loop() {
        try {
            telemetry.addData("Position: ", nbb.redVision());
            telemetry.update();
        } catch (InterruptedException e) {

        }
    }
}