package org.firstinspires.ftc.teamcode.OpMode.Old;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HerculesLibraries.Vision.BitMap;
import org.firstinspires.ftc.teamcode.OpMode.Robot;
import org.firstinspires.ftc.teamcode.Robot.DriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Grabber;
import org.firstinspires.ftc.teamcode.Robot.Lift;
import org.firstinspires.ftc.teamcode.Robot.Sensors.IMU;
import org.firstinspires.ftc.teamcode.lib.Constraints.MotionConstraint;
import org.firstinspires.ftc.teamcode.lib.Constraints.PIDFCoefficients;
import org.firstinspires.ftc.teamcode.lib.Constraints.TankKinematics;
import org.firstinspires.ftc.teamcode.lib.Coords.Position;
import org.firstinspires.ftc.teamcode.lib.Coords.State;
import org.firstinspires.ftc.teamcode.lib.Coords.Waypoint;
import org.firstinspires.ftc.teamcode.lib.Path.PathFollower;
import org.firstinspires.ftc.teamcode.lib.Path.PathGenerator;
import org.firstinspires.ftc.teamcode.lib.Path.Paths;
import org.firstinspires.ftc.teamcode.lib.Path.TestPaths;

import java.util.List;

@TeleOp(name = "Pursuit", group = "TeleOp")
public class TeleOPPP extends OpMode {
    PathFollower pp;
    ElapsedTime t;
    double lastT = 0.0;
    public void init() {
        //TestPaths.straightLine, 10.0, TankKinematics(DriveTrain.width), PIDFCoefficients(.01,0.0,.04,1/30.0,.0002), MotionConstraint(30.0, 5.0, 3.0)
        Robot.init(this);
        t = new ElapsedTime();
        pp = new PathFollower(Paths.getStraightBlue(), 10.0, new TankKinematics(DriveTrain.width + 10.0), new PIDFCoefficients(.015,0.0,.0003,1/10.0,.0003),
        10.0, this);
        t.reset();
        Robot.reset();
        DriveTrain.resetEncoders();
    }

    public void loop() {
//        pp.reset(Paths.getStraightLine());
        lastT = t.seconds() - lastT;
        Waypoint rloc = Robot.PurePursuit(lastT);
        List<Double> powers = pp.followPath(new Position(rloc.getX(), rloc.getY(), rloc.getDdx()), rloc.getDx(), rloc.getDy(), lastT);
        DriveTrain.setPower(powers.get(0), powers.get(1));
        telemetry.addData("Left: ", powers.get(0));
        telemetry.addData("Right: ", powers.get(1));
        telemetry.update();
    }
}