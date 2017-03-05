package org.firstinspires.ftc.teamcode.ops5060;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by ryan on 2/12/17. auryan898@gmail.com 6176159076
 */

//@TeleOp(name = "Template", group = "Concept")
@Autonomous(name = "Robot Control 1", group = "TeleOp")
@Disabled
public class RNxt021817A_3 extends LinearOpMode {
    //DECLARATION

    //r_drivePower
    DcMotor motorLeft;
    DcMotor motorRight;
    DcMotor motorLeft1;
    DcMotor motorRight1;

    //r_catapult
    DcMotor motorCat;

    //r_ballCollection
    DcMotor motorConv;
    DcMotor motorSweep;

    //r_ballGate
    Servo servoGate1;
    Servo servoGate2;

    //r_sense
    ColorSensor color1;
    UltrasonicSensor ultra1;
    UltrasonicSensor ultra2;

    //r_autonomousColor
    LightSensor light1;
    LightSensor light2;
    UltrasonicSensor ultra2Arm;
    UltrasonicSensor ultra2But;

    int autoStep = 0;

    double pi = Math.PI;

    private ElapsedTime runtime = new ElapsedTime();

    @Override public void runOpMode() {

        //INITIALIZATION

        //r_drivePower
        motorLeft = hardwareMap.dcMotor.get("leftB");
        motorRight = hardwareMap.dcMotor.get("rightB");
        motorLeft1 = hardwareMap.dcMotor.get("leftF");
        motorRight1 = hardwareMap.dcMotor.get("rightF");
        
        //r_catapult
        motorCat = hardwareMap.dcMotor.get("catapult");

        //r_ballCollection
        motorConv = hardwareMap.dcMotor.get("conveyor");
        motorSweep = hardwareMap.dcMotor.get("sweeper");

        //r_ballGate
        servoGate2 = hardwareMap.servo.get("gate2");
        servoGate1 = hardwareMap.servo.get("gate1");

        //r_sense
        //color1 = hardwareMap.colorSensor.get("color1");
        ultra1 = hardwareMap.ultrasonicSensor.get("ultra1");
        //ultra2 = hardwareMap.ultrasonicSensor.get("ultra2");

        //r_autonomousColor
        light1 = hardwareMap.lightSensor.get("light1");
        light2 = hardwareMap.lightSensor.get("light2");
        ultra2But = hardwareMap.ultrasonicSensor.get("ultra2but");
        ultra2Arm = hardwareMap.ultrasonicSensor.get("ultra2arm");


        /**
         * Wait until we've been given the ok to go. For something to do, we emit the
         * elapsed time as we sit here and wait. If we didn't want to do anything while
         * we waited, we would just call {@link #waitForStart()}.
         */
        while (!isStarted()) {
            //r_drivePower
            motorRight.setDirection(DcMotor.Direction.REVERSE);
            motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorRight1.setDirection(DcMotor.Direction.REVERSE);
            motorLeft1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorRight1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            
            //r_catapult
            motorCat.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            telemetry.addData("hello", "world");
            telemetry.update();
            idle();

        }

        // Ok, we've been given the ok to go

        /**
         * As an illustration, the first line on our telemetry display will display the battery voltage.
         * The idea here is that it's expensive to compute the voltage (at least for purposes of illustration)
         * so you don't want to do it unless the data is <em>actually</em> going to make it to the
         * driver station (recall that telemetry transmission is throttled to reduce bandwidth use.
         * Note that getBatteryVoltage() below returns 'Infinity' if there's no voltage sensor attached.
         *
         * @see Telemetry#getMsTransmissionInterval()
         */
        /**  telemetry.addData("voltage", "%.1f volts", new Func<Double>() {
            @Override public Double value() {
                return getBatteryVoltage();
            }
        });  */

        // Reset to keep some timing stats for the post-'start' part of the opmode
        
        int loopCount = 1;

        // Go go gadget robot!
        while (opModeIsActive()) {
            //r_autonomous();
            if(gamepad1.right_bumper&&gamepad1.back){
                r_autonomousColor();
            }

            if(gamepad1.right_bumper&&gamepad1.start){
                
            }

            if(gamepad1.right_bumper&&gamepad1.a){
                runtime.reset();
                while(runtime.seconds()<1){
                    a_driveL(180,.5);
                }
            }
            if(gamepad1.right_bumper&&gamepad1.b){
                runtime.reset();
                while(runtime.seconds()<1){
                    a_driveL(90,.5);
                }
            }
            if(gamepad1.right_bumper&&gamepad1.y){
                runtime.reset();
                while(runtime.seconds()<1){
                    a_driveL(0,.5);
                }
            }
            if(gamepad1.right_bumper&&gamepad1.x){
                runtime.reset();
                while(runtime.seconds()<1){
                    a_driveL(-90,.5);
                }
            }
            a_driveL(0,0);



            /* TeleOp Methods
                //r_drivePower();
                //r_catapult();
                //r_ballGate();
                //r_ballCollection();
                //r_buttonPusher();
                //r_sense();
            */

            // As an illustration, show some loop timing information
            telemetry.addData("loop count", loopCount);
            //telemetry.addData("ms/loop", "%.3f ms", opmodeRunTime.milliseconds() / loopCount);
            
            
            /**
             * Transmit the telemetry to the driver station, subject to throttling.
             * @see Telemetry#getMsTransmissionInterval()
             */
            telemetry.update();

            /** Update loop info and play nice with the rest of the {@link Thread}s in the system */
            loopCount++;
        }
    }

