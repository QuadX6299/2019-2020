package org.firstinspires.ftc.teamcode.OpMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot.NRobot;
import org.firstinspires.ftc.teamcode.lib.Coords.State;
import org.firstinspires.ftc.teamcode.lib.Path.NPathBuilder;
@Autonomous(name = "||RedAuto||", group = "Auto")
public class NRedAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        NRobot r = new NRobot(this);
        waitForStart();

        r.followPath(
                r.builder()
                .addStraight(new State(0.0,0.0))
                .addStraight(new State(-10.0,70.0))
                .build()
        );
    }
}
