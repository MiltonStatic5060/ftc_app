package org.firstinspires.ftc.teamcode.competition2017;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.*;



@TeleOp(name = "SensorTest", group = "Competition2017-18")
//@Autonomous(name = "Concept: NullOp", group = "Concept")
//@Disabled
public class SensorTest extends OpMode implements SensorEventListener {
    TouchSensor touch1;
    UltrasonicSensor frontDist;
    UltrasonicSensor leftDist;
    // UltrasonicSensor rightDist;

    Sensor magnetometer;
    Sensor rotationSensor;
    Sensor accelerometer;

    SensorManager mSensorManager;    

    float azimuth = 0.0f;      // value in radians
    float pitch = 0.0f;        // value in radians
    float roll = 0.0f;         // value in radians
    float[] rotationVector = {0.0f,0.0f,0.0f,0.0f,0.0f};

    float[] mGravity;       // latest sensor values
    float[] mGeomagnetic;   // latest sensor values
    float[] mRotationVector;

    public SensorTest(){
        //empty constructor
    }

    @Override
    public void init(){
        frontDist = hardwareMap.get(UltrasonicSensor.class, "frontDist");
        leftDist  = hardwareMap.get(UltrasonicSensor.class, "leftDist");
        // rightDist = hardwareMap.get(UltrasonicSensor.class, "rightDist");
        touch1    = hardwareMap.get(TouchSensor.class,      "touch1");

        mSensorManager = (SensorManager) hardwareMap.appContext.getSystemService(Context.SENSOR_SERVICE);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        rotationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        azimuth = 0.0f;      // value in radians
        pitch = 0.0f;        // value in radians
        roll = 0.0f;

        for(int i=0; i<4; i++) {
            rotationVector[i] = 0.0f;
        }
    }
    @Override
    public void start(){
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
    }

    @Override
    public void loop(){
        telemetry.addData("frontDist ",frontDist);
        telemetry.addData("leftDist  ",leftDist);
        // telemetry.addData("rightDist ",rightDist);
        telemetry.addData("touch1    ",touch1);
        sensor_telemetry();
    }
    @Override
    public void stop() {
        mSensorManager.unregisterListener(this);
    }

    public void sensor_telemetry(){
        if (mGravity != null && mGeomagnetic != null && mRotationVector != null) {
            telemetry.addData("azimuth", Math.round(Math.toDegrees(azimuth)));
            telemetry.addData("pitch", Math.round(Math.toDegrees(pitch)));
            telemetry.addData("roll", Math.round(Math.toDegrees(roll)));
            
            telemetry.addData("1 x", String.format("%8.4f, y:%8.4f", rotationVector[0], rotationVector[1]));
            telemetry.addData("2 z", String.format("%8.4f, cos(θ/2):%8.4f", rotationVector[2], rotationVector[3]));
            telemetry.addData("1 x", rotationVector[0]+", y:"+rotationVector[1]);
            telemetry.addData("2 z", rotationVector[2]+", cos(θ/2):"+rotationVector[4]);
            if (rotationVector[4] == -1.0f) {
                telemetry.addData("3 Accuracy", " value unavailable");
            } else {
                telemetry.addData("3 Accuracy", rotationVector[4] + " radians");
            }
        }
        else {
            if (mGravity != null) {
                telemetry.addData("note1", "no default accelerometer sensor on phone");
            }
            if (mGeomagnetic != null) {
                telemetry.addData("note2", "no default magnetometer sensor on phone");
            }
            if (mRotationVector != null) {
                telemetry.addData("note", "no default rotation sensor on phone");
            }
        }
    }

    public void onSensorChanged(SensorEvent event) {
        // we need both sensor values to calculate orientation
        // only one value will have changed when this method called, we assume we can still use the other value.
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mGravity = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mGeomagnetic = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            mRotationVector = event.values;
        }
        if (mGravity != null && mGeomagnetic != null && mRotationVector != null) {  //make sure we have both before calling getRotationMatrix
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimuth = orientation[0]; // orientation contains: azimuth, pitch and roll
                pitch = orientation[1];
                roll = orientation[2];
            }
            
            rotationVector[0] = mRotationVector[0];
            rotationVector[1] = mRotationVector[1];
            rotationVector[2] = mRotationVector[2];
            rotationVector[3] = mRotationVector[3];
            rotationVector[4] = mRotationVector[4];
        }
    }

    public double[] somefunction(){

        if (mGravity != null && mGeomagnetic != null && mRotationVector != null) {
            telemetry.addData("azimuth", Math.round(Math.toDegrees(azimuth)));
            telemetry.addData("pitch", Math.round(Math.toDegrees(pitch)));
            telemetry.addData("roll", Math.round(Math.toDegrees(roll)));
            
            telemetry.addData("1 x", String.format("%8.4f, y:%8.4f", rotationVector[0], rotationVector[1]));
            telemetry.addData("2 z", String.format("%8.4f, cos(θ/2):%8.4f", rotationVector[2], rotationVector[3]));
            telemetry.addData("1 x", rotationVector[0]+", y:"+rotationVector[1]);
            telemetry.addData("2 z", rotationVector[2]+", cos(θ/2):"+rotationVector[4]);
            if (rotationVector[4] == -1.0f) {
                telemetry.addData("3 Accuracy", " value unavailable");
            } else {
                telemetry.addData("3 Accuracy", rotationVector[4] + " radians");
            }
        }
        else {
            if (mGravity != null) {
                telemetry.addData("note1", "no default accelerometer sensor on phone");
            }
            if (mGeomagnetic != null) {
                telemetry.addData("note2", "no default magnetometer sensor on phone");
            }
            if (mRotationVector != null) {
                telemetry.addData("note", "no default rotation sensor on phone");
            }
        }
        double[] arr = {
                (double)Math.round(Math.toDegrees(azimuth)),
                (double)Math.round(Math.toDegrees(pitch)),
                (double)Math.round(Math.toDegrees(roll)),
                

        };
        return arr;
    }
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not sure if needed, placeholder just in case
    }
}
