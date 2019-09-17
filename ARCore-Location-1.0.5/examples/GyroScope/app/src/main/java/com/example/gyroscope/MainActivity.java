package com.example.gyroscope;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView tv;
    private SensorManager sm;
    private Sensor s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)findViewById(R.id.textView1);

        // 센서객체를 얻어오기 위해서는 센서메니저를 통해서만 가능하다
        sm = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);
        s = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION); // 방향센서
    } // end of onCreate

    @Override
    protected void onResume() { // 화면에 보이기 직전에 센서자원 획득
        super.onResume();
        // 센서의 값이 변경되었을 때 콜백 받기위한 리스너를 등록한다
        sm.registerListener(this,        // 콜백 받을 리스너
                s,            // 콜백 원하는 센서
                SensorManager.SENSOR_DELAY_UI); // 지연시간
    }
    @Override
    protected void onPause() { // 화면을 빠져나가면 즉시 센서자원 반납해야함!!
        super.onPause();
        sm.unregisterListener(this); // 반납할 센서
    }

    public void onSensorChanged(SensorEvent event) {
        // 센서값이 변경되었을 때 호출되는 콜백 메서드
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            // 방향센서값이 변경된거라면
            String str = "방향센서값 \n\n"
                    +"\n방위각: "+event.values[0]
                    +"\n피치 : "+event.values[1]
                    +"\n롤 : "+event.values[2];
            tv.setText(str);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // 센서의 정확도가 변경되었을 때 호출되는 콜백 메서드
    }


    /*
    private SensorManager mSensorManager = null;

    //Using the Accelometer
    private SensorEventListener mAccLis;
    private Sensor mAccelometerSensor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Using the Gyroscope & Accelometer
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //Using the Accelometer
        mAccelometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccLis = new AccelometerListener();

        //Touch Listener for Accelometer
        findViewById(R.id.a_start).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){

                    case MotionEvent.ACTION_DOWN:
                        mSensorManager.registerListener(mAccLis, mAccelometerSensor, SensorManager.SENSOR_DELAY_UI);
                        break;

                    case MotionEvent.ACTION_UP:
                        mSensorManager.unregisterListener(mAccLis);
                        break;

                }
                return false;
            }
        });

    }

    @Override
    public void onPause(){
        super.onPause();
        Log.e("LOG", "onPause()");
        mSensorManager.unregisterListener(mAccLis);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.e("LOG", "onDestroy()");
        mSensorManager.unregisterListener(mAccLis);
    }

    private class AccelometerListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {

            double accX = event.values[0];
            double accY = event.values[1];
            double accZ = event.values[2];

            double angleXZ = Math.atan2(accX,  accZ) * 180/Math.PI;
            double angleYZ = Math.atan2(accY,  accZ) * 180/Math.PI;

            //폰이 세워 지면 0 10 0
            //바닥보면 0 0 10
            //가로로 앞을 보면 10 0 0.5
            //하늘 보면 -2 6 -8
            Log.e("LOG", "ACCELOMETER           [X]:" + String.format("%.4f", event.values[0])
                    + "           [Y]:" + String.format("%.4f", event.values[1])
                    + "           [Z]:" + String.format("%.4f", event.values[2])
                    + "           [angleXZ]: " + String.format("%.4f", angleXZ)
                    + "           [angleYZ]: " + String.format("%.4f", angleYZ));

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
*/

//    private SensorManager mSensorManager = null;
//
//    //Using the Gyroscope
//    private SensorEventListener mGyroLis;
//    private Sensor mGgyroSensor = null;
//
//    //Roll and Pitch
//    private double pitch;
//    private double roll;
//    private double yaw;
//
//    //timestamp and dt
//    private double timestamp;
//    private double dt;
//
//    // for radian -> dgree
//    private double RAD2DGR = 180 / Math.PI;
//    private static final float NS2S = 1.0f/1000000000.0f;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        //Using the Gyroscope & Accelometer
//        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//
//        //Using the Accelometer
//        mGgyroSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
//        mGyroLis = new GyroscopeListener();
//
//        //Touch Listener for Accelometer
//        findViewById(R.id.a_start).setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()){
//
//                    case MotionEvent.ACTION_DOWN:
//                        mSensorManager.registerListener(mGyroLis, mGgyroSensor, SensorManager.SENSOR_DELAY_UI);
//                        break;
//
//                    case MotionEvent.ACTION_UP:
//                        mSensorManager.unregisterListener(mGyroLis);
//                        break;
//
//                }
//                return false;
//            }
//        });
//
//    }
//
//    @Override
//    public void onPause(){
//        super.onPause();
//        Log.e("LOG", "onPause()");
//        mSensorManager.unregisterListener(mGyroLis);
//    }
//
//    @Override
//    public void onDestroy(){
//        super.onDestroy();
//        Log.e("LOG", "onDestroy()");
//        mSensorManager.unregisterListener(mGyroLis);
//    }
//
//    private class GyroscopeListener implements SensorEventListener {
//
//        @Override
//        public void onSensorChanged(SensorEvent event) {
//
//            /* 각 축의 각속도 성분을 받는다. */
//            double gyroX = event.values[0];
//            double gyroY = event.values[1];
//            double gyroZ = event.values[2];
//
//            /* 각속도를 적분하여 회전각을 추출하기 위해 적분 간격(dt)을 구한다.
//             * dt : 센서가 현재 상태를 감지하는 시간 간격
//             * NS2S : nano second -> second */
//            dt = (event.timestamp - timestamp) * NS2S;
//            timestamp = event.timestamp;
//
//            /* 맨 센서 인식을 활성화 하여 처음 timestamp가 0일때는 dt값이 올바르지 않으므로 넘어간다. */
//            if (dt - timestamp*NS2S != 0) {
//
//                /* 각속도 성분을 적분 -> 회전각(pitch, roll)으로 변환.
//                 * 여기까지의 pitch, roll의 단위는 '라디안'이다.
//                 * SO 아래 로그 출력부분에서 멤버변수 'RAD2DGR'를 곱해주어 degree로 변환해줌.  */
//                pitch = pitch + gyroY*dt;
//                roll = roll + gyroX*dt;
//                yaw = yaw + gyroZ*dt;
//
// //세움  GYROSCOPE           [X]:0.0064           [Y]:0.0941           [Z]:-0.0023           [Pitch]: -109.4           [Roll]: -43.3           [Yaw]: 70.7           [dt]: 0.0600
////눞임   GYROSCOPE           [X]:-0.0016          [Y]:-0.0005          [Z]:0.0000            [Pitch]: -108.4           [Roll]: -44.8           [Yaw]: 69.8           [dt]: 0.0600
//
//                Log.e("LOG", "GYROSCOPE           [X]:" + String.format("%.4f", event.values[0])
//                        + "           [Y]:" + String.format("%.4f", event.values[1])
//                        + "           [Z]:" + String.format("%.4f", event.values[2])
//                        + "           [Pitch]: " + String.format("%.1f", pitch*RAD2DGR)
//                        + "           [Roll]: " + String.format("%.1f", roll*RAD2DGR)
//                        + "           [Yaw]: " + String.format("%.1f", yaw*RAD2DGR)
//                        + "           [dt]: " + String.format("%.4f", dt));
//
//            }
//        }
//
//        @Override
//        public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//        }
//    }



}
