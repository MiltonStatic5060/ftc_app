package org.firstinspires.ftc.teamcode.competition2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.hardware.HardwareMap;


@Autonomous(name = "Main AutoOp (6)", group = "Competition2017-18")
//@Autonomous(name = "Concept: NullOp", group = "Concept")
@Disabled
public class HardwareOp_6 extends OpMode {
    ElapsedTime runtime = new ElapsedTime();
    double nowTime;
    double targetTime;

    DcMotor motorFR;
    DcMotor motorFL;
    DcMotor motorBR;
    DcMotor motorBL;
    DcMotor motorLiftR;
    DcMotor motorLiftL;
    TouchSensor touch1;

    int opCounter = 0;
    HardwareMap hardwareMap = null;

    public HardwareOp_6(HardwareMap hrd){
        this.hardwareMap = hrd;
    }

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
        touch1 = hardwareMap.get(TouchSensor.class, "touch1");
    }
    @Override
    public void start(){
        runtime.reset();
        nowTime = runtime.milliseconds();
    }
    @Override
    public void loop(){
        if(runtime.milliseconds()<1000){
            
            switch(opCounter){
                case 0:
                    auto_drive(-1,0,0);
                    telemetry.addData("Mode","Forward");
                    break;
                case 1:
                    auto_drive(1,0,0);
                    telemetry.addData("Mode","Backward");
                    break;
                case 2:
                    auto_drive(0,-1,0);
                    telemetry.addData("Mode","Leftward");
                    break;
                case 3:
                    auto_drive(0,1,0);
                    telemetry.addData("Mode","Rightward");
                    break;
                case 4:
                    auto_drive(0,0,1);
                    telemetry.addData("Mode","Clockwise");
                    break;
                case 5:
                    auto_drive(0,0,-1);
                    telemetry.addData("Mode","Counterwise");
                    break;
                default:
                    opCounter=0;
            }
        } else if( touch1.isPressed() ){
            runtime.reset();
            opCounter++;
        } else {
            auto_drive(0,0,0);
        }
    }
    /**
     * description: autonomous version of lift control
     * @param power accepts values -1.0 to 1.0 for up/down lifting. 0 is resting.
     */
    public void auto_lift(double power){

        double powR = power;
        double powL = power;

        motorLiftR.setPower(-powR);
        motorLiftL.setPower(powL);
        telemetry.addData("motorLiftR",-powR);
        telemetry.addData("motorLiftL",powL);
        telemetry.update();
    }

    /**
     * 
     * description: autonomous version of driving function
     * @param cardinal_y -1.0 to 1.0 forward/backward
     * @param cardinal_x -1.0 to 1.0 leftward/rightward
     * @param single_x   -1.0 to 1.0 counter/clock wise
     * 
     */
    public void auto_drive(double cardinal_y, double cardinal_x, double single_x){
        
        // double cardinal_x  = Range.clip( gamepad1.right_stick_x ,-1,1);
        //when the left joystick is turned to like a certain horizontal thing, then a certaaiinn value is set to the variable
        // double cardinal_y  = Range.clip( gamepad1.right_stick_y ,-1,1);
        //when the left joystick is turned to like a certain vertical thing, then a certain value is set to the variable
        // double single_x = Range.clip( gamepad1.left_stick_x  ,-1,1); 
        //when the right joystick is turned to like a certain horizontal thing, then a certain value is set to the variable
        double single_y = Range.clip( gamepad1.left_stick_y  ,-1,1);
        //when the right joystick is turned to like a certain vertical thing, then a certain value is set to the variable
        
        cardinal_y *= -1;
        cardinal_x *= -1;
        single_y = 0;


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

    /**
     * gamepad1
     * left_trigger/right_trigger - lift up/down
     */
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