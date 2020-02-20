package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp (name = "Outreach", group = "TeleOp")
public class Outreach extends OpMode {
    DcMotorEx fl, fr, bl, br;

    @Override
    public void init() {
        fl = hardwareMap.get(DcMotorEx.class, "FL");
        fr = hardwareMap.get(DcMotorEx.class, "FR");
        bl = hardwareMap.get(DcMotorEx.class, "BL");
        br = hardwareMap.get(DcMotorEx.class, "BR");

        br.setDirection(DcMotor.Direction.REVERSE);
        fr.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        if (Math.abs(gamepad1.left_stick_y) > .01 || Math.abs(gamepad1.right_stick_x) > .01) {
            fl.setPower(clip(gamepad1.left_stick_y + gamepad1.right_stick_x));
            bl.setPower(clip(gamepad1.left_stick_y + gamepad1.right_stick_x));

            fr.setPower(clip(gamepad1.left_stick_y - gamepad1.right_stick_x));
            br.setPower(clip(gamepad1.left_stick_y - gamepad1.right_stick_x));
        } else {
            fl.setPower(0.0);
            bl.setPower(0.0);
            fr.setPower(0.0);
            br.setPower(0.0);
        }
    }

    public double clip(double x) {
        return Math.min(1.0, Math.max(-1.0,x));
    }
}
