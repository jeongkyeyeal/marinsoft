package uk.co.appoly.sceneform_example;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import uk.co.appoly.arcorelocation.LocationScene;
import uk.co.appoly.arcorelocation.sensor.DeviceLocation;
import uk.co.appoly.arcorelocation.utils.LocationUtils;

public class GPSLcation {
    Context mcontext;
    Activity mActivity;
    GPSLcation(){}
    GPSLcation(Context context,Activity activity){
        mcontext = context;
        mActivity = activity;
    }


    final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            //여기서 위치값이 갱신되면 이벤트가 발생한다.
            //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.



            double longitude = Double.parseDouble(String.format("%.3f",location.getLongitude())); //경도
            double latitude = Double.parseDouble(String.format("%.3f",location.getLatitude()));   //위도
            double altitude = Double.parseDouble(String.format("%.3f",location.getAltitude())); //고도

            LocationScene locationScene =  new LocationScene(mcontext, mActivity, ((LocationActivity) mcontext).arSceneView);
            double longitude1 = locationScene.deviceLocation.currentBestLocation.getLongitude(); //경도
            double latitude1 =locationScene.deviceLocation.currentBestLocation.getLatitude();   //위도
            double altitude1 = locationScene.deviceLocation.currentBestLocation.getAltitude(); //고도

//            Location locationA = new Location("A");
//            locationA.setLatitude(129.039);
//            locationA.setLongitude(35.088);
//            float distance = location.distanceTo(locationA);

            String distance = calcDistance(latitude1, longitude1, 129.039, 35.088);
            double distance1 = LocationUtils.distance(latitude1, 35.097, longitude1, 129.034, 0.0D, 0.0D);
            String provider = location.getProvider();   //위치제공자
            //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
            //Network 위치제공자에 의한 위치변화
            //Network 위치는 Gps에 비해 정확도가 많이 떨어진다.
            double bearing = LocationUtils.bearing(129.039,35.088,latitude1,longitude1);
            ((LocationActivity) mcontext).textView.setText("위치정보 : " + provider + "\n위도 : " + longitude1 + "\n경도 : " + latitude1
                    + "\n고도 : " + altitude1 + "\n거리 : " +  distance1 + "\n각도 : " +  bearing);

           // ((LocationActivity) mcontext).textView.setText("\n고도 : " + distance + "\n거리 : " +  distance1);
        }
        public void onProviderDisabled(String provider) {
            // Disabled시

        }

        public void onProviderEnabled(String provider) {
            // Enabled시

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시

        }
    };
    public static String calcDistance(double lat1, double lon1, double lat2, double lon2){
        double EARTH_R, Rad, radLat1, radLat2, radDist;
        double distance, ret;

        EARTH_R = 6371000.0;
        Rad = Math.PI/180;
        radLat1 = Rad * lat1;
        radLat2 = Rad * lat2;
        radDist = Rad * (lon1 - lon2);

        distance = Math.sin(radLat1) * Math.sin(radLat2);
        distance = distance + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radDist);
        ret = EARTH_R * Math.acos(distance);

        double rslt = Math.round(Math.round(ret) / 1000);
        String result = rslt + " km";
        if(rslt == 0) result = Math.round(ret) +" m";

        return result;
    }


}
