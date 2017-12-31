package org.firstinspires.ftc.teamcode.competition2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.*;



@TeleOp(name = "TeleOp3", group = "Competition2017-18")
//@Autonomous(name = "Concept: NullOp", group = "Concept")
//@Disabled
public class TeleOp_3 extends OpMode {
    /* Drive motors */
    public DcMotor  motorFL;
    public DcMotor  motorFR;
    public DcMotor  motorBL;
    public DcMotor  motorBR;
    
    /* Block Lift motors */
    public DcMotor  blockLift;
    public Servo    blockGrabL;
    public Servo    blockGrabR;

    /* Relic Manipulation motors */
    public DcMotor  relicPusher;
    public DcMotor  relicLifter;
    public Servo    relicClaw;
    public Servo    relicPanel;

    public ColorSensor colorSensor;

    /* Robot constants */
    public static final double BLOCK_LIFT_LIM = 2800; //Number of encoder degrees
    public static final double RELIC_LIFT_LIM = -650; //Number of encoder degrees
    public static final double BLOCK_GRAB_LIM = 0.45 ; //0.0 to 1.0 servo degrees
    public static final double RELIC_CLAW_LIM = 0.3; //0.0 to 1.0 servo degrees
    public static final double BLUE_THRESHOLD  = 4;  //Should be int 0 to around 15 
    public static final double RED_THRESHOLD   = 4;  //Should be int 0 to around 15
    public static final double GREEN_THRESHOLD = 4;  //Should be int 0 to around 15
    
    @Override
    public void init(){
        //A list of each motor used for the robit
        motorFL     = hardwareMap.get( DcMotor.class , "motorFL"     );
        motorFR     = hardwareMap.get( DcMotor.class , "motorFR"     );
        motorBL     = hardwareMap.get( DcMotor.class , "motorBL"     );
        motorBR     = hardwareMap.get( DcMotor.class , "motorBR"     );
        blockLift   = hardwareMap.get( DcMotor.class , "blockLift"   );
        blockGrabL  = hardwareMap.get( Servo.class   , "blockGrabL"  );
        blockGrabR  = hardwareMap.get( Servo.class   , "blockGrabR"  );
        relicPusher = hardwareMap.get( DcMotor.class , "relicPusher" );
        relicLifter = hardwareMap.get( DcMotor.class , "relicLifter" );
        relicClaw   = hardwareMap.get( Servo.class   , "relicClaw"   );
        colorSensor = hardwareMap.get( ColorSensor.class, "colorSensor");
        relicPanel  = hardwareMap.get( Servo.class   , "relicPanel");

        
        //The direction of the robit's motors are being set
        blockLift.setDirection(   DcMotor.Direction.FORWARD );
        relicPusher.setDirection( DcMotor.Direction.FORWARD );
        relicLifter.setDirection( DcMotor.Direction.FORWARD );
        
        blockGrabL.setDirection(  Servo.Direction.FORWARD   );
        blockGrabR.setDirection(  Servo.Direction.FORWARD   );
        relicClaw.setDirection(   Servo.Direction.REVERSE   );     
        relicPanel.setDirection(  Servo.Direction.REVERSE   );     
        
        // Set all motors to zero power
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
        blockLift.setPower(0);
        relicPusher.setPower(0);
        relicLifter.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        motorFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        blockLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        relicPusher.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        relicLifter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);  

        motorFL.setDirection( DcMotor.Direction.FORWARD );                
        motorFR.setDirection( DcMotor.Direction.FORWARD );
        motorBL.setDirection( DcMotor.Direction.FORWARD );
        motorBR.setDirection( DcMotor.Direction.REVERSE );

        blockLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.blockLift.setTargetPosition(0);
        blockLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    //The loop function calls on 4 other functions that were described before
    @Override
    public void loop(){
        loop_drive(); //The driving function
        loop_grabber(); //The grabbing function
        loop_colortest(); //The color sensoring function
        loop_relic(); //The relic grabber function
    }
    @Override
    public void start(){
        
    }
    public boolean isNear( double num, double target, double thresh ){
        return Math.abs( target - num )<=thresh;
    }
    public boolean isNear( int num, int target, int thresh ){
        return Math.abs( target - num )<=thresh;
    }

