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
    Servo servo5;

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
        servo3 = hardwareMap.get( Servo.class, "servo3");
        servo4 = hardwareMap.get( Servo.class, "servo4");
        servo5 = hardwareMap.get( Servo.class, "servo5");
        
        motorFR = hardwareMap.get( DcMotor.class, "motorFR");
        motorFL = hardwareMap.get( DcMotor.class, "motorFL");
        motorBR = hardwareMap.get( DcMotor.class, "motorBR");
        motorBL = hardwareMap.get( DcMotor.class, "motorBL");
    }

    @Override
    public void start(){
        pos1 = 0.05;
        pos2 = 0.95;
        delta1 = 0.01;

        pos3 = 0.16;
        pos4 = 0.84;
        delta4 = 0.01;
    }

    @Override
    public void loop(){
        loop_claw();
        loop_drive();
    }
    public void loop_claw(){
        double dpadUp = (gamepad1.dpad_up) ? -1 : 0;
        double dpadDown = (gamepad1.dpad_down) ? 1 : 0;
        double pow5 = Range.clip( (dpadUp+dpadDown+1)/2.0 ,0,1);
        servo5.setPosition(pow5);

        if(gamepad1.y){
            pos1 -= delta1;
            pos2 = 1-pos1;
        }
        if(gamepad1.a){
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
        pos1 = Range.clip(pos1,0,0.65);
        pos2 = Range.clip(pos2,0.35,1);
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
        
        double cardinal_x  = Range.clip( gamepad1.right_stick_x ,-1,1);
        //when the left joystick is turned to like a certain horizontal thing, then a certaaiinn value is set to the variable
        double cardinal_y  = Range.clip( gamepad1.right_stick_y ,-1,1);
        //when the left joystick is turned to like a certain vertical thing, then a certain value is set to the variable
        double single_x = Range.clip( gamepad1.left_stick_x  ,-1,1); 
        //when the right joystick is turned to like a certain horizontal thing, then a certain value is set to the variable
        double single_y = Range.clip( gamepad1.left_stick_y  ,-1,1);
        //when the right joystick is turned to like a certain vertical thing, then a certain value is set to the variable

        double strafe_val = ( (gamepad1.right_bumper) ? 0.8 : 0 )+( (gamepad1.left_bumper) ? -0.8 : 0 ); // strafe is side to side movements
        strafe_val = Range.clip(strafe_val,-1,1);

        // double powFR =  Range.clip( cardinal_y + single_y + cardinal_x + single_x , -1 , 1 );
        // double powFL =  Range.clip( cardinal_y + single_y - cardinal_x - single_x , -1 , 1 );
        // double powBR =  Range.clip( cardinal_y + single_y - cardinal_x + single_x , -1 , 1 );
        // double powBL =  Range.clip( cardinal_y + single_y + cardinal_x - single_x , -1 , 1 );
        double powFR =  Range.clip( single_y - strafe_val + single_x , -1 , 1 );
        double powFL =  Range.clip( single_y + strafe_val - single_x , -1 , 1 );
        double powBR =  Range.clip( single_y + strafe_val + single_x , -1 , 1 );
        double powBL =  Range.clip( single_y - strafe_val - single_x , -1 , 1 );
        
        motorFR.setPower(-powFR);
        motorFL.setPower(powFL);
        motorBR.setPower(-powBR);
        motorBL.setPower(powBL);
    }
    public void loop_dump(){
        // TODO: complete the dumping section
    }
}