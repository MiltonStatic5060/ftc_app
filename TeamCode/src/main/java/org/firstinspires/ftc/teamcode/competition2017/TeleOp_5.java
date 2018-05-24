package org.firstinspires.ftc.teamcode.competition2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.*;



@TeleOp(name = "Main TeleOp (4)", group = "Competition2017-18")
//@Autonomous(name = "Concept: NullOp", group = "Concept")
@Disabled
public class TeleOp_5 extends OpMode {
    /* Variables */
    DcMotor leftDrive;
    DcMotor rightDrive;

    double powerLeft;
    @Override
    public void init(){
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
    }
    @Override
    public void start(){
        powerLeft = 1;
        
    }
    @Override 
    public void loop(){
        // if(gamepad1.x)
        //     powerLeft = -1;
        // if(gamepad1.y)
        //     powerLeft = 1;
        // if(gamepad1.a)
        //     powerLeft = 0;

        // powerLeft = ;

        leftDrive.setPower(gamepad1.left_stick_y);
        rightDrive.setPower(gamepad1.right_stick_y);

    }
}