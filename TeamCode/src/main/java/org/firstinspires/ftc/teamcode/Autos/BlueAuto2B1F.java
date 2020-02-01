package org.firstinspires.ftc.teamcode.Autos;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Lib.Hooks.StaticRoutine;
import org.firstinspires.ftc.teamcode.Lib.Marker.End;
import org.firstinspires.ftc.teamcode.Lib.Marker.SpeedEnd;
import org.firstinspires.ftc.teamcode.Lib.Marker.SpeedPoint;
import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint;
import org.firstinspires.ftc.teamcode.Lib.Path.Follower;
import org.firstinspires.ftc.teamcode.Lib.Path.PathBuilder;
import org.firstinspires.ftc.teamcode.Modules.Robot;

import java.util.List;

import kotlin.Unit;

@Config
@Autonomous (name = "2 f pure", group = "Autonomous")
public class BlueAuto2B1F extends LinearOpMode {

    enum States {
        COLLECT,
        FOUNDATION,
        MOVEF,
        GETSECOND,
        DEPOSITSECOND,
        PARK
    }
    States state = States.COLLECT;
    public static double finX = 35.0;
    public static double finY = 3.5;
    public static double heading = Math.PI/2.0;
    @Override
    public void runOpMode() throws InterruptedException {
        Robot r = new Robot(this);
        List<Waypoint> collect = new PathBuilder(new SpeedPoint(0.0,0.0,Math.PI/2.0, 10.0, new StaticRoutine(() -> {
                    Robot.autoGrabberRight.rotatorDown();
                    return Unit.INSTANCE;
                }), .4))
                .addPoint(new End(4,0.0, new Waypoint(33.0,3.5,1.5007963, 10.0, null)))
                .build();

        List<Waypoint> foundation = new PathBuilder(new SpeedPoint(33.0,3.5, 0.0, 10.0, null, .6))
                .addPoint(new Waypoint(25.0, 0.0, 0.0, 9.0, null))
                .addPoint(new Waypoint(25.0,60.0, Math.PI/2.0, 7.0, new StaticRoutine(() -> {
                    Robot.foundationHooks.up();
                    return Unit.INSTANCE;
                })))
                .addPoint(new End(5,0.0,new Waypoint(30, 80.0, 1.5007963, 5.0, null)))
                .build();


        waitForStart();
        while (opModeIsActive()) {
            Robot.driveTrain.update();
            switch (state) {
                case COLLECT:
                    Follower f = new Follower(collect, r, this);
                    while (!f.getDone()) {
                        r.follow(f);
                    }
                    Robot.autoGrabberRight.clamp();
                    Thread.sleep(300);
                    Robot.autoGrabberRight.hold();
                    state = States.FOUNDATION;
                    break;
                case FOUNDATION:
                    f = new Follower(foundation, r, this);
                    while (!f.getDone()) {
                        r.follow(f);
                    }
                    Robot.autoGrabberRight.rotatorDown();
                    Robot.autoGrabberRight.dropBlock();
                    Thread.sleep(400);
                    Robot.driveTrain.turnPID(.4, false, Math.PI);
                    Robot.driveTrain.encoderStraight(-3.0, .5);
                    Robot.autoGrabberRight.store();
                    Robot.driveTrain.encoderStraight(-3.0, .3);
                    Robot.foundationHooks.down();
                    state = States.MOVEF;
                    break;
                case MOVEF:
                    Thread.sleep(500);
                    List<Waypoint> movef = new PathBuilder(new Waypoint(Robot.driveTrain.getPoseEstimate().getX(), Robot.driveTrain.getPoseEstimate().getY(), Math.PI, 10.0, null))
                            .addPoint(new Waypoint(20,80.0,-Math.PI,10.0, null))
                            .addPoint(new Waypoint(13, 75, -Math.PI/4.0, 7.0, null))
                            .addPoint(new SpeedEnd(5.0,0.0,new Waypoint(10,60, Math.PI/2.0, 10.0,null)))
                            .build();
                    f = new Follower(movef, r, this);
                    while (!f.getDone()) {
                        r.follow(f);
                    }
                    Robot.foundationHooks.up();
                    state = States.GETSECOND;
                    break;
                case GETSECOND:
                    List<Waypoint> park = new PathBuilder(new Waypoint(Robot.driveTrain.getPoseEstimate().getX(), Robot.driveTrain.getPoseEstimate().getY(), -Math.PI/2.0, 10.0, null))
                            .addPoint(new Waypoint(22, 50, -Math.PI, 7.0, new StaticRoutine(() -> {
                                Robot.foundationHooks.down();
                                return Unit.INSTANCE;
                            })))
                            .addPoint(new Waypoint(22,30.0,-Math.PI,6.0, null))
                            .addPoint(new Waypoint(21, 3.5-10.5, 1.5007963, 6.0, null))
                            .addPoint(new End(7.0,0.0,new Waypoint(21, 3.5-20.5, 1.5007963, 6.0, null)))
                            .build();
                    f = new Follower(park, r, this);
                    while (!f.getDone()) {
                        r.follow(f);
                    }
                    Robot.autoGrabberRight.clamp();
                    Thread.sleep(400);
                    Robot.autoGrabberRight.hold();
                    requestOpModeStop();
                    break;
            }
        }
    }
}
