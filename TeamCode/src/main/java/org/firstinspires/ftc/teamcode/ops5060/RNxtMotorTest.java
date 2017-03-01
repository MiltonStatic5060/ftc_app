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
 * Created by ryan on 2/12/17. auryan898@gmail.com 6176159076
 */

//@TeleOp(name = "Template", group = "Concept")
@Autonomous(name = "Template", group = "Concept")
@Disabled
public class RNxtMotorTest extends LinearOpMode {
    //DECLARATION

    //r_drivePower
    DcMotor motorLeft;
    DcMotor motorRight;
    DcMotor motorLeft1;
    DcMotor motorRight1;

    //r_catapult
    DcMotor motorCat;

    //r_ballCollection
    DcMotor motorConv;
    DcMotor motorSweep;

    //r_ballGate
    Servo servoGate1;
    Servo servoGate2;

    //r_sense
    ColorSensor color1;
    UltrasonicSensor ultra1;
    UltrasonicSensor ultra2;

    @Override public void runOpMode() {

        //INITIALIZATION

        //r_drivePower
        motorLeft = hardwareMap.dcMotor.get("leftB");
        motorRight = hardwareMap.dcMotor.get("rightB");
        motorLeft1 = hardwareMap.dcMotor.get("leftF");
        motorRight1 = hardwareMap.dcMotor.get("rightF");
        
        //r_catapult
        //motorCat = hardwareMap.dcMotor.get("catapult");

        //r_ballCollection
        //motorConv = hardwareMap.dcMotor.get("conveyor");
        //motorSweep = hardwareMap.dcMotor.get("sweeper");

        //r_ballGate
        //servoGate2 = hardwareMap.servo.get("gate2");
        //servoGate1 = hardwareMap.servo.get("gate1");

        //r_sense
        //color1 = hardwareMap.colorSensor.get("color1");
        //ultra1 = hardwareMap.ultrasonicSensor.get("ultra1");
        //ultra2 = hardwareMap.ultrasonicSensor.get("ultra2");

        /**
         * Wait until we've been given the ok to go. For something to do, we emit the
         * elapsed time as we sit here and wait. If we didn't want to do anything while
         * we waited, we would just call {@link #waitForStart()}.
         */
        while (!isStarted()) {
            //r_drivePower
            motorRight.setDirection(DcMotor.Direction.REVERSE);
            motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorRight1.setDirection(DcMotor.Direction.REVERSE);
            motorLeft1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorRight1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            
            //r_catapult
            //motorCat.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            telemetry.addData("hello", "world");
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
            r_drivePower();
            //r_catapult();
            //r_ballGate();
            //r_ballCollection();
            //r_buttonPusher();
            //r_sense();

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
    private void r_drivePower(){
        double left_x = Range.clip(gamepad1.left_stick_x+gamepad2.left_stick_x,-1,1);
        double left_y = Range.clip(gamepad1.left_stick_y+gamepad2.left_stick_y,-1,1);
        double right_x  = Range.clip(gamepad1.right_stick_x+gamepad2.right_stick_x,-1,1);
        double right_y = Range.clip(gamepad1.right_stick_y+gamepad2.right_stick_y,-1,1);

        double powLeft1 =  Range.clip(left_y+right_y - left_x - right_x,-1,1);
        double powRight1 = Range.clip(left_y+right_y + left_x + right_x,-1,1);
        double powLeft =   Range.clip(left_y+right_y - left_x + right_x,-1,1);
        double powRight =  Range.clip(left_y+right_y + left_x - right_x,-1,1);

        telemetry.addData("Front Left", powLeft1);
        telemetry.addData("Front Right", powRight1);
        telemetry.addData("Back Left", powLeft);
        telemetry.addData("Back Right", powRight);

        motorRight.setPower(powRight);
        motorLeft.setPower(powLeft);
        motorRight1.setPower(powRight1);
        motorLeft1.setPower(powLeft1);

    }

    private void r_catapult(){
        double powCat = -(gamepad1.left_trigger+gamepad2.left_trigger);

        if(gamepad1.left_bumper||gamepad2.left_bumper){
            powCat+=0.5;
        }

        motorCat.setPower(Range.clip(powCat,-1,1));
        telemetry.addData("Catapult",powCat);
    }

    private void r_ballGate(){
        double pos1 = 0.5;
        double pos2 = 0.5;

        if(gamepad1.dpad_up||gamepad2.dpad_up){
            pos1 = 0.5;
            pos2 = 1;
        }
        if(gamepad1.dpad_down||gamepad2.dpad_down){
            pos1 = 1;
            pos2 = 0.5;
        }

        servoGate1.setPosition(Range.clip(pos1,0,1));
        telemetry.addData("Gate 1",Range.clip(pos1,0,1));
        //servoGate2.setPosition(Range.clip(pos2,0,1));
        //telemetry.addData("Gate 2", Range.clip(pos2,0,1));
    }

    private void r_ballCollection(){
        double powSweep = 0;
        double powConv = 0;

        if(gamepad1.a||gamepad2.a){
            powSweep -= 0.8;
        }
        if(gamepad1.y||gamepad2.y){
            powSweep += 0.8;
        }
        if(gamepad1.x||gamepad2.x){
            powConv -= 0.8;
        }
        if(gamepad1.b||gamepad2.b){
            powConv += 0.8;
        }

        telemetry.addData("Sweeper Power",Range.clip(powSweep,-1,1));
        telemetry.addData("Conveyor Power", Range.clip(powConv,-1,1));
        motorSweep.setPower(Range.clip(powSweep,-1,1));
        motorConv.setPower(Range.clip(powConv,-1,1));
    }

    private void r_buttonPusher(){
        double powButt = 0;

        if(gamepad1.back||gamepad2.back){
            powButt = 0.4;
        }
        if(gamepad1.guide||gamepad2.guide){
            powButt = -0.4;
        }

        double posButt = 0.5;

        if(gamepad1.back||gamepad2.back){
            posButt = 0.2;
        }
        if(gamepad1.guide||gamepad2.guide){
            posButt = 0.8;
        }

        telemetry.addData("Button Push Power",Range.clip(powButt,-1,1));
        telemetry.addData("Button Push Position",Range.clip(posButt,-1,1));
        //motor.setPower(Range.clip(powButt,-1,1));
        //servo.setPosition(Range.clip(posButt,0,1));
    }

    private void r_sense(){
        //telemetry.addData("Red",color1.red());
        //telemetry.addData("Green",color1.green());
        //telemetry.addData("Blue",color1.blue());
        telemetry.addData("Ultrasonic 1",ultra1.getUltrasonicLevel());
        telemetry.addData("Ultrasonic 2",ultra2.getUltrasonicLevel());
        //telemetry.addData("Left Distance",ultra);
        //telemetry.addData("Right Distance",ultra);
    }
}
