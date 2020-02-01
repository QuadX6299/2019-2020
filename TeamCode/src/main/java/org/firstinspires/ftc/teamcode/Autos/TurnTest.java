package org.firstinspires.ftc.teamcode.Autos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint;
import org.firstinspires.ftc.teamcode.Lib.Path.Follower;
import org.firstinspires.ftc.teamcode.Lib.Path.PathBuilder;
import org.firstinspires.ftc.teamcode.Modules.Robot;

import java.util.List;
@Autonomous(name = "Turn Test", group = "Autonomous")
public class TurnTest extends LinearOpMode {
    Robot r;
    Waypoint w = new Waypoint(20.0,0.0, 0.0, 5.0, null);
    List<Waypoint> path;
    public static double kp = .2;
    public static double angle = Math.PI;

   public void runOpMode() {
       Robot r = new Robot(this);
       waitForStart();
       Robot.autoGrabberRight.rotatorDown();
   }


}
