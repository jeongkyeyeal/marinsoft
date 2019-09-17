package com.example.tabbutton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.GeomagneticField;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


//public class MainActivity extends AppCompatActivity {
//
//    private Button button1;
//    private TextView txtResult;
//
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        button1 = (Button)findViewById(R.id.button1);
//        txtResult = (TextView)findViewById(R.id.txtResult);
//        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if ( Build.VERSION.SDK_INT >= 23 &&
//                        ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
//                    ActivityCompat.requestPermissions( MainActivity.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
//                            0 );
//                }
//                else{
//
//                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
//                            1000,
//                            1,
//                            gpsLocationListener);
//                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                            1000,
//                            1,
//                            gpsLocationListener);
//
//                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                    String provider = location.getProvider();
//                    double longitude = location.getLongitude();
//                    double latitude = location.getLatitude();
//                    double altitude = location.getAltitude();
//
//                    txtResult.setText("위치정보 : " + provider + "\n" +
//                            "위도 : " + longitude + "\n" +
//                            "경도 : " + latitude + "\n" +
//                            "고도  : " + altitude);
//
//
//
//                }
//            }
//        });
//
//
//
//
//
//    }
//
//    public void menuClick(View view) {
//        PopupMenu popupMenu = new PopupMenu(this,view);
//        MenuInflater inflater = popupMenu.getMenuInflater();
//        Menu menu = popupMenu.getMenu();
//        inflater.inflate(R.menu.menu_main,menu);
//
//        popupMenu.setOnMenuItemClickListener(
//                new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//                        if(menuItem.getItemId()==R.id.menuItem1){
//                            Toast.makeText(getApplicationContext(), "1번 선택", Toast.LENGTH_SHORT).show();
//                        }
//                        if(menuItem.getItemId()==R.id.menuItem2){
//                            Toast.makeText(getApplicationContext(), "2번 선택", Toast.LENGTH_SHORT).show();
//                        }
//
//                        return false;
//                    }
//                }
//
//        );
//        popupMenu.show();
//    }
//
//
//    final LocationListener gpsLocationListener = new LocationListener() {
//        public void onLocationChanged(Location location) {
//
//            String provider = location.getProvider();
//            double longitude = location.getLongitude();
//            double latitude = location.getLatitude();
//            double altitude = location.getAltitude();
//            double bearingAccuracyDegrees  =location.getBearing();
//
//            txtResult.setText("위치정보 : " + provider + "\n" +
//                    "위도 : " + longitude + "\n" +
//                    "경도 : " + latitude + "\n" +
//                    "고도  : " + altitude + "\n" +
//                    "베어링2 : "+ bearingAccuracyDegrees
//
//            );
//
//        }
//
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//        }
//
//        public void onProviderEnabled(String provider) {
//        }
//
//        public void onProviderDisabled(String provider) {
//        }
//    };
//
//
//
//
//
//}


public class MainActivity extends AppCompatActivity {

    TextView tv;
    Button tb;
    ImageButton imageButton;
    SupportMapFragment mapFragment;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Location 제공자에서 정보를 얻어오기(GPS)
        // 1. Location을 사용하기 위한 권한을 얻어와야한다 AndroidManifest.xml
        //     ACCESS_FINE_LOCATION : NETWORK_PROVIDER, GPS_PROVIDER
        //     ACCESS_COARSE_LOCATION : NETWORK_PROVIDER
        // 2. LocationManager 를 통해서 원하는 제공자의 리스너 등록
        // 3. GPS 는 에뮬레이터에서는 기본적으로 동작하지 않는다
        // 4. 실내에서는 GPS_PROVIDER 를 요청해도 응답이 없다.  특별한 처리를 안하면 아무리 시간이 지나도
        //    응답이 없다.
        //    해결방법은
        //     ① 타이머를 설정하여 GPS_PROVIDER 에서 일정시간 응답이 없는 경우 NETWORK_PROVIDER로 전환
        //     ② 혹은, 둘다 한꺼번헤 호출하여 들어오는 값을 사용하는 방식.

        tv = findViewById(R.id.txtResult);
        tv.setText("위치정보 미수신중");
        imageButton= findViewById(R.id.imgbtn);

        tb = (Button)findViewById(R.id.button1);

        // LocationManager 객체를 얻어온다
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                        tv.setText("수신중..");
                        // GPS 제공자의 정보가 바뀌면 콜백하도록 리스너 등록하기~!!!
                        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                                100, // 통지사이의 최소 시간간격 (miliSecond)
                                1, // 통지사이의 최소 변경거리 (m)
                                mLocationListener);
                        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                                100, // 통지사이의 최소 시간간격 (miliSecond)
                                1, // 통지사이의 최소 변경거리 (m)
                                mLocationListener);

                }catch(SecurityException ex){
                }
            }
        });

        mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Log.d("Map","지도 준비됨.");
                map = googleMap;

            }
        });
        try {
            MapsInitializer.initialize(this);
        }catch (Exception e){
            e.printStackTrace();
        }



    } // end of onCreate

    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            //여기서 위치값이 갱신되면 이벤트가 발생한다.
            //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.

            Log.d("test", "onLocationChanged, location:" + location);
            double longitude = location.getLongitude(); //경도
            double latitude = location.getLatitude();   //위도
            double altitude = location.getAltitude();   //고도
            float accuracy = location.getAccuracy();    //정확도
            String provider = location.getProvider();   //위치제공자
            //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
            //Network 위치제공자에 의한 위치변화
            //Network 위치는 Gps에 비해 정확도가 많이 떨어진다.
            tv.setText("위치정보 : " + provider + "\n위도 : " + longitude + "\n경도 : " + latitude
                    + "\n고도 : " + altitude + "\n정확도 : "  + accuracy);
            showCurrentLocation(latitude,longitude);
        }
        public void onProviderDisabled(String provider) {
            // Disabled시
            Log.d("test", "onProviderDisabled, provider:" + provider);
        }

        public void onProviderEnabled(String provider) {
            // Enabled시
            Log.d("test", "onProviderEnabled, provider:" + provider);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
            Log.d("test", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
        }
    };

    private void showCurrentLocation(double latitude, double longitude) {
        LatLng curPoint = new LatLng(latitude,longitude);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint,15));
    }

    public void menuClick(View view) {
        PopupMenu popupMenu = new PopupMenu(this,view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        Menu menu = popupMenu.getMenu();
        inflater.inflate(R.menu.menu_main,menu);

        popupMenu.setOnMenuItemClickListener(
                new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId()==R.id.menuItem1){
                            Toast.makeText(getApplicationContext(), "1번 선택", Toast.LENGTH_SHORT).show();
                        }
                        if(menuItem.getItemId()==R.id.menuItem2){
                            Toast.makeText(getApplicationContext(), "2번 선택", Toast.LENGTH_SHORT).show();
                        }

                        return false;
                    }
                }

        );
        popupMenu.show();

    }
} // end of class


