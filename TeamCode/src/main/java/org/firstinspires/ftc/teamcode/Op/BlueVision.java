package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HerculesLibraries.Vision.NewBitMap;
import org.firstinspires.ftc.teamcode.Robot.NRobot;

@TeleOp (name = "BlueVision", group = "TeleOp")
public class BlueVision extends OpMode {

    NRobot r;
    @Override
    public void init() {
        r = new NRobot(this);
    }

    @Override
    public void loop() {
        try {
            telemetry.addData("Position: ", NewBitMap.blueVision());
            telemetry.update();
        } catch (InterruptedException e) {

        }
    }
}