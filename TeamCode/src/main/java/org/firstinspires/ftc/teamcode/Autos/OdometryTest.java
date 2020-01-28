package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Lib.Marker.End;
import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint;
import org.firstinspires.ftc.teamcode.Lib.Path.Follower;
import org.firstinspires.ftc.teamcode.Lib.Path.PathBuilder;
import org.firstinspires.ftc.teamcode.Lib.Structs.Point;
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D;
import org.firstinspires.ftc.teamcode.Modules.Robot;

import java.util.List;

@Autonomous(name = "Odom Test", group = "Autonomous")
public class OdometryTest extends LinearOpMode {
    Robot r;
    Waypoint w = new Waypoint(20.0,0.0, 0.0, 5.0, null);
    List<Waypoint> path;

   public void runOpMode() {
       r = new Robot(this);
       PathBuilder build = new PathBuilder(new Waypoint(0.0,0.0,0.0,5.0, null));
       path = build.addPoint(new Waypoint(20.0,0.0, 0.0, 5.0, null))
               .addPoint(new Waypoint(40.0,20.0,0.0, 5.0, null))
               .addPoint(new Waypoint(50.0,40.0, 0.0, 5.0, null))
               .build();
       waitForStart();
       Follower f = new Follower(path, r, this);
       while (opModeIsActive()) {
           Robot.driveTrain.update();
           List<Double> powers = f.update(Robot.driveTrain.getPoseEstimate());
           Robot.driveTrain.setPower(powers);
           telemetry.addData("powers", powers);
           telemetry.addData("pose", Robot.driveTrain.getPoseEstimate());
           telemetry.update();
       }
   }


}
