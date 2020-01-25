package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Modules.Robot;


@TeleOp (name="print", group = "TeleOp")
public class OdometryPrint extends OpMode {
    Robot r;

    @Override
    public void init() {
        r = new Robot(this);
    }

    public void loop() {
        r.controls();
        Robot.driveTrain.update();
        telemetry.addData("Estimated Position", Robot.driveTrain.getPoseEstimate().easyToRead());
        telemetry.update();
    }
}
