// Copy paste this code into a new java class file

package org.firstinspires.ftc.teamcode.competition2018;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.*;

public class templateOp extends OpMode {
    DcMotor wheel;
    Servo finger;
    DcMotorController motcont1;
    ServoController servcont1;

    @Override
    public void init(){
        wheel  = hardwareMap.get(DcMotor.class, "motor1");
        wheel.setPower(0);
        motcont1 = wheel.getController();
    }

    @Override
    public void init_loop(){}
    
    @Override
    public void start(){}

    @Override
    public void loop(){
        if(gamepad1.a || gamepad2.left_stick_y<0)
            wheel.setPower(1); //clockwise full power
        else {
            wheel.setPower(0); 
            //the motor has to be stopped. if this were not here, the motor would keep running forever
        }
    }

    @Override
    public void stop(){

    }
}
