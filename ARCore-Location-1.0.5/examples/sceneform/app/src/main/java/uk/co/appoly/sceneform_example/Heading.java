//package uk.co.appoly.sceneform_example;
//
//import android.content.Context;
//import android.hardware.Sensor;
//import android.hardware.SensorEvent;
//import android.hardware.SensorEventListener;
//import android.hardware.SensorManager;
//import android.view.animation.Animation;
//import android.view.animation.RotateAnimation;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class Heading {
//    Context mcontext1;
//    float e1;
//    float e2;
//    float e3;
//
//
//
//    private SensorManager mSensorManager;
//    private float[] mLastAccelerometer = new float[3];
//    private float[] mLastMagnetometer = new float[3];
//    private boolean mLastAccelerometerSet = false;
//    private boolean mLastMagnetometerSet = false;
//    private float[] mR = new float[9];
//    private float[] mOrientation = new float[3];
//    private float mCurrentDegree = 0f;
//
//    Heading(){}
//    Heading(Context context){ mcontext1 = context;    }
//    void Heading() {
//
//
////        // 센서객체를 얻어오기 위해서는 센서메니저를 통해서만 가능하다
////
//        ((LocationActivity) mcontext1).sm = (SensorManager) mcontext1.getSystemService(Context.SENSOR_SERVICE);
//        ((LocationActivity) mcontext1).s1 = ((LocationActivity) mcontext1).sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        ((LocationActivity) mcontext1).s2 = ((LocationActivity) mcontext1).sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
//
//        //((LocationActivity) mcontext1).s1 = ((LocationActivity) mcontext1).sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
//
//
//        SensorEventListener _SensorEventListener=   new SensorEventListener() {
//            @Override
//            public void onSensorChanged(SensorEvent event) {
//                if(event.sensor == ((LocationActivity) mcontext1).s1) {
//System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
//mLastAccelerometerSet = true;
//} else if(event.sensor == ((LocationActivity) mcontext1).s2) {
//System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
//mLastMagnetometerSet = true;
//}
//if(mLastAccelerometerSet && mLastMagnetometerSet) {
//SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
//float azimuthinDegress = (int) ( Math.toDegrees( SensorManager.getOrientation( mR, mOrientation)[0] ) + 360 ) % 360;
//
//RotateAnimation ra = new RotateAnimation(
//                            mCurrentDegree,
//                            -azimuthinDegress,
//Animation.RELATIVE_TO_SELF, 0.5f,
//Animation.RELATIVE_TO_SELF, 0.5f
//);
//ra.setDuration(250);
//ra.setFillAfter(true);
//                    ((LocationActivity) mcontext1).nimagebutton.startAnimation(ra);
//mCurrentDegree = -azimuthinDegress;
//}
//
//
//
//
//
//
//
////                if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
////                    // 방향센서값이 변경된거라면
////                    e1=Float.parseFloat(String.format("%3.0f",event.values[0]));
////                    e2=Float.parseFloat(String.format("%3.3f",event.values[1]));
////                    e3=Float.parseFloat(String.format("%3.3f",event.values[2]));
////                    String str = "방향센서값 \n\n"
////                            +"\n방위각: "+ e1
////                            +"\n피치 : "+ e2
////                            +"\n롤 : "+ e3;
////                    ((LocationActivity) mcontext1).tv.setText(str);
////
////                    RotateAnimation ra = new RotateAnimation(
////                                mCurrentDegree,
////                                e1,
////                            Animation.RELATIVE_TO_SELF, 0.5f,
////                            Animation.RELATIVE_TO_SELF, 0.5f
////                    );
////                    mCurrentDegree=e1;
////                    ((LocationActivity) mcontext1).nimagebutton.startAnimation(ra);
////                }
//            }
////
//            @Override
//            public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//            }
//        };
//        ((LocationActivity) mcontext1).sm.registerListener(_SensorEventListener , ((LocationActivity) mcontext1).s1, SensorManager.SENSOR_DELAY_GAME);
//        ((LocationActivity) mcontext1).sm.registerListener(_SensorEventListener , ((LocationActivity) mcontext1).s2, SensorManager.SENSOR_DELAY_GAME);
//
//
//
//
//
//
//
//
//
//
//
//
//
//    }
//
//    float getHeading(){
//        return e1;
//    }
//
//
//
//}
