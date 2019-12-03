package org.firstinspires.ftc.teamcode.OpMode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot.NRobot;
import org.firstinspires.ftc.teamcode.Robot.Sensors.IMU;

import java.util.Arrays;

@TeleOp(name = "CheemsBorger", group = "TeleOp")
public class TeleOpTest extends OpMode {
    NRobot r;
    @Override
    public void init() {
        r = new NRobot(this);

    }

    @Override
    public void loop() {
        r.controls();
        r.getCompanion().getDriveTrain().refresh();
        telemetry.addData("Pose: ",r.getCompanion().getDriveTrain().getPosition());
        telemetry.addData("All Readings: ", r.getCompanion().getDriveTrain().getEncoderAverage());
        telemetry.addData("IMU: ", IMU.heading());
        telemetry.update();
    }
}
