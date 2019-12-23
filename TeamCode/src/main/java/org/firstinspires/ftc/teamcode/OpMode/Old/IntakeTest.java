
package org.firstinspires.ftc.teamcode.OpMode.Old;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "IntakeTest", group = "TeleOp")
public class IntakeTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
//        DcMotor intakeL = hardwareMap.get(DcMotor.class, "intakeL");
//        DcMotor intakeR = hardwareMap.get(DcMotor.class, "intakeR");
        DcMotor liftL = hardwareMap.get(DcMotor.class, "liftL");
        DcMotor liftR = hardwareMap.get(DcMotor.class, "liftR");

        liftL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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

        while (opModeIsActive()) {
            if (gamepad2.right_stick_y > 0.05) {
                liftL.setPower(gamepad2.left_stick_y);
                liftR.setPower(gamepad2.left_stick_y);
                //this is up
            } else if (gamepad2.right_stick_y < 0.05) {
                liftL.setPower(gamepad2.left_stick_y);
                liftR.setPower(gamepad2.left_stick_y);
            } else {
                liftL.setPower(0.0);
                liftR.setPower(0.0);
            }
//            if (gamepad1.left_bumper) {
//                //spit out
//                intakeL.setPower(-1.0);
//                intakeR.setPower(1.0);
//            } else if (gamepad1.right_bumper){
//                //intake
//                intakeL.setPower(1.0);
//                intakeR.setPower(-1.0);
//            } else {
//                intakeL.setPower(0.0);
//                intakeR.setPower(0.0);
//            }

        }
    }
}