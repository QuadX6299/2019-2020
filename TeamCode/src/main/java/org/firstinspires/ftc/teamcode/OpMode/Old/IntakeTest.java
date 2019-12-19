
package org.firstinspires.ftc.teamcode.OpMode.Old;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "IntakeTest", group = "TeleOp")
public class IntakeTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor intakeL = hardwareMap.get(DcMotor.class, "intakeL");
        DcMotor intakeR = hardwareMap.get(DcMotor.class, "intakeR");

          //Uncomment whichever you need to flip the intake
//        intakeL.setDirection(DcMotor.Direction.REVERSE);
//        intakeR.setDirection(DcMotor.Direction.REVERSE);



        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.left_bumper) {
                //spit out
                intakeL.setPower(-1.0);
                intakeR.setPower(1.0);
            } else if (gamepad1.right_bumper){
                //intake
                intakeL.setPower(1.0);
                intakeR.setPower(-1.0);
            } else {
                intakeL.setPower(0.0);
                intakeR.setPower(0.0);
            }
        }
    }
}