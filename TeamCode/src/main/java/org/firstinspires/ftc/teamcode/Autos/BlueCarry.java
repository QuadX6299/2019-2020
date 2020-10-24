//package org.firstinspires.ftc.teamcode.Autos;
//
//import android.os.Handler;
//import android.os.Looper;
//
//import com.acmerobotics.dashboard.config.Config;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.teamcode.Lib.Hooks.ArrivalRoutine;
//import org.firstinspires.ftc.teamcode.Lib.Hooks.DelayedRoutine;
//import org.firstinspires.ftc.teamcode.Lib.Hooks.StaticRoutine;
//import org.firstinspires.ftc.teamcode.Lib.Marker.End;
//import org.firstinspires.ftc.teamcode.Lib.Marker.Interrupt;
//import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint;
//import org.firstinspires.ftc.teamcode.Lib.Path.Follower;
//import org.firstinspires.ftc.teamcode.Lib.Path.PathBuilder;
//import org.firstinspires.ftc.teamcode.Lib.Structs.Point;
//import org.firstinspires.ftc.teamcode.Lib.Util.MathExtensionsKt;
//import org.firstinspires.ftc.teamcode.Modules.Robot;
//
//import java.util.List;
//
//import kotlin.Unit;
//
//@Config
//@Autonomous (name = "Bc", group = "Autonomous")
//@Disabled
//public class BlueCarry extends LinearOpMode {
//
//    public static String postion = "C";
//    public static double c1 = 2;
//    public static double c2 = -22;
//    public static double c3 = -5;
//    public static double l1 = 10;
//    public static double l2 = -14;
//    public static double l3 = 2;
//    public static double r1 = -6;
//    public static double r2 = -30;
//    public static double r3 = 2;
//    public static int index = 0;
//
//    double[] one = new double[] {l1, c1, r1};
//    double[] two = new double[] {l2, c2, r2};
//    double[] three = new double[] {l3, c3, r3};
//
//    public enum States {
//        GETFIRST,
//        GOTOSECOND,
//        GETSECOND,
//        GOTOTHIRD,
//        GETTHIRD,
//        FOUNDATIONPOSITION,
//        MOVEF,
//        PARK,
//        SIMP
//    }
//
//    public States state = States.GETFIRST;
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        Robot r = new Robot(this);
//        Handler dispatch = new Handler(Looper.getMainLooper());
//        List<Waypoint> scoreFirst = new PathBuilder(new Waypoint(0.0,0.0,Math.PI/2.0,5.0,new StaticRoutine(() -> {
//                    Robot.autoGrabberRight.rotatorDown();
//                    return Unit.INSTANCE;
//                }))).addPoint(new Interrupt(28.0,one[index],Math.PI/2.0, 5.0, new ArrivalRoutine(() -> {
//                    Robot.autoGrabberRight.clamp();
//                    dispatch.postDelayed(() -> {
//                        Robot.autoGrabberRight.hold();
//                    }, 450);
//                    return Unit.INSTANCE;
//                }, 700, 1.0)))
//                .addPoint(new Waypoint(19,10,Math.PI/2.0,5.0, null))
//                .addPoint(new Waypoint(19, 40, Math.PI/2.0, 5.0, null))
//                .addPoint(new Waypoint(27, 60, Math.PI/2.0, 5.0,null))
//                .addPoint(new End(1.0,0.0, new Waypoint(30,95,Math.PI/2.0, 5.0, null)))
//                .build();
//
////        List<Waypoint> moveF = new PathBuilder(new Waypoint(30,85, Math.PI, 5.0, new StaticRoutine(() -> {
////            Robot.autoGrabberRight.store();
////            return Unit.INSTANCE;
////        })))
////                .addPoint(new Interrupt(25,85, Math.PI, 5.0, new ArrivalRoutine(() -> {
////                    Robot.foundationHooks.up();
////                    return Unit.INSTANCE;
////                }, 100, 1.0)))
////                .addPoint(new End(.5, 0.0, new Waypoint(34, 77, Math.PI,5.0, null)))
////                .build();
////
////        List<Waypoint> moveF2 = new PathBuilder(new Waypoint(34,77, Math.PI, 5.0, null))
////                .addPoint(new Waypoint(20,70, 5 * Math.PI / 4, 5.0, null))
////                .addPoint(new Interrupt(20, 60, -Math.PI/2.0, 5.0, new StaticRoutine(() -> {
////                    Robot.foundationHooks.up();
////                    return Unit.INSTANCE;
////                })))
////                .addPoint(new End(1.0,0.0, new Waypoint(20, 0, -Math.PI/2.0, 5.0, null)))
////                .build();
//
//        List<Waypoint> getSecond = new PathBuilder(new Waypoint(30,95,Math.PI/2.0,5.0, null))
//                .addPoint(new Waypoint(19,40,Math.PI/2.0, 5.0, new StaticRoutine(() -> {
//                    dispatch.postDelayed(() -> {
//                        Robot.autoGrabberRight.dropBlock();
//                    }, 400);
//                    return Unit.INSTANCE;
//                })))
//                .addPoint(new Waypoint(19,10, Math.PI/2.0, 5.0, new StaticRoutine(() -> {
//                    Robot.autoGrabberRight.rotatorDown();
//                    return Unit.INSTANCE;
//                })))
//                .addPoint(new End(1.0,0.0,new Waypoint(28.0,two[index], Math.PI/2.0, 5.0, null)))
//                .build();
//
//        List<Waypoint> depoSecond = new PathBuilder(new Interrupt(28,two[index], Math.PI/2.0, 5.0, new ArrivalRoutine(() -> {
//            Robot.autoGrabberRight.clamp();
//            dispatch.postDelayed(() -> {
//                Robot.autoGrabberRight.hold();
//            }, 450);
//            return Unit.INSTANCE;
//        }, 700,1.0)))
//                .addPoint(new Waypoint(19,-10,Math.PI/2.0,5.0, null))
//                .addPoint(new Waypoint(19, 40, Math.PI/2.0, 5.0, null))
//                .addPoint(new Waypoint(27, 60, Math.PI/2.0, 5.0,null))
//                .addPoint(new End(1.0,0.0, new Waypoint(30,85,Math.PI/2.0, 5.0, null)))
//                .build();
//
//        List<Waypoint> getThird = new PathBuilder(new Waypoint(29,85,Math.PI/2.0,5.0, null))
//                .addPoint(new Waypoint(19,40,Math.PI/2.0, 5.0, new StaticRoutine(() -> {
//                    dispatch.postDelayed(() -> {
//                        Robot.autoGrabberRight.dropBlock();
//                    }, 400);
//                    return Unit.INSTANCE;
//                })))
//                .addPoint(new Waypoint(19,10, Math.PI/2.0, 5.0, new StaticRoutine(() -> {
//                    Robot.autoGrabberRight.rotatorDown();
//                    return Unit.INSTANCE;
//                })))
//                .addPoint(new End(1.0,0.0,new Waypoint(28.0,three[index], Math.PI/2.0, 5.0, null)))
//                .build();
//
//        List<Waypoint> depoThird = new PathBuilder(new Interrupt(28,three[index], Math.PI/2.0, 5.0, new ArrivalRoutine(() -> {
//            Robot.autoGrabberRight.clamp();
//            dispatch.postDelayed(() -> {
//                Robot.autoGrabberRight.hold();
//            }, 450);
//            return Unit.INSTANCE;
//        }, 700,1.0)))
//                .addPoint(new Waypoint(19,5,Math.PI/2.0,5.0, null))
//                .addPoint(new Waypoint(19, 40, Math.PI/2.0, 5.0, null))
//                .addPoint(new Waypoint(27, 60, Math.PI/2.0, 5.0,null))
//                .addPoint(new End(1.0,0.0, new Waypoint(30,75,Math.PI/2.0, 5.0, null)))
//                .build();
//
//        List<Waypoint> park = new PathBuilder(new Waypoint(30,75, Math.PI/2.0, 5.0, null))
//                .addPoint(new End(1.0,0.0,new Waypoint(23,40,-Math.PI/2.0,5.0,null)))
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
//                    Robot.autoGrabberRight.dropBlock();
//                    Robot.autoGrabberRight.tilt();
//                    Thread.sleep(500);
//                    Robot.autoGrabberRight.store();
//                    state = States.GOTOSECOND;
//                    break;
////                case MOVEF:
////                    Follower pls = new Follower(moveF, r, this);
////                    while (!pls.getDone()) {
////                        r.follow(pls);
////                        telemetry.update();
////                    }
////                    Robot.foundationHooks.hook();
////                    Thread.sleep(2000);
////                    Follower pls2 = new Follower(moveF2, r, this);
////                    while (!pls2.getDone()) {
////                        r.follow(pls2);
////                        telemetry.update();
////                    }
////                    requestOpModeStop();
////                    break;
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
//                    Robot.autoGrabberRight.dropBlock();
//                    Robot.autoGrabberRight.tilt();
//                    Thread.sleep(500);
//                    Robot.autoGrabberRight.store();
//                    state = States.GOTOTHIRD;
//                    break;
//                case GOTOTHIRD:
//                    Follower ff20 = new Follower(getThird, r, this);
//                    while (!ff20.getDone()) {
//                        r.follow(ff20);
//                        telemetry.update();
//                    }
//                    state = States.GETTHIRD;
//                    break;
//                case GETTHIRD:
//                    Follower ff30 = new Follower(depoThird, r, this);
//                    while (!ff30.getDone()) {
//                        r.follow(ff30);
//                        telemetry.update();
//                    }
//                    Robot.autoGrabberRight.dropBlock();
//                    Robot.autoGrabberRight.tilt();
//                    Thread.sleep(500);
//                    Robot.autoGrabberRight.store();
//                    state = States.PARK;
//                case PARK:
//                    Follower ff2 = new Follower(park, r, this);
//                    while (!ff2.getDone()) {
//                        r.follow(ff2);
//                        telemetry.update();
//                    }
//                    requestOpModeStop();
//                    break;
//                case SIMP:
//                    List<Waypoint> pb = new PathBuilder(new Waypoint(0.0,0.0,0.0,0.0,null))
//                            .addPoint(new End(1.0,0.0,new Waypoint(10.0,0.0,0.0,0.0,null)))
//                            .build();
//                    Follower ff15 = new Follower(pb, r, this);
//                    while (!ff15.getDone()) {
//                        r.follow(ff15);
//                        telemetry.update();
//                    }
//                    requestOpModeStop();
//                    break;
//            }
//        }
//    }
//}
