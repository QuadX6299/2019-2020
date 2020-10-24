//package org.firstinspires.ftc.teamcode.Autos;
//
//import android.os.Handler;
//import android.os.Looper;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.Lib.Hooks.ArrivalRoutine;
//import org.firstinspires.ftc.teamcode.Lib.Hooks.StaticRoutine;
//import org.firstinspires.ftc.teamcode.Lib.Marker.End;
//import org.firstinspires.ftc.teamcode.Lib.Marker.Interrupt;
//import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint;
//import org.firstinspires.ftc.teamcode.Lib.Path.Follower;
//import org.firstinspires.ftc.teamcode.Lib.Path.PathBuilder;
//import org.firstinspires.ftc.teamcode.Modules.Robot;
//
//import java.util.List;
//
//import kotlin.Unit;
//@Autonomous(name = "Rc", group = "Autonomous")
//@Disabled
//public class RedCarry extends LinearOpMode {
//    public enum States {
//        GETFIRST,
//        GOTOSECOND,
//        GETSECOND,
//        GOTOTHIRD,
//        GETTHIRD,
//        FOUNDATIONPOSITION,
//        MOVEF,
//        PARK
//    }
//
//    public States state = States.GETFIRST;
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        Robot r = new Robot(this);
//        Handler dispatch = new Handler(Looper.getMainLooper());
//        List<Waypoint> scoreFirst = new PathBuilder(new Waypoint(0.0,0.0,-Math.PI/2.0,5.0,new StaticRoutine(() -> {
//            Robot.autoGrabberLeft.rotatorDown();
//            return Unit.INSTANCE;
//        }))).addPoint(new Interrupt(28.0,-4,-Math.PI/2.0, 5.0, new ArrivalRoutine(() -> {
//            Robot.autoGrabberLeft.clamp();
//            dispatch.postDelayed(() -> {
//                Robot.autoGrabberLeft.hold();
//            }, 500);
//            return Unit.INSTANCE;
//        }, 600, 1.0)))
//                .addPoint(new Waypoint(20,-10,-Math.PI/2.0,5.0, null))
//                .addPoint(new Waypoint(20, -40, -Math.PI/2.0, 5.0, null))
//                .addPoint(new End(1.0,0.0, new Waypoint(28,-80,-Math.PI/2.0, 5.0, new StaticRoutine(() -> {
//                    if (Math.abs(Robot.driveTrain.getPoseEstimate().getX()-28) < 1) {
//                        Robot.autoGrabberLeft.dropBlock();
//                    }
//                    return Unit.INSTANCE;
//                }))))
//                .build();
//
//        List<Waypoint> getSecond = new PathBuilder(new Waypoint(28,-80,-Math.PI/2.0,5.0, new StaticRoutine(() -> {
//            Robot.autoGrabberLeft.store();
//            return Unit.INSTANCE;
//        })))
//                .addPoint(new Waypoint(20,-15,-Math.PI/2.0, 5.0, new StaticRoutine(() -> {
//                    dispatch.postDelayed(() -> {
//                        Robot.autoGrabberLeft.dropBlock();
//                    }, 300);
//                    return Unit.INSTANCE;
//                })))
//                .addPoint(new Waypoint(20,10, -Math.PI/2.0, 10.0, new StaticRoutine(() -> {
//                    Robot.autoGrabberLeft.rotatorDown();
//                    return Unit.INSTANCE;
//                })))
//                .addPoint(new End(1.0,0.0,new Waypoint(32.0,18, -Math.PI/2.0, 5.0, null)))
//                .build();
//
//        List<Waypoint> depoSecond = new PathBuilder(new Interrupt(32,18, -Math.PI/2.0, 5.0, new ArrivalRoutine(() -> {
//            Robot.autoGrabberLeft.clamp();
//            dispatch.postDelayed(() -> {
//                Robot.autoGrabberLeft.hold();
//            }, 500);
//            return Unit.INSTANCE;
//        }, 600,1.0)))
//                .addPoint(new Waypoint(20,10,-Math.PI/2.0,5.0, null))
//                .addPoint(new Waypoint(20, -40, -Math.PI/2.0, 5.0, null))
//                .addPoint(new End(1.0,0.0, new Waypoint(28,-75,-Math.PI/2.0, 5.0, new StaticRoutine(() -> {
//                    if (Math.abs(Robot.driveTrain.getPoseEstimate().getX()-28) < 1) {
//                        Robot.autoGrabberLeft.dropBlock();
//                    }
//                    return Unit.INSTANCE;
//                }))))
//                .build();
//
//        List<Waypoint> park = new PathBuilder(new Waypoint(28,-75, -Math.PI/2.0, 5.0, null))
//                .addPoint(new End(1.0,0.0,new Waypoint(23,-40,Math.PI/2.0,5.0,null)))
//                .build();
//
//        waitForStart();
//        while (opModeIsActive()) {
//            Robot.driveTrain.update();
//            switch (state) {
//                case GETFIRST:
//                    Follower fi = new Follower(scoreFirst, r, this);
//                    while (!fi.getDone()) {
//                        r.follow(fi);
//                        telemetry.update();
//                    }
//                    state = States.GOTOSECOND;
//                    break;
//                case GOTOSECOND:
//                    Follower ff = new Follower(getSecond, r, this);
//                    while (!ff.getDone()) {
//                        r.follow(ff);
//                        telemetry.update();
//                    }
//                    state = States.GETSECOND;
//                    break;
//                case GETSECOND:
//                    Follower fi2 = new Follower(depoSecond, r, this);
//                    while (!fi2.getDone()) {
//                        r.follow(fi2);
//                        telemetry.update();
//                    }
//                    Robot.autoGrabberLeft.store();
//                    state = States.PARK;
//                    break;
//                case PARK:
//                    Follower ff2 = new Follower(park, r, this);
//                    while (!ff2.getDone()) {
//                        r.follow(ff2);
//                        telemetry.update();
//                    }
//                    requestOpModeStop();
//                    break;
//            }
//        }
//    }
//}
