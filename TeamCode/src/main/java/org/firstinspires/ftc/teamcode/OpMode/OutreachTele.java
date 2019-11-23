package org.firstinspires.ftc.teamcode.OpMode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Outreach", group = "TeleOp")
public class OutreachTele extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor fl = hardwareMap.get(DcMotor.class, "fl");
        DcMotor fr = hardwareMap.get(DcMotor.class, "fr");
        DcMotor bl = hardwareMap.get(DcMotor.class, "bl");
        DcMotor br = hardwareMap.get(DcMotor.class, "br");

        // Uncomment to reverse left side <--
//        fl.setDirection(DcMotor.Direction.REVERSE);
//        bl.setDirection(DcMotor.Direction.REVERSE);

        // Uncomment to reverse right side <--
//        fr.setDirection(DcMotor.Direction.REVERSE);
//        br.setDirection(DcMotor.Direction.REVERSE);
        
        waitForStart();
        
        while (opModeIsActive()) {
            if (Math.abs(gamepad1.left_stick_y) > .01 || Math.abs(gamepad1.right_stick_x) > .01) {
                fl.setPower(gamepad1.left_stick_y + gamepad1.right_stick_x);
                bl.setPower(gamepad1.left_stick_y + gamepad1.right_stick_x);
                br.setPower(gamepad1.left_stick_y-gamepad1.right_stick_x);
                fr.setPower(gamepad1.left_stick_y-gamepad1.right_stick_x);
            } else {
                fl.setPower(0.0);
                bl.setPower(0.0);
                fr.setPower(0.0);
                br.setPower(0.0);
            }
        }
    }
}
