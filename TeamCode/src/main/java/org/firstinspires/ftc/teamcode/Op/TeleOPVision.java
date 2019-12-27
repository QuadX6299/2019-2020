package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HerculesLibraries.Vision.NewBitMap;
import org.firstinspires.ftc.teamcode.Robot.NRobot;

@TeleOp (name = "visionTest", group = "TeleOp")
public class TeleOPVision extends OpMode {

    NewBitMap n;
    @Override
    public void init() {
        n.init(this);
    }

    @Override
    public void loop() {
        try {
            telemetry.addData("Position: ", NewBitMap.getAvgXBlue());
            telemetry.update();
        } catch (InterruptedException e) {

        }
    }
}