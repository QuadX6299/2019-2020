package org.firstinspires.ftc.teamcode.Autos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint;
import org.firstinspires.ftc.teamcode.Lib.Path.PureUtilKt;
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D;
import org.firstinspires.ftc.teamcode.Lib.Util.MathExtensionsKt;
import org.firstinspires.ftc.teamcode.Modules.Robot;
import org.openftc.revextensions2.ExpansionHubEx;

import java.util.List;

@Config
@Autonomous (name = "Para Devanshi Pt9", group = "Autonomous")
@Disabled
public class ParaDevanshi extends LinearOpMode {
    public static double x = 20.0;
    public static double y = 20.0;
    public static double heading = Math.PI;
    public static double speed = .5;
    public static boolean fast = true;

    @Override
    public void runOpMode() throws InterruptedException {
        FtcDashboard dash = FtcDashboard.getInstance();
        Robot r = new Robot(this);
        TelemetryPacket pp = new TelemetryPacket();
        pp.put("Voltage", Robot.driveTrain.getHubL().read12vMonitor(ExpansionHubEx.VoltageUnits.MILLIVOLTS));
        dash.sendTelemetryPacket(pp);
        Waypoint target = new Waypoint(x,y,heading, 0.0, null);
        waitForStart();
        while (opModeIsActive()) {
            TelemetryPacket packet = new TelemetryPacket();
            packet.put("Distance: ", Robot.driveTrain.getPoseEstimate().distance(target));
            Robot.driveTrain.update();
            List<Double> oogapowers = PureUtilKt.PureController(Robot.driveTrain.getPoseEstimate(), target, fast, speed);
            packet.put("Pogchampers ", oogapowers);
            Robot.driveTrain.setPower(oogapowers);
            dash.sendTelemetryPacket(packet);
        }
    }
}
