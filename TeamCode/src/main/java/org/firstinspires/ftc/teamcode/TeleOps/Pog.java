package org.firstinspires.ftc.teamcode.TeleOps;




import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Modules.Robot;


@TeleOp(name = "test", group = "Controls")
public class Pog extends OpMode {

    @Override
    public void init() {
        // took out PID coefficients b/c messing with the strafing,
        // can test later, not going to help rn though
        DcMotor md = hardwareMap.get(DcMotor.class, "fl");
    }

    @Override
    public void loop() {
    }
}