package org.firstinspires.ftc.teamcode.OpMode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.PIDCoefficients;

import org.firstinspires.ftc.teamcode.Robot.NRobot;
import org.firstinspires.ftc.teamcode.lib.Coords.State;
@Config
@Autonomous(name = "tuner", group = "Auto")
public class PurePursuitPIDTuner extends LinearOpMode {
    public static double P = .015;
    public static double I = 0.0;
    public static double D = 0.0;
    public static double KV = 1/120.0;
    public static double KA = .003;
    public static double DISTANCE = 40.0;
    @Override
    public void runOpMode() throws InterruptedException {
        NRobot r = new NRobot(this);
        waitForStart();
        r.followPath(
                r.builder()
                        .addPoint(new State(0.0,0.0))
                        .addPoint(new State(DISTANCE/2,0.0))
                        .addPoint(new State(DISTANCE/2, -DISTANCE/2))
                        .build()
        , new PIDCoefficients(P,I,D), KV, KA);
    }
}
