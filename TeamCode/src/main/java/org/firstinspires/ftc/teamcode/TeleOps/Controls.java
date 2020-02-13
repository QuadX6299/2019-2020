package org.firstinspires.ftc.teamcode.TeleOps;




import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Modules.Robot;


@TeleOp(name = "pogchampers", group = "Controls")
public class Controls extends OpMode {
    Robot r;

    @Override
    public void init() {
        r = new Robot(this);
        // took out PID coefficients b/c messing with the strafing,
        // can test later, not going to help rn though
    }

    @Override
    public void loop() {
        r.controls();
        Robot.driveTrain.update();
    }
}