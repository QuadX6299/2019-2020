package org.firstinspires.ftc.teamcode.Autos;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Lib.Hooks.StaticRoutine;
import org.firstinspires.ftc.teamcode.Lib.Marker.Accurate;
import org.firstinspires.ftc.teamcode.Lib.Marker.End;
import org.firstinspires.ftc.teamcode.Lib.Marker.SpeedEnd;
import org.firstinspires.ftc.teamcode.Lib.Marker.SpeedPoint;
import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint;
import org.firstinspires.ftc.teamcode.Lib.Path.Follower;
import org.firstinspires.ftc.teamcode.Lib.Path.Path;
import org.firstinspires.ftc.teamcode.Lib.Path.PathBuilder;
import org.firstinspires.ftc.teamcode.Lib.Path.PureUtilKt;
import org.firstinspires.ftc.teamcode.Lib.Structs.Point;
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D;
import org.firstinspires.ftc.teamcode.Modules.Robot;
import org.firstinspires.ftc.teamcode.Modules.Vision.NewBitMap;

import java.util.List;

import kotlin.Unit;

@Config
@Autonomous (name = "1 f pure", group = "Autonomous")
public class BlueAuto1B1F extends LinearOpMode {

    enum States {
        COLLECT,
        FOUNDATION,
        MOVEF,
        PARK
    }
    States state = States.COLLECT;

    double left = 11;
    double right = -4.5;
    double center = 3.5;

    @Override
    public void runOpMode() throws InterruptedException {
        Robot r = new Robot(this);
        NewBitMap nbb = new NewBitMap(this);
        double desired = center;
        while (!isStarted()) {
            String visionPose = nbb.blueVision();
            if (visionPose.equals("L")) {
                desired = left;
            } else if (visionPose.equals("R")) {
                desired = right;
            } else if (visionPose.equals("C")) {
                desired = center;
            }
            telemetry.addData("Vision ", visionPose);
            telemetry.update();
        }
        List<Waypoint> collect = new PathBuilder(new SpeedPoint(0.0,0.0,Math.PI/2.0, 10.0, new StaticRoutine(() -> {
                    Robot.autoGrabberRight.rotatorDown();
                    return Unit.INSTANCE;
                }), .4))
                .addPoint(new End(4,0.0, new Waypoint(33.0,desired,1.5007963, 10.0, null)))
                .build();

        List<Waypoint> foundation = new PathBuilder(new SpeedPoint(33.0,desired, 0.0, 10.0, null, .6))
                .addPoint(new Waypoint(25.0, 0.0, 0.0, 10.0, null))
                .addPoint(new Waypoint(25.0,60.0, Math.PI/2.0, 10.0, new StaticRoutine(() -> {
                    Robot.foundationHooks.up();
                    return Unit.INSTANCE;
                })))
                .addPoint(new End(4,0.0,new Waypoint(32, 80.0, 1.5007963, 10.0, null)))
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
                    Thread.sleep(500);
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
                            .addPoint(new Waypoint(20,80.0,Math.PI,10.0, null))
                            .addPoint(new Waypoint(13, 75, -Math.PI/4.0, 7.0, null))
                            .addPoint(new SpeedEnd(5.0,0.0,new Waypoint(10,60, -Math.PI/2.0, 10.0,null)))
                            .build();
                    f = new Follower(movef, r, this);
                    while (!f.getDone()) {
                        r.follow(f);
                    }
                    Robot.foundationHooks.up();
                    Robot.driveTrain.encoderStraight(-12,1.0);
                    state = States.PARK;
                    break;
                case PARK:
                    List<Waypoint> park = new PathBuilder(new Waypoint(Robot.driveTrain.getPoseEstimate().getX(), Robot.driveTrain.getPoseEstimate().getY(), -Math.PI/2.0, 10.0, null))
                            .addPoint(new Waypoint(22, 50, -Math.PI, 7.0, new StaticRoutine(() -> {
                                Robot.foundationHooks.down();
                                return Unit.INSTANCE;
                            })))
                            .addPoint(new End(4.0,0.0,new Waypoint(22,30.0,-Math.PI,6.0, null)))
                            .build();
                    f = new Follower(park, r, this);
                    while (!f.getDone()) {
                        r.follow(f);
                    }
                    requestOpModeStop();
                    break;
            }
        }
    }
}
