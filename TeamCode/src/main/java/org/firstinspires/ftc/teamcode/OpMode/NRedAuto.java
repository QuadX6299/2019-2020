//package org.firstinspires.ftc.teamcode.OpMode;
//
//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.config.Config;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.PIDFCoefficients;
//
//import org.firstinspires.ftc.teamcode.HerculesLibraries.Vision.NewBitMap;
//import org.firstinspires.ftc.teamcode.Robot.NGrabber;
//import org.firstinspires.ftc.teamcode.Robot.NRobot;
//import org.firstinspires.ftc.teamcode.lib.Coords.State;
//import org.firstinspires.ftc.teamcode.lib.Path.NPathBuilder;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static java.lang.Math.PI;
//@Config
//@Autonomous(name = "XxRedAutoxX", group = "Auto")
//public class NRedAuto extends LinearOpMode {
//
//    private String location = "C";
//
//    public static List<State> leftSkystonePath = Arrays.asList(new State(0.0,0.0), new State(30.0,10.0));
//    public static List<State> rightSkystonePath = Arrays.asList(new State(0.0,0.0), new State(30.0,-10.0));
//    public static List<State> centerSkystonePath = Arrays.asList(new State(0.0,0.0), new State(30.0,0.0));
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        NRobot r = new NRobot(this);
//        FtcDashboard dash = FtcDashboard.getInstance();
//
//        while (!isStarted() && !Thread.interrupted()) {
//            location = NewBitMap.redVision();
//            telemetry.addData("Skystone Position: ", location);
//            telemetry.update();
//        }
//
//        waitForStart();
//
//        //TODO flip out intake
//
//
//        if (location.equals("C")) {
//            r.followPath(
//                    r.builder()
//                    .addList(centerSkystonePath)
//                    .build()
//            );
//        } else if (location.equals("L")) {
//            r.followPath(
//                    r.builder()
//                    .addList(leftSkystonePath)
//                    .build()
//            );
//        } else if (location.equals("R")) {
//            r.followPath(
//                    r.builder()
//                    .addList(rightSkystonePath)
//                    .build()
//            );
//        }
//
//        //Macro for collecting block
//        r.getBhorn().down();
//        Thread.sleep(600);
//        r.getDt().encoderDrive(-6);
//        r.getIntake().powerAsync(1.0,1000);
//        r.getDt().encoderDrive(3);
//        r.getBhorn().up();
//        r.getIntake().stopIntake();
//        //block collection -- end -- drive to plate
////        r.getDt().turnPID(.27, true, ((3 * PI) / 2.0));
////        r.getIntake().powerAsync(-1.0, 400);
//        r.followPath(
//                r.builder()
//                .addStraight(80.0)
//                .build()
//        );
////        //TODO Path to get to build plate & figure out angles after first turn
////        r.getGrabber().setAssemblyPosition(NGrabber.POSITIONS.HORNDOWN);
////        r.getDt().turnPID(.27, true, ((3 * PI) / 2.0));
////        r.getGrabber().setAssemblyPosition(NGrabber.POSITIONS.CLAMPDOWN);
//
//    }
//}