    private void r_autonomousColor(){
        //TODO:
        /**
        * Get Red/Blue Filters; Install Ultrasonic Sensors on arm and body; Install push plate;
        * Design Auto Driving Scheme; 
        *
        */
        boolean run = true;
        int r = 0;
        while(run){
            
            switch(r){
                case 0:
                //Algorithm to detect blue/red; light1 = red(side) ; light2 = blue(top)
                    //initiate next step when red detected light > .18
                    //if(light2>0.15){//blue
                    if(light1.getLightDetected()>0.18){//red
                        //red detected so break out of while loop and go on to button detecting action
                        r++;
                        servoGate1.setPosition(0);
                        break;
                    }
                //Drive backwards and forwards across light beacon until color detected
                    //while(){}
                    break;
                case 1:
                //Algorithm to detect black button and then end outer while loop to push button
                if(light1.getLightDetected()<.05){ //bottom light detector
                    run=false;
                    servoGate1.setPosition(0.75);
                }
                //Drive backwards and forwards across light beacon until black detected
                    //while(){} 
            }
                

        //Algorithm to keep button pushing arm around object within its reach
            //Shorten arm if arm to button distance is too short
            if(ultra2But.getUltrasonicLevel()==255||ultra2But.getUltrasonicLevel()<=10){
                servoGate2.setPosition(0);
            }
            //Stop arm if arm  to body distance is too short
            if(ultra2Arm.getUltrasonicLevel()==255||ultra2Arm.getUltrasonicLevel()<=7){
                servoGate2.setPosition(0.5);
            }
            //Extend arm if arm to button distance is too long
            if(ultra2But.getUltrasonicLevel()>13){
                servoGate2.setPosition(1);
            }
            //Stop arm if arm  to body distance is too long
            if(ultra2Arm.getUltrasonicLevel()>=21){
                servoGate2.setPosition(0.5);
            }
            telemetry.addData("Distance from arm to outside", ultra2But.getUltrasonicLevel());
            telemetry.addData("Distance from arm to body   ", ultra2Arm.getUltrasonicLevel());
            
            //Give ability to drive until driving scheme can be designed
            r_drivePower();
        }
        //two arm push the button
            for(int i=0; i<2; i++){
                while(!(ultra2But.getUltrasonicLevel()==255||ultra2But.getUltrasonicLevel()<5)){
                    servoGate2.setPosition(1);
                    servoGate1.setPosition(1);
                    //Stop arm if arm  to body distance is too long
                    while(ultra2Arm.getUltrasonicLevel()>15){
                        servoGate2.setPosition(0);
                        //drive towards the direction left/right 
                        double x = -0.1; //left
                        //double x = 0.1;  //right
                        a_driveR(90,x);
                        servoGate1.setPosition(0.5);
                    }
                    a_driveR(0,0);
                    telemetry.addData("Ext",ultra2But.getUltrasonicLevel());
                    telemetry.addData("Arm",ultra2Arm.getUltrasonicLevel());
                }
                while((ultra2Arm.getUltrasonicLevel()>=10)){
                    servoGate2.setPosition(0);
                }
                
            }
    }

