package org.firstinspires.ftc.teamcode.competition2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.*;



@TeleOp(name = "ServoTester", group = "Competition2017-18")
//@Autonomous(name = "Concept: NullOp", group = "Concept")
//@Disabled
public class ServoTester extends OpMode {
    Servo servo1;
    Servo servo2;
    Servo servo3;
    Servo servo4;
    Servo servo5;
    Servo servo6;

    double pos1;
    double delta1;
    double delta5;

    double pos2;
    double pos3;
    double pos4;
    double pos5;
    double pos6;
    boolean alternateOn = false;

    @Override
    public void init(){
        servo1 = hardwareMap.get( Servo.class, "servo1");
        servo2 = hardwareMap.get( Servo.class, "servo2");
        servo3 = hardwareMap.get( Servo.class, "servo3");
        servo4 = hardwareMap.get( Servo.class, "servo4");
        servo5 = hardwareMap.get( Servo.class, "servo5");
        servo6 = hardwareMap.get( Servo.class, "servo6");
    }

    @Override
    public void start(){
        pos1 = 0;
        delta1 = 0.01;
        pos2 = 0.2;
        pos3 = 0.4;
        pos4 = 0.6;
        pos5 = 0.8;
        delta5 = 0.01;
        pos6 = 1.0;
    }

    @Override
    public void loop(){
        if(pos1>=.65 || pos1<=0){
            delta1 *= -1;
            pos1 += delta1;
        }
        if(gamepad1.y||alternateOn){
            pos1 += delta1;
            pos6 = 1-pos1;
        }
        if(pos5>=1 || pos2<=0){
            delta5 *= -1;
            pos5 += delta5;
        }
        if(gamepad1.x||alternateOn){
            pos5 += delta5;
            pos2 = 1-pos5;
        }
        pos1 = Range.clip(pos1,0,1);
        pos2 = Range.clip(pos2,0,1);
        pos5 = Range.clip(pos5,0,1);
        pos6 = Range.clip(pos6,0,1);
        telemetry.addData("pos1",pos1);
        telemetry.addData("pos6",pos6+"\n");
        telemetry.addData("pos2",pos2);
        telemetry.addData("pos5",pos5);
        telemetry.addData("Press Y to make servos 1 and 6 alternate",gamepad1.y);
        telemetry.addData("Press X to make servos 2 and 5 alternate",gamepad1.x);
        servo1.setPosition(pos1);
        servo2.setPosition(pos2);
        servo3.setPosition(pos3);
        servo4.setPosition(pos4);
        servo5.setPosition(pos5);
        servo6.setPosition(pos6);
    }
}