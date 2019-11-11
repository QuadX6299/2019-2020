package org.firstinspires.ftc.teamcode.OpMode;

import android.graphics.Path;
import android.os.Handler;
import android.os.Looper;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HerculesLibraries.Vision.NewBitMap;
import org.firstinspires.ftc.teamcode.Robot.Bhorn;
import org.firstinspires.ftc.teamcode.Robot.DriveTrain;
import org.firstinspires.ftc.teamcode.Robot.FoundationHook;
import org.firstinspires.ftc.teamcode.Robot.Grabber;
import org.firstinspires.ftc.teamcode.Robot.Intake;
import org.firstinspires.ftc.teamcode.Robot.Sensors.IMU;
import org.firstinspires.ftc.teamcode.lib.Constraints.PIDFCoefficients;
import org.firstinspires.ftc.teamcode.lib.Constraints.TankKinematics;
import org.firstinspires.ftc.teamcode.lib.Coords.Position;
import org.firstinspires.ftc.teamcode.lib.Coords.Waypoint;
import org.firstinspires.ftc.teamcode.lib.Path.PathFollower;
import org.firstinspires.ftc.teamcode.lib.Path.Paths;

import java.util.List;

@Autonomous(name = "StateRed", group = "Auto")

public class StateMachineRed extends OpMode {
    AutoStates state;
    PathFollower pure;
    ElapsedTime t;
    String skystone = "NA";
    Handler dispatcher;
    double lastT = 0.0;


    @Override
    public void init() {
        Robot.init(this);
        dispatcher = new Handler(Looper.getMainLooper());
        pure = new PathFollower(Paths.getStraightRed(), 10.0, new TankKinematics(DriveTrain.width + 5.0), new PIDFCoefficients(.01,0.0,.0003,1/120.0,.0003),
                5.0, this);
        t = new ElapsedTime(0);
        t.reset();
        while (t.seconds() < 5.0 && skystone.equals("NA")) {
            try {
                skystone = NewBitMap.redVision();
            } catch (InterruptedException e) {
                telemetry.addLine("Interrupted Exception");
            }
            telemetry.addData("Stone Pose ", skystone);
            telemetry.addData("Time Left ", 5.0 - t.seconds());
            telemetry.update();
        }
        state = AutoStates.VISION;
    }

    @Override
    public void loop() {
        lastT = t.seconds() - lastT;
        Waypoint rloc = Robot.PurePursuit(lastT);
        switch (state) {
            case VISION:
                switch (skystone) {
                    case "C":
                        state = AutoStates.GOTOSKYSTONE;
                        break;
                    case "R":
                        pure.reset(Paths.getRightRed(), false);
                        state = AutoStates.GOTOSKYSTONE;
                        break;
                    case "L":
                        pure.reset(Paths.getLeftRed(), false);
                        state = AutoStates.GOTOSKYSTONE;
                        break;
                    default:
                        state = AutoStates.GOTOSKYSTONE;
                        break;
                }
                break;
            case GOTOSKYSTONE:
                if (!pure.isDone()) {
                    List<Double> powers = pure.followPath(new Position(rloc.getX(), rloc.getY(), rloc.getDdx()), rloc.getDx(), rloc.getDy(), lastT);
                    DriveTrain.setPower(powers.get(0), powers.get(1));
                }
                else if (rloc.getDx() == 0.0 && rloc.getDy() == 0.0) {
                    state = AutoStates.COLLECT;
                }
                break;
            case COLLECT:
                try {
                    Bhorn.toggle();
                    Thread.sleep(500);
                    DriveTrain.setPower(-.25, -.25);
                    Thread.sleep(500);
                    DriveTrain.setPower(.2, .2);
                    Intake.power(1.0);
                    Thread.sleep(300);
                    Bhorn.toggle();
                    DriveTrain.stopMotors();
                    Thread.sleep(1000);
                    Grabber.setPosition(Grabber.POSITIONS.PUSHTHROUGH);
                    Intake.stopIntake();
                    dispatcher.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Grabber.setPosition(Grabber.POSITIONS.CLAMPDOWN);
                        }
                    }, 500);
                } catch (InterruptedException e) {
                    telemetry.addLine("Failed Collect Macro");
                }
                state = AutoStates.TURNTOFOUNDATION;
                break;
            case TURNTOFOUNDATION:
                double error = IMU.getAbsDifference((3*Math.PI)/2.0);
                if (Math.abs(error) > Math.toRadians(.5)) {
                    boolean right = error < 0.0;
                    DriveTrain.turn(Math.abs(error) * .25, right);
                } else {
                    if (skystone.equals("R")) {
                        pure.reset(Paths.getBackUpRedRight(), false);
                    } else {
                        pure.reset(Paths.getBackUpRedLeft(), false);
                    }
                    state = AutoStates.DRIVETOFOUNDATION;
                }
                break;
            case DRIVETOFOUNDATION:
                if (!pure.isDone()) {
                    List<Double> powers = pure.followPath(new Position(rloc.getX(), rloc.getY(), rloc.getDdx()), rloc.getDx(), rloc.getDy(), lastT);
                    DriveTrain.setPower(powers.get(0), powers.get(1));
                } else if (rloc.getDx() == 0.0 && rloc.getDy() == 0.0) {
                    state = AutoStates.ALIGN;
                }
                break;
            case ALIGN:
                double difference = IMU.getAbsDifference((Math.PI)/2.0);
                if (Math.abs(difference) > Math.toRadians(.5)) {
                    boolean right = difference > 0.0;
                    DriveTrain.turn(Math.abs(difference) * .2, right);
                } else {
                    state = AutoStates.DEPOSIT;
                }
                break;
            case DEPOSIT:
                try {
                    DriveTrain.setPower(-.2,-.2);
                    Thread.sleep(700);
                    DriveTrain.stopMotors();
                    Robot.manipMachine();
                    Robot.manipMachine();
                    Robot.manipMachine();
                    Thread.sleep(1000);
                    Grabber.setPosition(Grabber.POSITIONS.DROP);
                    state = AutoStates.LATCH;
                } catch (InterruptedException e) {
                    telemetry.addLine("Failed Deposit");
                }
                break;
            case LATCH:
                FoundationHook.toggle();
                FoundationHook.toggle();
                break;
            case TURN:
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    telemetry.addLine("Failed Turning Build Plate");
                }
                break;
        }
    }
}
