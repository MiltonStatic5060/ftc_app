
package org.firstinspires.ftc.teamcode.competition2017;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.*;



@Autonomous(name="VuMark Interfacing", group ="Concept")
public class VuMarkIterate0 extends OpMode {
    public VuMarkInterface vumarkInterface;
    @Override
    public void init(){
        vumarkInterface = new VuMarkInterface(hardwareMap,telemetry);
        vumarkInterface.init();
    }
    @Override
    public void start(){
        vumarkInterface.start();
    }
    @Override
    public void loop(){
        vumarkInterface.loop();
    }   
}