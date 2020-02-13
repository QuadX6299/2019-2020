package org.firstinspires.ftc.teamcode.Autos;

import android.os.Handler;
import android.os.Looper;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Lib.Hooks.ArrivalRoutine;
import org.firstinspires.ftc.teamcode.Lib.Hooks.DelayedRoutine;
import org.firstinspires.ftc.teamcode.Lib.Hooks.StaticRoutine;
import org.firstinspires.ftc.teamcode.Lib.Marker.Interrupt;
import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint;
import org.firstinspires.ftc.teamcode.Lib.Path.Follower;
import org.firstinspires.ftc.teamcode.Lib.Path.PathBuilder;
import org.firstinspires.ftc.teamcode.Modules.Robot;

import java.util.List;

import kotlin.Unit;

@Autonomous (name = "Bc", group = "Autonomous")
public class BlueCarry extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot r = new Robot(this);
        Handler dispatch = new Handler(Looper.getMainLooper());
        PathBuilder pb = new PathBuilder(new Waypoint(0.0,0.0,Math.PI/2.0,5.0,new StaticRoutine(() -> {
            Robot.autoGrabberRight.rotatorDown();
            return Unit.INSTANCE;
        })));
        pb.addPoint(new Interrupt(27.0,0,Math.PI/2.0, 5.0, new ArrivalRoutine(() -> {
            dispatch.postDelayed(() -> {
                Robot.autoGrabberRight.clamp();
            }, 600);
            dispatch.postDelayed(() -> {
                Robot.autoGrabberRight.hold();
            }, 800);
            return Unit.INSTANCE;
        }, 1400, 1.0)))
                .addPoint(new Waypoint(25,15,0.0,5.0, null))
                .addPoint(new Waypoint(25, 40, 0.0, 4.0, null))
                .addPoint(new Waypoint(32,80,Math.PI/2.0, 3.0, null));
        List<Waypoint> path = pb.build();
        Follower f = new Follower(path, r, this);
        waitForStart();
        while (!f.getDone()) {
            r.follow(f);
            telemetry.update();
        }
    }
}
