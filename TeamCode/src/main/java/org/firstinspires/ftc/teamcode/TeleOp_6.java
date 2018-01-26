package org.firstinspires.ftc.teamcode.competition2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.*;



@TeleOp(name = "Main TeleOp (6)", group = "Competition2017-18")
//@Autonomous(name = "Concept: NullOp", group = "Concept")
//@Disabled
public class TeleOp_6 extends OpMode {
    DcMotor motorFR;
    DcMotor motorFL;
    DcMotor motorBR;
    DcMotor motorBL;
    DcMotor motorLiftR;
    DcMotor motorLiftL;
    @Override
    public void init(){
        String instructions = "add motorFR motorFL motorBR motorBL motorLiftR motorLiftL";
        telemetry.addData("Instructions", instructions);
        motorFR = hardwareMap.get(DcMotor.class,    "motorFR");
        motorFL = hardwareMap.get(DcMotor.class,    "motorFL");
        motorBR = hardwareMap.get(DcMotor.class,    "motorBR");
        motorBL = hardwareMap.get(DcMotor.class,    "motorBL");
        motorLiftR = hardwareMap.get(DcMotor.class, "motorLiftR");
        motorLiftL = hardwareMap.get(DcMotor.class, "motorLiftL");
    }
    @Override
    public void start(){

    }
    @Override
    public void loop(){
        loop_drive();
        loop_lift();
    }
    public void loop_lift(){

        double powR = gamepad1.left_trigger - gamepad1.right_trigger;
        double powL = gamepad1.left_trigger - gamepad1.right_trigger;

        motorLiftR.setPower(-powR);
        motorLiftL.setPower(powL);
        telemetry.addData("motorLiftR",-powR);
        telemetry.addData("motorLiftL",powL);
        telemetry.update();
    }
    /**
     * gamepad1
     * left_stick one stick rotation drive
     * right_stick one stick cardinal drive
     * right_bumper/left_bumper strafe @Disabled
     * 
     * devices:
     * motorFR
     * motorFL
     * motorBR
     * motorBL
     */
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

        double powFR =  Range.clip( cardinal_y + single_y - cardinal_x + single_x , -1 , 1 );
        double powFL =  Range.clip( cardinal_y + single_y + cardinal_x - single_x , -1 , 1 );
        double powBR =  Range.clip( cardinal_y + single_y + cardinal_x + single_x , -1 , 1 );
        double powBL =  Range.clip( cardinal_y + single_y - cardinal_x - single_x , -1 , 1 );
        // double powFR =  Range.clip( single_y - strafe_val + single_x , -1 , 1 );
        // double powFL =  Range.clip( single_y + strafe_val - single_x , -1 , 1 );
        // double powBR =  Range.clip( single_y + strafe_val + single_x , -1 , 1 );
        // double powBL =  Range.clip( single_y - strafe_val - single_x , -1 , 1 );
        
        motorFR.setPower(-powFR);
        motorFL.setPower(powFL);
        motorBR.setPower(-powBR);
        motorBL.setPower(powBL);
        telemetry.addData("motorFR",-powFR);
        telemetry.addData("motorFL",powFL);
        telemetry.addData("motorBR",-powBR);
        telemetry.addData("motorBL",powBL);
    }
}