    private void r_autonomous(){
        //Move into position
            double dist1 = ultra1.getUltrasonicLevel();

            runtime.reset();
            while(ultra1.getUltrasonicLevel()<dist1+50){
                a_driveL(0,.5);
            }
            while(ultra1.getUltrasonicLevel()>dist1+80){
                a_driveL(0,-.2);
            }
        //Shoot Balls
            //shoot first ball
            runtime.reset();
            while(runtime.seconds()<3){
                a_driveL(0,0);
                motorCat.setPower(-1);
            }
            //load second ball
            runtime.reset();
            while(runtime.seconds()<2){
                a_driveL(0,0);
                servoGate1.setPosition(0);
                motorCat.setPower(-0.3);
            }
            //shoot ball
            runtime.reset();
            while(runtime.seconds()<3){
                a_driveL(0,0);
                servoGate1.setPosition(0);
                motorCat.setPower(-1);
            }
            runtime.reset();
            while(runtime.seconds()<1){
                motorCat.setPower(0.1);
            }
        //Push Cap Ball
            //locate big ball and move there
                runtime.reset();
                //while(runtime.seconds()<2){}
            //push big ball
                runtime.reset();
                //while(runtime.seconds()<2){}
        //Move onto Corner Vortex
            //rotate first into position
                runtime.reset();
                //while(runtime.seconds()<2){}
            //move onto ramp
                runtime.reset();
                //while(runtime.seconds()<2){}
    }

    //0,1 is forward, -90,1 is left +90,1 is right, 180,1 is backwards
    private void a_driveL(double deg, double pow){

        double rad = Math.toRadians(deg+270);

        double left_x = pow*Math.cos(rad);
        double left_y = pow*Math.sin(rad);
        double right_x  = 0;
        double right_y = 0;

        double powLeft1 =  Range.clip(left_y+right_y - left_x - right_x,-1,1);
        double powRight1 = Range.clip(left_y+right_y + left_x + right_x,-1,1);
        double powLeft =   Range.clip(left_y+right_y - left_x + right_x,-1,1);
        double powRight =  Range.clip(left_y+right_y + left_x - right_x,-1,1);

        powRight1*=0.8;
        powRight*=0.9;


        telemetry.addData("Front Left", powLeft1);
        telemetry.addData("Front Right", powRight1);
        telemetry.addData("Back Left", powLeft);
        telemetry.addData("Back Right", powRight);

        motorRight.setPower(powRight);
        motorLeft.setPower(powLeft);
        motorRight1.setPower(powRight1);
        motorLeft1.setPower(powLeft1);
    }
    private void a_driveR(double deg, double pow){

        double rad = Math.toRadians(deg+270);

        double left_x = 0;
        double left_y = 0;
        double right_x  = pow*Math.cos(rad);
        double right_y = pow*Math.sin(rad);

        double powLeft1 =  Range.clip(left_y+right_y - left_x - right_x,-1,1);
        double powRight1 = Range.clip(left_y+right_y + left_x + right_x,-1,1);
        double powLeft =   Range.clip(left_y+right_y - left_x + right_x,-1,1);
        double powRight =  Range.clip(left_y+right_y + left_x - right_x,-1,1);

        powRight1*=0.8;
        powRight*=0.9;


        telemetry.addData("Front Left", powLeft1);
        telemetry.addData("Front Right", powRight1);
        telemetry.addData("Back Left", powLeft);
        telemetry.addData("Back Right", powRight);

        motorRight.setPower(powRight);
        motorLeft.setPower(powLeft);
        motorRight1.setPower(powRight1);
        motorLeft1.setPower(powLeft1);
    }

