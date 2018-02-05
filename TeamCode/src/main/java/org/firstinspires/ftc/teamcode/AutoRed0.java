package org.firstinspires.ftc.teamcode.competition2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.hardware.HardwareMap;


//A whole bunch of Vuforia Stuff
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
//No more Vuforia Stuff

@Autonomous(name = "Red Closer", group = "Competition2017-18")
//@Autonomous(name = "Concept: NullOp", group = "Concept")
@Disabled

public class AutoRed0 extends OpMode {
    String vuforiaLicense = "AZsvxrv/////AAAAmb3WyR5Ma0lWgTCoh2ttuKxH5OJudy851mrB6LQM3l12YtWlMiqByWMN4NJ2jKqgp4epaAcrPkwCUQ86D7iBx5++icywdUxTsIR1J3EjQ5lhQd29NbZWtAAGrp+j1e188N3gqrwMOvD7IIwZoAnVx8EK5z6KEOo3lPne0Hj7Nwq2lqzzqxX+3/1eZcj5/VI9184h7lcNoR6SqSa+DvN4o4fvqO+QOsxBoCp7CfT5gBG343nLgIc6OejoEYHOedhdwmUEdjGObu8tOImMrl0NoO0J+2NkGDZI/iFv5ypmH/0RVDr9r1OhBSS/NUozgTsakKfrbDWQYIP6mDHRAiecYq3W2OtWg63YRRx5IhB9Ht/S";
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
    UltrasonicSensor frontDist;
    UltrasonicSensor leftDist;

    
    int team = 1; // 1 is red. -1 is blue.
    int position = 1; //1 is close. -1 is far
    int progress_dist = 3; //very imprecise distance of feet to travel forward to shelving
    
    boolean didDetection = false;
    int vuMark = 0; //-1 left. 0 center. 1 right.
    int ballColor = 1; // 1 is red-blue. -1 is blue-red.
    int progress = 1; //1 is primary orientation. -1 is secondary.
    int rotation = 1; //1 is primary rotation. -1 is secondary.
    int orientation = -rotation;

    int opCounter = 0; // current stage of autonomous
    int opTime = 1000; // in milliseconds

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

        frontDist = hardwareMap.get(UltrasonicSensor.class, "frontDist");
        leftDist  = hardwareMap.get(UltrasonicSensor.class, "leftDist");
    }
    @Override
    public void start(){
        tryDetection();
        runtime.reset();
        nowTime = runtime.milliseconds();
    }

    

    @Override
    public void loop(){
        
        if(runtime.milliseconds()<opTime){
            switch(opCounter){
                case 0:
                    opTime = 5000;
                    //Try DETECTION for Color & VuMark (if possible) give it 5 seconds
                    //->Success = bring down arm
                    if(didDetection){
                        lowerArm();
                        opTime = 0;
                    }
                    break;
                case 1:
                    opTime = 0;
                    //Set the constants
                    rotation = team * ballColor;
                    progress = ballColor * position;
                    orientation = rotation * -1;
                    break;
                case 2:
                    //ROTATION 90 degrees
                    if(didDetection)
                        raiseArm();
                    auto_drive(0,0,rotation);
                    break;
                case 3:
                    opTime = 500 * progress_dist; // 500 * number of feet
                    //PROGRESS 3 or 4 ft
                    auto_drive(progress,0,0);
                    break;
                case 4:
                    //REPOSITION
                    switch(position){
                        case 1:
                            auto_drive(0,0,orientation);
                            break;
                        case -1:
                            auto_drive(0,orientation,0);
                            break;
                    }
                    break;
                //---
                case 5:
                    //Shelf ALIGNMENT
                    if(false){
                        // TODO: if auto alignment is made, then use it
                        auto_alignment(vuMark);  
                        opTime = 0;                      
                    }
                    else
                        auto_drive(0,vuMark*0.7,0);
                    
                    break;
                case 6:
                    //Forward to Shelf
                    opTime = 5000;
                    if(frontDist.getUltrasonicLevel()<15 || leftDist.getUltrasonicLevel()<15){
                        opTime = 0;
                    }
                    break;
                case 7:
                    // TODO: test this
                    //Block PLACEMENT
                    opTime = 5000;
                    auto_lift(-1);
                    break;
                default:
                    opCounter = 10;
                    auto_drive(0,0,0);
            }
        } else {
            opTime = 1000;
            runtime.reset();
            opCounter++;
        }

    }
    public boolean tryDetection(){
        
        return false;
    }
    public void raiseArm(){}
    public void lowerArm(){}
    public void auto_alignment(int vuMark){}


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
        
        cardinal_y *= 1;
        cardinal_x *= 1;
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