package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Modules.Robot;
import org.openftc.revextensions2.ExpansionHubMotor;

@TeleOp (name = "Odom Test", group = "TeleOp")
public class OdomTest extends OpMode {
    Robot r;

    @Override
    public void init() {
        r = new Robot(this);
        for (ExpansionHubMotor b : Robot.driveTrain.getAll()) {
            b.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
    }

    @Override
    public void loop() {
        Robot.driveTrain.update();
        telemetry.addData("Wheel Poses", Robot.driveTrain.getWheelPositions());
        telemetry.addData("Location ", Robot.driveTrain.getPoseEstimate().easyToRead());
    }
}
