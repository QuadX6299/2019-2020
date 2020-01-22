package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Modules.Robot;

@TeleOp(name = "Odom Test", group = "TeleOp")
public class OdometryTest extends OpMode {
    Robot r;

   public void init(){
       r = new Robot(this);
   }

   public void loop(){
       r.controls();
   }



}
