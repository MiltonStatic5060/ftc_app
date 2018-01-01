package org.firstinspires.ftc.teamcode.competition2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.*;



@TeleOp(name = "TeleOp4", group = "Competition2017-18")
//@Autonomous(name = "Concept: NullOp", group = "Concept")
//@Disabled
public class TeleOp_4 extends OpMode {
    /* Drive motors */
    public DcMotor  motorFL;
    public DcMotor  motorFR;
    public DcMotor  motorBL;
    public DcMotor  motorBR;
    
    Servo servo1;
    Servo servo2;
    Servo servo3;
    Servo servo4;

    double pos1;
    double pos2;
    double delta1;

    double pos3;
    double pos4;
    double delta4;

    boolean alternateOn = false;

    @Override
    public void init(){
        servo1 = hardwareMap.get( Servo.class, "servo1");
        servo2 = hardwareMap.get( Servo.class, "servo2");
        // servo3 = hardwareMap.get( Servo.class, "servo3");
        // servo3 = hardwareMap.get( Servo.class, "servo3");
        servo3 = hardwareMap.get( Servo.class, "servo3");
        servo4 = hardwareMap.get( Servo.class, "servo4");
    }

    @Override
    public void start(){
        pos1 = 0;
        pos2 = 1.0;
        delta1 = 0.01;

        pos3 = 0.0;
        pos4 = 1.0;
        delta4 = 0.01;
    }

    @Override
    public void loop(){
        if(pos1>=.65 || pos1<=.06){
            delta1 *= -1;
            pos1 += delta1;
        }
        if(gamepad1.y||alternateOn){
            pos1 += delta1;
            pos2 = 1-pos1;
        }
        
        if(gamepad1.x){
            pos4 += delta4;
            pos3 = 1-pos4;
        }
        if(gamepad1.b){
            pos4 -= delta4;
            pos3 = 1-pos4;
        }
        if(gamepad1.back)
            alternateOn=true;
        if(gamepad1.guide)
            alternateOn=false;
        pos1 = Range.clip(pos1,0,1);
        pos2 = Range.clip(pos2,0,1);
        pos3 = Range.clip(pos3,0.16,1);
        pos4 = Range.clip(pos4,0,0.84);
        telemetry.addData("pos1",pos1);
        telemetry.addData("pos2",pos2+"\n");
        telemetry.addData("pos3",pos3);
        telemetry.addData("pos4",pos4);
        telemetry.addData("Press Y to make servos 1 and 2 alternate",gamepad1.y);
        telemetry.addData("Press X to make servos 3 and 4 alternate",gamepad1.x);
        servo1.setPosition(pos1);
        servo2.setPosition(pos2);
        servo3.setPosition(pos3);
        servo4.setPosition(pos4);
    }

    
    public void loop_drive(){
        
        double left_x  = Range.clip( gamepad1.left_stick_x  ,-1,1); 
        //when the left joystick is turned to like a certain horizontal thing, then a certaaiinn value is set to the variable
        double left_y  = Range.clip( gamepad1.left_stick_y  ,-1,1);
        //when the left joystick is turned to like a certain vertical thing, then a certain value is set to the variable
        double right_x = Range.clip( gamepad1.right_stick_x ,-1,1);
        //when the right joystick is turned to like a certain horizontal thing, then a certain value is set to the variable
        double right_y = Range.clip( gamepad1.right_stick_y ,-1,1);
        //when the right joystick is turned to like a certain vertical thing, then a certain value is set to the variable

        double strife_val = Range.clip( (gamepad1.right_bumper) ? -0.8 : 0.8 ,-1,1); // strafe is side to side movements
        

        double powFR =  Range.clip( left_y + right_y + left_x + right_x , -1 , 1 );
        double powFL =  Range.clip( left_y + right_y - left_x - right_x , -1 , 1 );
        double powBR =  Range.clip( left_y + right_y - left_x + right_x , -1 , 1 );
        double powBL =  Range.clip( left_y + right_y + left_x - right_x , -1 , 1 );
        // double powFR =  Range.clip( left_y + strife_val + left_x , -1 , 1 );
        // double powFL =  Range.clip( left_y - strife_val - left_x , -1 , 1 );
        // double powBR =  Range.clip( left_y - strife_val + left_x , -1 , 1 );
        // double powBL =  Range.clip( left_y + strife_val - left_x , -1 , 1 );
        
        motorFR.setPower(powFR);
        motorFL.setPower(powFL);
        motorBR.setPower(powBR);
        motorBL.setPower(powBL);
    }
}