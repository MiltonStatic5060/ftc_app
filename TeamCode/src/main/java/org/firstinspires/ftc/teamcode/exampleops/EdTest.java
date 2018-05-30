package org.firstinspires.ftc.teamcode.exampleops;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.*;



@TeleOp(name = "EdTest", group = "Competition2017-18")
//@Autonomous(name = "Concept: NullOp", group = "Concept")
// @Disabled
public class EdTest extends OpMode {
    // Variables
    DcMotor edwardMotor = null;
    
    Servo edwardServo = null;




    @Override
    public void init(){
        // Initialization Code NO MOVEMENT!!!!
        edwardMotor = hardwareMap.get(DcMotor.class, "motor1");
        edwardServo = hardwareMap.get(Servo.class, "Servo");
    }

    @Override
    public void start(){
        // Starting Code when starting opmode on app
    }

    @Override
    public void loop(){
        loop_motor();
        

        edwardMotor.setPower( gamepad1.left_bumper ? -1 : 0 );
        /** Extended code definition (see above)
            double var;
            if(gamepad1.left_bumper)
                var = -1;
            else
                var = 0;
            edwardMotor.setPower(var);
        */

 
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
            dpad_...
            start
            guide
            back
            Control Code
            gamepad1.left_stick_y;
            gamepad1.left_stick_x;
            gamepad1.right_stick_x;
            gamepad1.right_stick_y;

        */

        edwardServo.setPosition( gamepad1.right_trigger ); // 0 to 1
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
        telemetry.addData("Help Message","Plug in the robot");
    }
}