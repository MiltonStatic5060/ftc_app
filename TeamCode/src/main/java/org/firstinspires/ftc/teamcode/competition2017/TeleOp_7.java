package org.firstinspires.ftc.teamcode.competition2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.*;



@TeleOp(name = "Main TeleOp (7)", group = "Competition2017-18")
//@Autonomous(name = "Concept: NullOp", group = "Concept")
//@Disabled
public class TeleOp_7 extends OpMode {
    DcMotor motorLeft;
    DcMotor motorRight;
    DcMotor motorGrabL;
    DcMotor motorGrabR;
    DcMotor motorLift;

    @Override
    public void init(){
        motorLeft = hardwareMap.get(DcMotor.class, "motorLeft");
        motorRight = hardwareMap.get(DcMotor.class, "motorRight");
        motorGrabL = hardwareMap.get(DcMotor.class, "motorGrabL");
        motorGrabR = hardwareMap.get(DcMotor.class, "motorGrabR");
        motorLift = hardwareMap.get(DcMotor.class, "motorLift");
    }
    @Override
    public void start(){}
    @Override
    public void loop(){
        loop_drive();
    }
    public void loop_drive(){
        double power = gamepad1.left_stick_y;
        double direction = gamepad1.left_stick_x;
        double powLeft = power + direction;
        double powRight = power - direction;
        powLeft = Range.clip(powLeft,-1,1);
        powRight = Range.clip(powRight,-1,1);
        
        motorLeft.setPower(powLeft);
        motorRight.setPower(-powRight);
    }
    public void loop_grab(){
        double power = gamepad1.right_stick_y;
        double direction = gamepad1.right_stick_x;
        double powLeft = power + direction;
        double powRight = power - direction;
        powLeft = Range.clip(powLeft,-1,1);
        powRight = Range.clip(powRight,-1,1);
        
        motorGrabL.setPower(powLeft);
        motorGrabR.setPower(-powRight);
    }
    public void loop_lift(){
        double powLift = gamepad1.left_trigger - gamepad1.right_trigger;
        powLift = Range.clip( powLift * 0.8 , -1 , 1 );
        motorLift.setPower(powLift);
    }
    

}