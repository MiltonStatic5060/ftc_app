package org.firstinspires.ftc.teamcode.competition2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.*;



@Autonomous(name = "Main AutoOp (6)", group = "Competition2017-18")
//@Autonomous(name = "Concept: NullOp", group = "Concept")
// @Disabled
public class AutoOp_6 extends OpMode {
    HardwareOp_6 robot6 = new HardwareOp_6(hardwareMap);

    @Override
    public void init(){
        robot6.init();
    }

    @Override
    public void start(){
        // robot6.start();
    }

    @Override
    public void loop(){
        // robot6.loop();
    }

    /**
     * description: autonomous version of lift control
     * @param power accepts values -1.0 to 1.0 for up/down lifting. 0 is resting.
     */
    public void auto_lift(double power){
        robot6.auto_lift(power);
    }

    /**
     * 
     * description: autonomous version of driving function
     * @param a -1.0 to 1.0 forward/backward
     * @param b -1.0 to 1.0 leftward/rightward
     * @param c   -1.0 to 1.0 counter/clock wise
     * 
     */
    public void auto_drive(double a,double b,double c){
        robot6.auto_drive( a, b, c);
    }
    
}