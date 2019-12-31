package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HerculesLibraries.Vision.NewBitMap;
import org.firstinspires.ftc.teamcode.Robot.NRobot;
import org.firstinspires.ftc.teamcode.Robot.NVision;

@TeleOp (name = "visionTest", group = "TeleOp")
public class TeleOPVision extends OpMode {

    NRobot r;
    @Override
    public void init() {
        r = new NRobot(this);
    }

    @Override
    public void loop() {
        try {
            telemetry.addData("Position: ", NewBitMap.redVision());
            telemetry.update();
        } catch (InterruptedException e) {

        }
    }
}