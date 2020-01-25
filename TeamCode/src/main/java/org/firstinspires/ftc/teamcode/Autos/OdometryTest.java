package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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

@TeleOp(name = "Odom Test", group = "TeleOp")
public class OdometryTest extends OpMode {
    Robot r;
    Waypoint w = new Waypoint(20.0,0.0, 0.0, 10.0, null);
    List<Waypoint> path;

   public void init(){
       r = new Robot(this);
       PathBuilder build = new PathBuilder(new Waypoint(0.0,0.0,20.0));
       path = build.addPoint(new Waypoint(20.0,0.0, Double.NaN, 40.0, null))
               .addPoint(new Waypoint(21.0,40.0, Double.NaN, 20.0, null))
               .addPoint(new Waypoint(40.0, 50.0, Double.NaN, 20.0, null))
               .addPoint(new Waypoint(70.0, 60.0, Double.NaN, 20.0, null))
               .build();
   }

   public void loop(){
       r.followPath(path);
   }

}
