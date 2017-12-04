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

    public ColorSensor colorSensor;

    /* Robot constants */
    public static final double BLOCK_LIFT_LIM = -2880; //Number of encoder degrees
    public static final double RELIC_LIFT_LIM = 1440.0/3.0; //Number of encoder degrees
    public static final double BLOCK_GRAB_LIM = 0.55 ; //0.0 to 1.0 servo degrees
    public static final double RELIC_CLAW_LIM = 0.5; //0.0 to 1.0 servo degrees
    public static final double BLUE_THRESHOLD  = 0;  //Should be int 0 to around 15 
    public static final double RED_THRESHOLD   = 0;  //Should be int 0 to around 15
    public static final double GREEN_THRESHOLD = 0;  //Should be int 0 to around 15
    
    @Override
    public void init(){
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

        

        blockLift.setDirection(   DcMotor.Direction.FORWARD );
        relicPusher.setDirection( DcMotor.Direction.FORWARD );
        relicLifter.setDirection( DcMotor.Direction.FORWARD );
        
        blockGrabL.setDirection(  Servo.Direction.FORWARD   );
        blockGrabR.setDirection(  Servo.Direction.FORWARD   );
        relicClaw.setDirection(   Servo.Direction.FORWARD   );        
        
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

        motorFL.setDirection(     DcMotor.Direction.FORWARD );                
        motorFR.setDirection(     DcMotor.Direction.FORWARD );
        motorBL.setDirection(     DcMotor.Direction.FORWARD );
        motorBR.setDirection(     DcMotor.Direction.FORWARD );

        blockLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.blockLift.setTargetPosition(0);
        blockLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    @Override
    public void loop(){
        loop_drive();
        loop_grabber();
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
        double left_y  = Range.clip( gamepad1.left_stick_y  ,-1,1);
        double right_x = Range.clip( gamepad1.right_stick_x ,-1,1);
        double right_y = Range.clip( gamepad1.right_stick_y ,-1,1);

        double powFR =  Range.clip( left_y + right_y + left_x + right_x , -1 , 1 );
        double powFL =  Range.clip( left_y + right_y - left_x - right_x , -1 , 1 );
        double powBR =  Range.clip( left_y + right_y - left_x + right_x , -1 , 1 );
        double powBL =  Range.clip( left_y + right_y + left_x - right_x , -1 , 1 );

        this.motorFR.setPower(-powFR);
        this.motorFL.setPower(powFL);
        this.motorBR.setPower(powBR);
        this.motorBL.setPower(powBL);
    }
    public void loop_grabber(){
        double leftAng  = Range.clip(this.gamepad1.right_trigger*this.BLOCK_GRAB_LIM,0,1-this.BLOCK_GRAB_LIM);
        double rightAng = Range.clip((1-this.gamepad1.right_trigger)*this.BLOCK_GRAB_LIM+1-this.BLOCK_GRAB_LIM,this.BLOCK_GRAB_LIM,1);
        this.blockGrabL.setPosition(leftAng);
        this.blockGrabR.setPosition(rightAng);
        // this.blockLift.setPower(0.5);
        this.blockLift.setTargetPosition( (int)Math.floor((1-this.gamepad1.left_trigger)*-1800) );
        // blockLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //STOP_AND_RESET_ENCODER - current encoder position zero
        //RUN_TO_POSITION - go to target encoder
        //RUN_USING_ENCODER - constant velocity
        //RUN_WITHOUt_ENCODER - just normal power based
    }
    public void loop_relic(){

    }
    public void loop_colortest(){

    }
}