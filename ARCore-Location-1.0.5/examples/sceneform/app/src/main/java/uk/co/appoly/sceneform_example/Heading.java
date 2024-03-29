package uk.co.appoly.sceneform_example;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class Heading {
    Context mcontext1;

    Heading(){}
    Heading(Context context){ mcontext1 = context;    }
    void Heading() {


        // 센서객체를 얻어오기 위해서는 센서메니저를 통해서만 가능하다
        ((LocationActivity) mcontext1).sm = (SensorManager) mcontext1.getSystemService(Context.SENSOR_SERVICE);
        ((LocationActivity) mcontext1).s = ((LocationActivity) mcontext1).sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        SensorEventListener _SensorEventListener=   new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                    // 방향센서값이 변경된거라면
                    String str = "방향센서값 \n\n"
                            +"\n방위각: "+ Float.parseFloat(String.format("%3.3f",event.values[0]))
                            +"\n피치 : "+Float.parseFloat(String.format("%3.3f",event.values[1]))
                            +"\n롤 : "+Float.parseFloat(String.format("%3.3f",event.values[2]));
                    ((LocationActivity) mcontext1).tv.setText(str);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        ((LocationActivity) mcontext1).sm.registerListener(_SensorEventListener , ((LocationActivity) mcontext1).s, SensorManager.SENSOR_DELAY_UI);
    }




}
