//package org.firstinspires.ftc.teamcode.Autos;
//
//import android.os.Handler;
//import android.os.Looper;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.teamcode.Lib.Hooks.DelayedRoutine;
//import org.firstinspires.ftc.teamcode.Lib.Hooks.StaticRoutine;
//import org.firstinspires.ftc.teamcode.Lib.Marker.Interrupt;
//import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint;
//import org.firstinspires.ftc.teamcode.Lib.Path.Follower;
//import org.firstinspires.ftc.teamcode.Lib.Path.PathBuilder;
//import org.firstinspires.ftc.teamcode.Modules.Robot;
//
//import java.util.List;
//
//import kotlin.Unit;
//
//@Autonomous (name = "Path Test", group = "Autonomous")
//@Disabled
//public class PathTest extends LinearOpMode {
//
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        Robot r = new Robot(this);
//        Handler dispatch = new Handler(Looper.getMainLooper());
//        List<Waypoint> path = new PathBuilder(new Waypoint(0.0,0.0,0.0, 10.0,null))
//                .addPoint(new Interrupt(20.0,0.0,1.51, 10.0, new DelayedRoutine(() -> {
//                    Robot.intake.power(1.0);
//
//                    return Unit.INSTANCE;
//                }, 2000)))
//                .addPoint(new Waypoint(10.0,40.0, 1.51, 10.0, null))
//                .build();
//        Follower f = new Follower(path, r, this);
//        waitForStart();
//        while (!f.getDone()) {
//            r.follow(f);
//            telemetry.update();
//        }
//    }
//}