    /**
     * returns the String BLUE RED GREEN NONE to indicate the highest color value
     *
     *
     *
     */
    public String whichColor(){
        this.colorSensor.enableLed(true);
        int blue = this.colorSensor.blue();
        int red = this.colorSensor.red();
        int green = this.colorSensor.green();
        int alpha = this.colorSensor.alpha();
        int thresh = 0;
        if( isNear(blue,red,thresh) || isNear(blue,green,thresh) || isNear(green,red,thresh) ){
            return "NEITHER";
        } else if (isNear(Math.max(blue,Math.max(red,green)),blue,this.BLUE_THRESHOLD)){
            return "BLUE";
        } else if (isNear(Math.max(blue,Math.max(red,green)),red,this.RED_THRESHOLD)){
            return "RED";
        } else if (isNear(Math.max(blue,Math.max(red,green)),green,this.GREEN_THRESHOLD)){
            return "GREEN";
        } else {
            return "NEITHER";
        }
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

        double powFR =  Range.clip( left_y + right_y + left_x + right_x , -1 , 1 );
        double powFL =  Range.clip( left_y + right_y - left_x - right_x , -1 , 1 );
        double powBR =  Range.clip( left_y + right_y - left_x + right_x , -1 , 1 );
        double powBL =  Range.clip( left_y + right_y + left_x - right_x , -1 , 1 );

        motorFR.setPower(powFR);
        motorFL.setPower(powFL);
        motorBR.setPower(powBR);
        motorBL.setPower(powBL);
    }
    public void loop_grabber(){
        double leftAng  = Range.clip(gamepad1.right_trigger*BLOCK_GRAB_LIM,0,1-BLOCK_GRAB_LIM);
        double rightAng = Range.clip((1-gamepad1.right_trigger)*BLOCK_GRAB_LIM+1-BLOCK_GRAB_LIM,BLOCK_GRAB_LIM,1);
        blockGrabL.setPosition(leftAng);
        blockGrabR.setPosition(rightAng);
        // blockLift.setPower(0.5);
        blockLift.setTargetPosition( (int)Math.floor((gamepad1.left_trigger)*BLOCK_LIFT_LIM) );
        telemetry.addData("Block Lifter Target Position",(int)Math.floor((gamepad1.left_trigger)*2800));
        telemetry.addData("Block Lifter Current Position,",blockLift.getCurrentPosition());
        if(gamepad1.left_bumper)
            blockLift.setPower(0.5);
        else
            blockLift.setPower(0);
        blockLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //STOP_AND_RESET_ENCODER - current encoder position zero
        //RUN_TO_POSITION - go to target encoder
        //RUN_USING_ENCODER - constant velocity
        //RUN_WITHOUT_ENCODER - just normal power based
    }
    public void loop_relic(){
        relicClaw.setPosition((1-RELIC_CLAW_LIM)*gamepad2.right_trigger);
        telemetry.addData("Claw Position",relicClaw.getPosition());
        this.relicLifter.setTargetPosition( (int)Math.floor((this.gamepad2.left_trigger)*RELIC_LIFT_LIM) );
        telemetry.addData("Relic Lifter Target Position",(int)Math.floor((this.gamepad2.left_trigger)*RELIC_LIFT_LIM));
        telemetry.addData("Relic Lifter Current Position,",relicLifter.getCurrentPosition());
        if(gamepad2.left_bumper)
            relicLifter.setPower(0.1);
        else
            relicLifter.setPower(0);
        relicLifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        relicPusher.setPower(gamepad2.right_stick_y*0.6);
        
        if(gamepad2.dpad_up)
            relicPanel.setPosition(0);
        else if(gamepad2.dpad_left)
            relicPanel.setPosition(0.3);
        else
            relicPanel.setPosition(0.8);
    }
    
    public void loop_colortest(){
        telemetry.addData("Color Sensor",whichColor());
    }
}