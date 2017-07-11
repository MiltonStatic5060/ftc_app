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
 * Created by ryan on 2/12/17. auryan898@gmail.com 617-615-9076
 */

//@TeleOp(name = "Template", group = "Concept")
@Autonomous(name = "Template", group = "Concept")
@Disabled
public class RNxt1 extends LinearOpMode {
    //DECLARATION
    DcMotor motorLeft;
    DcMotor motorRight;
    DcMotor motorLeft1;
    DcMotor motorRight1;

    DcMotor motorArm;
    DcMotor motorConv;

    Servo servoClaw;
    Servo servoWrist;

    UltrasonicSensor dist1;
    //LightSensor color1;
    UltrasonicSensor color1;

    double wristPosition;
    double clawPosition;
    double powConv;
    @Override public void runOpMode() {

        //INITIALIZATION
        motorLeft = hardwareMap.dcMotor.get("left");
        motorRight = hardwareMap.dcMotor.get("right");
        motorLeft1 = hardwareMap.dcMotor.get("left1");
        motorRight1 = hardwareMap.dcMotor.get("right1");
        motorArm = hardwareMap.dcMotor.get("arm");
        motorConv = hardwareMap.dcMotor.get("conveyor");

        dist1 = hardwareMap.ultrasonicSensor.get("dist1");
        //color1 = hardwareMap.lightSensor.get("color1");
        color1 = hardwareMap.ultrasonicSensor.get("color1");

        servoClaw = hardwareMap.servo.get("claw");
        servoWrist = hardwareMap.servo.get("wrist");


        /**
         * Wait until we've been given the ok to go. For something to do, we emit the
         * elapsed time as we sit here and wait. If we didn't want to do anything while
         * we waited, we would just call {@link #waitForStart()}.
         */
        while (!isStarted()) {
            motorRight.setDirection(DcMotor.Direction.REVERSE);
            motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorRight1.setDirection(DcMotor.Direction.REVERSE);
            motorLeft1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorRight1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorConv.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            wristPosition = 0.1;
            //Give variables values and set modes and directions of devices
            powConv = 0;

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
            while(gamepad1.right_stick_button){
                motorLeft1.setPower(1);
            }
            r_motorPower();
            r_motorArm();
            r_servoArm();
            r_sensorSense();

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
    private void r_motorPower(){
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



        if(gamepad1.start||gamepad2.start||gamepad2.dpad_up||gamepad1.dpad_up){
            powConv = 0.3;
        }
        if(gamepad1.back||gamepad2.back||gamepad1.dpad_down||gamepad2.dpad_down){
            powConv = -0.3;
        }
        if(gamepad1.guide||gamepad2.guide||gamepad1.right_bumper||gamepad2.right_bumper){
            powConv = 0;
        }
        motorConv.setPower(powConv);
    }
    private void r_motorArm(){
        double powArm;

        powArm = gamepad1.left_trigger+gamepad2.left_trigger;
        if(gamepad1.left_bumper||gamepad2.left_bumper){
            powArm-=0.5;
        }

        if(gamepad1.a||gamepad2.a){
            powArm = 0.10;
        } else if(gamepad1.x||gamepad2.x){
            powArm = 0.20;
        } else if(gamepad1.y||gamepad2.y){
            powArm = 0.40;
        } else if(gamepad1.b||gamepad2.b){
            powArm = 0.60;
        }

        motorArm.setPower(Range.clip(powArm,-1,1));
    }
    private void r_servoArm(){

        clawPosition = 0.95;
        double x = gamepad1.right_trigger+gamepad2.right_trigger;
        x = Range.clip(x,0,1);
        x = 0.95-(x/10);
        clawPosition = x;

        if(gamepad1.dpad_right||gamepad2.dpad_right){
            wristPosition = 0.9;
        }
        if(gamepad1.dpad_left||gamepad2.dpad_left){
            wristPosition = 0.1;
        }

        servoWrist.setPosition(wristPosition);
        servoClaw.setPosition(clawPosition);
    }
    private void r_sensorSense(){
        telemetry.addData("Distance 1", dist1.getUltrasonicLevel());
        //telemetry.addData("Light 1",color1.getLightDetected());
        telemetry.addData("Distance 2",color1.getUltrasonicLevel());

    }
}
