package org.firstinspires.ftc.teamcode.competition2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.*;



@Autonomous(name = "Main AutoOp (4)", group = "Competition2017-18")
//@TeleOp(name = "Concept: NullOp", group = "Concept")
//@Disabled
public class AutoOp_4 extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private double nowTime;
    private double targetTime;


    /* Drive motors */
    DcMotor  motorFL;
    DcMotor  motorFR;
    DcMotor  motorBL;
    DcMotor  motorBR;

    DcMotor motorDump;
    DcMotor motorArm;

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

    int posArm;
    int deltaArm;
    int upperLimit;
    int lowerLimit;

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

        motorDump = hardwareMap.get( DcMotor.class, "motorDump");
        motorArm  = hardwareMap.get( DcMotor.class, "motorArm");
    }

    @Override
    public void start(){
        runtime.reset();
        pos1 = 0.05;
        pos2 = 0.95;
        delta1 = 0.025; // Determines claw's up and down speed

        pos3 = 0.16;
        pos4 = 0.84;
        delta4 = 0.025; // Determines claw's contract expand speed

        posArm = 0;
        deltaArm = (1440/360) * 3; //sets speed of arm movement
        lowerLimit = (1440/360)*-100; // -110 deg * (1440 enc/360 deg) = -440 enc
        upperLimit = (1440/360)*0; 

        nowTime = runtime.milliseconds();
    }

    @Override
    public void loop(){
        // loop_claw();
        // loop_drive();
        // loop_dump();
        // (y, cardinal_x, cardinal_y);
        if(runtime.milliseconds()>2500)
            auto_drive(0,0,0);
        else
            auto_drive(-1,0,0);
        auto_dump(0); // -1 is up, +1 is down
        //auto_claw();
    }    
    public void auto_drive(double y, double cardinal, double single){
        
        double cardinal_x  = Range.clip( cardinal ,-1,1);
        //when the left joystick is turned to like a certain horizontal thing, then a certaaiinn value is set to the variable
        double cardinal_y  = Range.clip( y ,-1,1);
        //when the left joystick is turned to like a certain vertical thing, then a certain value is set to the variable
        double single_x = Range.clip( single  ,-1,1); 
        //when the right joystick is turned to like a certain horizontal thing, then a certain value is set to the variable
        double single_y = Range.clip( 0  ,-1,1);
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
    
    }
    /**
     * power +1 is down -1 is up
     */
    public void auto_dump(double power){
        double stickPower = gamepad2.left_trigger-gamepad2.right_trigger+( (gamepad1.guide) ? 1 : 0) - ((gamepad1.back) ? 1 : 0);
        double dumpPower = Range.clip( (power),-1,1);
        motorDump.setPower(dumpPower);
        telemetry.addData("Dump Power",dumpPower);
        // TODO: complete the dumping section
        //Clockwise is Positive Value (Motor axle pointing towards face)
        //Counterclockwise is Negative Value
    }

    public void auto_claw(){
        double dpadUp = (gamepad1.dpad_up || gamepad2.dpad_up) ? -1 : 0; // -1 is true, 0 is false
        
        double dpadDown = (gamepad1.dpad_down || gamepad2.dpad_down) ? 1 : 0; // 1 is true, 0 is false

        double thumbPower = Range.clip(dpadUp+dpadDown+gamepad2.left_stick_y,-1,1);
        double pow5 = Range.clip( (thumbPower+1)/2.0 ,0,1);
        servo5.setPosition(pow5);

        if(gamepad2.y || gamepad1.y || gamepad1.left_bumper ){
            pos1 -= delta1; //  left_bumper1, y on both pads raise up claw
            posArm += deltaArm;
        }
        if(gamepad2.a || gamepad1.a || gamepad1.right_bumper){
            pos1 += delta1; // right_bumper1, a on both pads lower down claw
            posArm -= deltaArm;
        }
        pos1 += gamepad2.right_stick_y*delta1; // right_stick_y move claw up/down by delta1 factor

        if(gamepad2.x || gamepad1.x || gamepad1.left_trigger>0.5){
            pos4 += delta4;
        }
        if(gamepad2.b || gamepad1.b || gamepad1.right_trigger>0.5){
            pos4 -= delta4;
        }
        pos4 -= gamepad2.right_stick_x*delta4; // right_stick_x claw expand/retract by delta4 factor

        pos2 = 1-pos1;
        pos1 = Range.clip(pos1,0,0.65);
        pos2 = Range.clip(pos2,0.35,1);

        pos3 = 1-pos4;
        pos3 = Range.clip(pos3,0.16,1);
        pos4 = Range.clip(pos4,0,0.84);

        if(gamepad2.start)
            motorArm.setPower(0.0);
        else
            motorArm.setPower(0.4);
        posArm = (int)(Range.clip(posArm,lowerLimit,upperLimit));
        motorArm.setTargetPosition(posArm);
        motorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData("Arm Position",posArm);
        telemetry.addData("Arm Power   ",motorArm.getPower());

        telemetry.addData("servo arm  1",pos1);
        telemetry.addData("servo arm  2",pos2);
        telemetry.addData("servo claw 3",pos3);
        telemetry.addData("servo claw 4",pos4);
        servo1.setPosition(pos1);
        servo2.setPosition(pos2);
        servo3.setPosition(pos3);
        servo4.setPosition(pos4);
    }

    /**
     * gamepad1
     * dpad_up/dpad_down:           thumb power control
     * left_trigger/right_trigger:  claw control expand/contract
     * y/a:                         claw control up/down
     * a/x:                         claw control expand/contract
     *      
     * gamepad2
     * dpad_up/dpad_down: thumb power control up/down
     * left_stick_y:      thumb power control
     * y/a:               claw control up/down
     * a/x:               claw control expand/contract
     * 
     */
    public void loop_claw(){
        double dpadUp = (gamepad1.dpad_up || gamepad2.dpad_up) ? -1 : 0; // -1 is true, 0 is false
        
        double dpadDown = (gamepad1.dpad_down || gamepad2.dpad_down) ? 1 : 0; // 1 is true, 0 is false

        double thumbPower = Range.clip(dpadUp+dpadDown+gamepad2.left_stick_y,-1,1);
        double pow5 = Range.clip( (thumbPower+1)/2.0 ,0,1);
        servo5.setPosition(pow5);

        if(gamepad2.y || gamepad1.y || gamepad1.left_bumper ){
            pos1 -= delta1; //  left_bumper1, y on both pads raise up claw
            posArm += deltaArm;
        }
        if(gamepad2.a || gamepad1.a || gamepad1.right_bumper){
            pos1 += delta1; // right_bumper1, a on both pads lower down claw
            posArm -= deltaArm;
        }
        pos1 += gamepad2.right_stick_y*delta1; // right_stick_y move claw up/down by delta1 factor

        if(gamepad2.x || gamepad1.x || gamepad1.left_trigger>0.5){
            pos4 += delta4;
        }
        if(gamepad2.b || gamepad1.b || gamepad1.right_trigger>0.5){
            pos4 -= delta4;
        }
        pos4 -= gamepad2.right_stick_x*delta4; // right_stick_x claw expand/retract by delta4 factor

        pos2 = 1-pos1;
        pos1 = Range.clip(pos1,0,0.65);
        pos2 = Range.clip(pos2,0.35,1);

        pos3 = 1-pos4;
        pos3 = Range.clip(pos3,0.16,1);
        pos4 = Range.clip(pos4,0,0.84);

        if(gamepad2.start)
            motorArm.setPower(0.0);
        else
            motorArm.setPower(0.4);
        posArm = (int)(Range.clip(posArm,lowerLimit,upperLimit));
        motorArm.setTargetPosition(posArm);
        motorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData("Arm Position",posArm);
        telemetry.addData("Arm Power   ",motorArm.getPower());

        telemetry.addData("servo arm  1",pos1);
        telemetry.addData("servo arm  2",pos2);
        telemetry.addData("servo claw 3",pos3);
        telemetry.addData("servo claw 4",pos4);
        servo1.setPosition(pos1);
        servo2.setPosition(pos2);
        servo3.setPosition(pos3);
        servo4.setPosition(pos4);
    }
    /**
     * gamepad1
     * left_stick one stick rotation drive
     * right_stick one stick cardinal drive
     * right_bumper/left_bumper strafe @Disabled
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
    }
    /**
     * gamepad2
     * left trigger is dump down
     * right trigger is dump up
     * 
     * gamepad1
     * guide is dump down
     * back is dump up
     */
    public void loop_dump(){
        double stickPower = gamepad2.left_trigger-gamepad2.right_trigger+( (gamepad1.guide) ? 1 : 0) - ((gamepad1.back) ? 1 : 0);
        double dumpPower = Range.clip( (stickPower),-1,1);
        motorDump.setPower(dumpPower);
        telemetry.addData("Dump Power",dumpPower);
        // TODO: complete the dumping section
        //Clockwise is Positive Value (Motor axle pointing towards face)
        //Counterclockwise is Negative Value
    }
}