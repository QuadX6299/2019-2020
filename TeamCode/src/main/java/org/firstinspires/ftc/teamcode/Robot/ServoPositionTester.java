package org.firstinspires.ftc.teamcode.Robot;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "ServoPositionTester", group = "TeleOp")
public class ServoPositionTester extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Servo pog = hardwareMap.get(Servo.class, "testS");

        ElapsedTime pt = new ElapsedTime();
        //Uncomment whichever you need to flip the intake
//        intakeL.setDirection(DcMotor.Direction.REVERSE);
//        intakeR.setDirection(DcMotor.Direction.REVERSE);


        //hub2
        //port0 liftL
        //port1 liftR
        //port2 FR
        //port3 BL

        //hub5


        waitForStart();
        double position = 0.0;
        while (opModeIsActive()) {

            if (gamepad1.left_bumper && pt.milliseconds() > 200) {
                //spit out
                position += .1;
                pog.setPosition(position);
                pt.reset();
            } else if (gamepad1.right_bumper && pt.milliseconds() > 200) {
                //intake
                position -= .1;
                pog.setPosition(position);
                pt.reset();
            }
            telemetry.addData("Pog Pose", pog.getPosition());
            telemetry.update();

        }
    }
}