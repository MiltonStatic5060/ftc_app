package org.firstinspires.ftc.teamcode.exampleops;

import android.hardware.Sensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//WELCOME TO EdTest1, THIS CODE CREATED BY MATT AND EDWARD WILL
//RUN YOU THROUGH THE BASICS FOR ANY ROBOT CODE
//
// copy and paste parts of this code into your programs to make your
// life easier. -courtesy of the robotics TEAM
//
//STEP 1: HAVE ALL OF THIS!!! These are import statements that allow
//        you to use all the methods you'll need when coding. Methods
//        are your tools in coding.
//--------------------------------------------------------------------------------------------------

//Everything in this space happens when you select EdTest1 on driver station phone
@TeleOp(name = "EdTest1", group = "Competition2017-18") //<---this just puts in in a folder
//@Autonomous(name = "Concept: NullOp", group = "Concept")
//@Disabled

public class EdTest1 extends OpMode { //<--- the grey name had to match the name above (ex: EdTest1)

    // Variables --> variables are to be instantiated here. This includes all motors, sensors
    //               and servos used by your robot in you code.
    DcMotor edwardMotor = null;
    TouchSensor touch = null;
    LightSensor light = null;
    UltrasonicSensor ultrasonic = null;
    //ColorSensor colorsensor = null; <-- HINT: comment out things you aren't using.
    IrSeekerSensor infrared = null;
    Servo edwardServo;
    Servo servo180;

    @Override //--> anything under this is in a loop that begins when you hit init on the driver station phone
    public void init(){
        // Initialization Code NO MOVEMENT SHOULD HAPPEN HERE!!!!

        telemetry.addData("Ed Help Message: ","turn on the robot");

        edwardMotor = hardwareMap.get(DcMotor.class, "motor1"); //<--- names here should match names set on the Robot Controller phone
        edwardServo = hardwareMap.get(Servo.class, "Servo");
        servo180 = hardwareMap.get(Servo.class, "servo180");
        touch = hardwareMap.get(TouchSensor.class, "touch");
        light = hardwareMap.get(LightSensor.class, "light");
        ultrasonic = hardwareMap.get(UltrasonicSensor.class, "ultrasonic");
        //colorsensor = hardwareMap.get(ColorSensor.class, "colorsensor");
        infrared = hardwareMap.get(IrSeekerSensor.class, "infrared");

        //servo180.setDirection(Servo.Direction.FORWARD);
        edwardServo.setDirection( Servo.Direction.FORWARD);
        edwardMotor.setDirection( DcMotor.Direction.FORWARD);
    }

    @Override //Code that runs ONCE when the driver hits play
    public void start(){
        // Starting Code when starting opmode on app
    }

    @Override //Code that runs repeatedly when the driver hits play
    public void loop(){
        //loop_motor(); // <-- not too sure how this works... :[
        

        //edwardMotor.setPower( gamepad1.left_bumper ? -1 : 0 );
        /** Extended code definition (see above)
            double var;
            if(gamepad1.left_bumper)
                var = -1;
            else
                var = 0;
            edwardMotor.setPower(var);
        */
        //TOUCH SENSOR CODE
        if(touch.isPressed()) //this section makes the motor turn repeatedly as ong as the touch sensor is pressed
        {
            edwardMotor.setPower(1.0);
            light.enableLed(true);  //turns on the LED light
        } else {
            light.enableLed(false); //turns off the LED light
        }

        //LIGHT SENSOR CODE ---> RED VS BLUE
        // This section of code detects light levels in front of the light sensor
        // and sets the servo to move clockwise if a RED object is shown and makes the servo turn
        //counter clockwise if a BLUE object is shown
        light.enableLed(true);
        if(light.getLightDetected() < .42 && light.getLightDetected() > .38){ //for lighter values. WHITE is 1.0
            edwardServo.setPosition(1.0);
        } else if(light.getLightDetected() <= .38){ //0.0 is BLACk
            edwardServo.setPosition(0.0);
        } else {
            edwardServo.setPosition(0.5);
        }

        //ULTRASONIC SENSOR CODE
        //The Ultrasonic sensor measures distance in inches and displays it to the diver station
        telemetry.addData("Distance: ", Math.round((ultrasonic.getUltrasonicLevel() / 2.54) - 1)); //gives value in inches rounded

        //Color Sensor code (SENSOR IS WIERD...DOESN'T WORK, CODE DOES)
        //colorsensor.enableLed(true);
        //telemetry.addData("Blue: ", colorsensor.blue());
        //telemetry.addData("Red: ", colorsensor.red());
        //telemetry.addData("Green: ", colorsensor.green());

        //INFRARED SENSOR CODE
        //returns true on the driver station if an infrared signal is detected by the sensor
        telemetry.addData("Infrared Signal: ", infrared.signalDetected());

        //motor code with gamepad1
        //spins the motor clockwise if the left stick is pushed up and counter-clockwise if
        //the left stick is pushed down
        if(gamepad1.left_stick_y > 0.0) {
            edwardMotor.setPower(1.0);
        } else if (gamepad1.left_stick_y < 0.0) {
            edwardMotor.setPower(-1.0);
        } else {
            edwardMotor.setPower(0.0);
        }


        /** Everything on Gamepad
            a
            b
            x
            y
            left_bumper
            right_bumper
            left_trigger
            right_trigger
            left_stick_button
            right_stick_button
            dpad_up
            dpad_right
            dpad_down
            dpad_left
            start
            guide
            back
            Control Code
            gamepad1.left_stick_y;
            gamepad1.left_stick_x;
            gamepad1.right_stick_x;
            gamepad1.right_stick_y;

        */

        //Makes the servo turn when the left or right bumpers are pressed
        if(gamepad1.left_bumper) {
            edwardServo.setPosition(1.0);
        } else {
            edwardServo.setPosition(0.5); // 0.5 IS OFF!!! 1.0 is clockwise, 0.0 is counterclockwise
        }
        if(gamepad1.right_bumper) {
            edwardMotor.setPower(1.0);
            //edwardServo.setPosition(1.0);
        }
        //SERVO CODE
        //changes the position of the servo based on if the y or x button is pressed
        if(gamepad1.y) {
            servo180.setPosition(Servo.MAX_POSITION);
        }
        if (gamepad1.x){
            servo180.setPosition(Servo.MIN_POSITION);
        }
//        if(gamepad1.dpad_down) {
//            servo180.setPosition(Servo.MIN_POSITION);
//        } else {
//            servo180.setPosition(0.5);
//        }
        //edwardServo.setPosition( gamepad1.right_trigger ); // 0 to 1
        // edwardServo.setPosition( gamepad1.y ? 1 : 0 ); // 0 to 1
    }

    public void loop_motor(){
        double edwardPower = gamepad1.left_stick_y + gamepad2.left_stick_y;
        double throttle = Range.clip( edwardPower,  -1, 1 );
        edwardPower = Range.clip( edwardPower,  -1, 1 );
        edwardMotor.setPower(throttle);  // -1.0(counterclockwise) to 1.0(clockwise)
    }

    public void loop_stuff(){
        telemetry.addData("Edward's Motor's Power",  edwardMotor.getPower());
    }
}