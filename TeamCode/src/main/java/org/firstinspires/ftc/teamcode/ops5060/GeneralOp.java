package org.firstinspires.ftc.teamcode.ops5060;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
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
 * Created by ryan on 2/12/17. auryan898@gmail.com 6176159076
 */

//@TeleOp(name = "Template", group = "Concept")
@Autonomous(name = "Template", group = "Concept")
@Disabled
public class GeneralOp extends LinearOpMode {
    //DECLARATION
    //DcMotor motorLeft;
    //Servo servoWrist;
    //double powConv;
    @Override public void runOpMode() {

        //INITIALIZATION
        //motorLeft = hardwareMap.dcMotor.get("left");
        //servoWrist = hardwareMap.servo.get("wrist");


        /**
         * Wait until we've been given the ok to go. For something to do, we emit the
         * elapsed time as we sit here and wait. If we didn't want to do anything while
         * we waited, we would just call {@link #waitForStart()}.
         */
        while (!isStarted()) {
            telemetry.addData("hello", "hello");
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

            // Emit poetry if it's been a while
            /*   if (poemElapsed.seconds() > sPoemInterval) {
                emitPoemLine();
            }*/

            // As an illustration, show some loop timing information
            telemetry.addData("loop count", loopCount);
            //telemetry.addData("ms/loop", "%.3f ms", opmodeRunTime.milliseconds() / loopCount);

            // Show joystick information as some other illustrative data
            telemetry.addLine("left joystick | ")
                    .addData("x", gamepad1.left_stick_x)
                    .addData("y", gamepad1.left_stick_y);
            telemetry.addLine("right joystick | ")
                    .addData("x", gamepad1.right_stick_x)
                    .addData("y", gamepad1.right_stick_y);

            /**
             * Transmit the telemetry to the driver station, subject to throttling.
             * @see Telemetry#getMsTransmissionInterval()
             */
            telemetry.update();

            /** Update loop info and play nice with the rest of the {@link Thread}s in the system */
            loopCount++;
        }
    }
}
