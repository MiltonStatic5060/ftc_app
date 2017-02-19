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
 * Created by ryan on 2/17/17. auryan898@gmail.com 6176159076
 */

//@TeleOp(name = "Template", group = "Concept")
@Autonomous(name = "Sensor Test", group = "Concept")
@Disabled
public class RNxtMotorTest extends LinearOpMode {
    //DECLARATION
    
    double powLeft;
    double powRight;

    boolean booLeft;
    boolean booRight;

    DcMotor motorLeft;
    DcMotor motorRight;

    @Override public void runOpMode() {

        //INITIALIZATION
        motorLeft = hardwareMap.dcMotor.get("left");
        motorRight = hardwareMap.dcMotor.get("right");
        
        /**
         * Wait until we've been given the ok to go. For something to do, we emit the
         * elapsed time as we sit here and wait. If we didn't want to do anything while
         * we waited, we would just call {@link #waitForStart()}.
         */

        while (!isStarted()) {
            //Give variables values and set modes and directions of devices
            
            telemetry.addData("Hello", " World");
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
            
            Range.clip(gamepad1.left_stick_y+gamepad2.left_stick_y,-1,1);
            Range.clip(gamepad1.right_stick_y+gamepad2.right_stick_y,-1,1);



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
    
    private void r_motorCtl(){
    }
}
