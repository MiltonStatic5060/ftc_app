package org.firstinspires.ftc.teamcode.exampleops;

import android.hardware.Sensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//@TeleOp(name = "TeleOpScrimmage", group = "Competition2017-2018")

@TeleOp
public class TeleOpScrimmage extends OpMode {

    //Variables.
    DcMotor motorFL = null;
    DcMotor motorFR = null;
    DcMotor motorBL = null;
    DcMotor motorBR = null;
    DcMotor motorDump = null;
    DcMotor motorArm = null;

    Servo armAssist; 

    @Override
    public void init(){

        motorFL = hardwareMap.get(DcMotor.class, "motorFL");
        motorFR = hardwareMap.get(DcMotor.class, "motorFR");
        motorBL = hardwareMap.get(DcMotor.class, "motorBL");
        motorBR = hardwareMap.get(DcMotor.class, "motorBR");

        motorDump = hardwareMap.get(DcMotor.class, "motorDump");
        motorArm = hardwareMap.get(DcMotor.class, "motorArm");
        armAssist = hardwareMap.get(Servo.class, "servoArmAssist");

        armAssist.setDirection( Servo.Direction.FORWARD);
        motorFL.setDirection( DcMotor.Direction.FORWARD);
        motorFR.setDirection( DcMotor.Direction.FORWARD);
        motorBL.setDirection( DcMotor.Direction.FORWARD);
        motorBR.setDirection( DcMotor.Direction.FORWARD);
        motorDump.setDirection( DcMotor.Direction.FORWARD);
        motorArm.setDirection( DcMotor.Direction.FORWARD);
    }

    @Override 
    public void start(){
        // Starting Code when starting opmode on app
    }

    @Override 
    public void loop(){
        //Code that runs repeatedly when the driver hits play

        if(gamepad1.left_stick_y > 0.0) {
            motorFL.setPower(1.0);
            motorBL.setPower(1.0);
        } else if (gamepad1.left_stick_y < 0.0) {
            motorFL.setPower(-1.0);
            motorBL.setPower(-1.0);
        } else {
            motorFL.setPower(0.0);
            motorBL.setPower(0.0);
        }

        if(gamepad1.left_stick_x > 0.0) {
            motorFR.setPower(1.0);
            motorBR.setPower(1.0);
        } else if (gamepad1.left_stick_x < 0.0) {
            motorFR.setPower(-1.0);
            motorBR.setPower(-1.0);
        } else {
            motorFL.setPower(0.0);
            motorBL.setPower(0.0);
        }

        if(gamepad1.left_bumper) {
            motorDump.setPower(1.0);
        } else if(!gamepad1.left_bumper) {
            motorDump.setPower(1.0);
        } else {
            motorDump.setPower(0.0);
        }

        if(gamepad1.right_bumper) {
            motorArm.setPower(1.0);
        } else if(!gamepad1.left_bumper) {
            motorArm.setPower(1.0);
        } else {
            motorArm.setPower(0.0);
        }

        //STRAFING CODE, NOT SURE IF IT WORKS
        if(gamepad1.right_stick_x > 0.0) {
            motorFL.setPower(1.0);
            motorBL.setPower(-1.0);
            motorFR.setPower(-1.0);
            motorBR.setPower(1.0);
        } else if(gamepad1.right_stick_x < 0.0) {
            motorFL.setPower(-1.0);
            motorBL.setPower(1.0);
            motorFR.setPower(1.0);
            motorBR.setPower(-1.0);
        }
    }
}