    private void r_drivePower(){
        double left_x = Range.clip(gamepad1.left_stick_x+gamepad2.left_stick_x,-1,1);
        double left_y = Range.clip(gamepad1.left_stick_y+gamepad2.left_stick_y,-1,1);
        double right_x  = Range.clip(gamepad1.right_stick_x+gamepad2.right_stick_x,-1,1);
        double right_y = Range.clip(gamepad1.right_stick_y+gamepad2.right_stick_y,-1,1);

        double powLeft1 =  Range.clip(left_y+right_y - left_x - right_x,-1,1);
        double powRight1 = Range.clip(left_y+right_y + left_x + right_x,-1,1);
        double powLeft =   Range.clip(left_y+right_y - left_x + right_x,-1,1);
        double powRight =  Range.clip(left_y+right_y + left_x - right_x,-1,1);

        powRight1*=0.8;
        powRight*=0.9;


        telemetry.addData("Front Left", powLeft1);
        telemetry.addData("Front Right", powRight1);
        telemetry.addData("Back Left", powLeft);
        telemetry.addData("Back Right", powRight);

        motorRight.setPower(powRight);
        motorLeft.setPower(powLeft);
        motorRight1.setPower(powRight1);
        motorLeft1.setPower(powLeft1);

    }

    private void r_catapult(){
        double powCat = -(gamepad1.left_trigger+gamepad2.left_trigger);

        if(gamepad1.left_bumper||gamepad2.left_bumper){
            powCat+=0.5;
        }

        motorCat.setPower(Range.clip(powCat,-1,1));
        telemetry.addData("Catapult",powCat);
    }

    private void r_ballGate(){
        double pos1 = 0.5;
        double pos2 = 0.5;

        if(gamepad1.dpad_up||gamepad2.dpad_up){
            pos1 = 0;
            
        }
        if(gamepad1.dpad_right||gamepad2.dpad_right){
            pos2 -= 0.5;
        }
        if(gamepad1.dpad_left||gamepad2.dpad_left){
            pos2 += 0.5;
        }

        servoGate1.setPosition(Range.clip(pos1,0,1));
        telemetry.addData("Gate 1",Range.clip(pos1,0,1));
        servoGate2.setPosition(Range.clip(pos2,0,1));
        telemetry.addData("Button Pusher", Range.clip(pos2,0,1));
    }

    private void r_ballCollection(){
        double powSweep = 0;
        double powConv = 0;

        if(gamepad1.a||gamepad2.a){
            powSweep -= 0.8;
        }
        if(gamepad1.y||gamepad2.y){
            powSweep += 0.8;
        }
        if(gamepad1.x||gamepad2.x){
            powConv -= 0.8;
        }
        if(gamepad1.b||gamepad2.b){
            powConv += 0.8;
        }

        telemetry.addData("Sweeper Power",Range.clip(powSweep,-1,1));
        telemetry.addData("Conveyor Power", Range.clip(powConv,-1,1));
        motorSweep.setPower(Range.clip(powSweep,-1,1));
        motorConv.setPower(Range.clip(powConv,-1,1));
    }

    private void r_buttonPusher(){
        double powButt = 0;

        if(gamepad1.back||gamepad2.back){
            powButt = 0.4;
        }
        if(gamepad1.guide||gamepad2.guide){
            powButt = -0.4;
        }

        double posButt = 0.5;

        if(gamepad1.back||gamepad2.back){
            posButt = 0.2;
        }
        if(gamepad1.guide||gamepad2.guide){
            posButt = 0.8;
        }

        telemetry.addData("Button Push Power",Range.clip(powButt,-1,1));
        telemetry.addData("Button Push Position",Range.clip(posButt,-1,1));
        //motor.setPower(Range.clip(powButt,-1,1));
        //servo.setPosition(Range.clip(posButt,0,1));
    }

    private void r_sense(){
        //telemetry.addData("Red",color1.red());
        //telemetry.addData("Green",color1.green());
        //telemetry.addData("Blue",color1.blue());
        telemetry.addData("Ultrasonic 1",ultra1.getUltrasonicLevel());
        telemetry.addData("Ultrasonic 2",ultra2.getUltrasonicLevel());
        //telemetry.addData("Left Distance",ultra);
        //telemetry.addData("Right Distance",ultra);
    }
}
