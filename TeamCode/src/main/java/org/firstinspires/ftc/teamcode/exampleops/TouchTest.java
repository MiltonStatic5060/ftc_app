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
public class TouchTest extends OpMode {
    // Variables
    DcMotor edwardMotor = null;
    
    Touch edwardSensor = null;




    @Override
    public void init(){
        // Initialization Code NO MOVEMENT!!!!
        edwardMotor = hardwareMap.get(DcMotor.class, "motor1");
        edwardSensor = hardwareMap.get(Servo.class, "touch");
    }

    @Override
    public void start(){
        // Starting Code when starting opmode on app

    }

    @Override
    public void loop(){
        telemetry.addData("Touch Sensor",edwardSensor.isPressed());
        if(edwardSensor.isPressed()){
            edwardMotor.setPower(1);
        } else {
            edwardMotor.setPower(0);
        }
    }
}