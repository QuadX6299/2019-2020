package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint;
import org.firstinspires.ftc.teamcode.Lib.Path.PureUtilKt;
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D;
import org.firstinspires.ftc.teamcode.Modules.Robot;

import java.util.List;

@Autonomous(name = "goTo Test", group = "Autonomous")
public class PositionTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot r = new Robot(this);
        Waypoint w = new Waypoint(20.0,10.0, 0.0, 10.0, null);
        waitForStart();
        while (opModeIsActive()) {
            Robot.driveTrain.update();
            List<Double> powers = PureUtilKt.goToPosition(Robot.driveTrain.getPoseEstimate(), new Pose2D(0.0,0.0,0.0), w, .3, true);
            Robot.driveTrain.setPower(powers);
        }
    }
}
