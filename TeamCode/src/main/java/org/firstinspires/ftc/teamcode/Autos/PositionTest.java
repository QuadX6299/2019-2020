package org.firstinspires.ftc.teamcode.Autos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint;
import org.firstinspires.ftc.teamcode.Lib.Path.PureUtilKt;
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D;
import org.firstinspires.ftc.teamcode.Lib.Util.MathExtensionsKt;
import org.firstinspires.ftc.teamcode.Modules.IMU;
import org.firstinspires.ftc.teamcode.Modules.Robot;

import java.util.List;
@Config
@Autonomous(name = "goTo Test", group = "Autonomous")
public class PositionTest extends LinearOpMode {
    public static double xp = 0.0;
    public static double yp = 0.0;
    public static double movement = .3;
    public static double heading = 0.0;


    @Override
    public void runOpMode() throws InterruptedException {
        FtcDashboard dash = FtcDashboard.getInstance();
        Robot r = new Robot(this);
        Waypoint w = new Waypoint(xp,yp, heading, 10.0, null);
        waitForStart();
        while (opModeIsActive()) {
            TelemetryPacket packet = new TelemetryPacket();
            packet.fieldOverlay()
                    .fillCircle(Robot.driveTrain.getPoseEstimate().getX(), Robot.driveTrain.getPoseEstimate().getY(), 1);
            Robot.driveTrain.update();
            packet.put("Apparent Heading", Robot.driveTrain.getPoseEstimate().getHeading());
            dash.sendTelemetryPacket(packet);
            List<Double> powers = PureUtilKt.goToPosition(Robot.driveTrain.getPoseEstimate(), new Pose2D(0.0,0.0,0.0), w, movement, true);
            Robot.driveTrain.setPower(powers);
        }
    }
}
