// Hey Ryan, We're learning code
//USEFUL LIKS:
//http://ftctechnh.github.io/ftc_app/doc/javadoc/index.html
//https://github.com/ftctechnh/ftc_app
//https://github.com/MiltonStatic5060/
//https://miltonstatic5060.github.io/main/index.html

package org.firstinspires.ftc.teamcode.apcsatest;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.*;

@TeleOp(name = "Test1", group = "apcsatest")
// basically it's grouped with the stuff for next
public class Test1 extends OpMode {
    DcMotor wheel;
    Servo servo;
    DcMotorController motcont1;
    ServoController servcont1;

    @Override
    public void init(){
        wheel  = hardwareMap.get(DcMotor.class, "motor1");
        wheel.setPower(0);
        motcont1 = wheel.getController(); // motcont1 is not necessary but good job declaring it anyways
        
        // Don't forget the TouchSensor named "touch" on the phone
    }

    @Override
    public void init_loop(){}
    
    @Override
    public void start(){}

    @Override
    public void loop(){
        //if(gamepad1.a || gamepad2.left_stick_y<0)
          //  wheel.setPower(1); //clockwise full power
        //else {
          //  wheel.setPower(0); 
            //the motor has to be stopped. if this were not here, the motor would keep running forever
        //}
        if(gamepad1.b)
        {
            wheel.setPower(-1);
        }
        else if(gamepad1.a)
        {
            wheel.setPower(1);
        }
        else
        {
            wheel.setPower(0);
        }
    }

    @Override
    public void stop(){

    }
}
