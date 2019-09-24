/*
 * Copyright 2018 Google LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.co.appoly.sceneform_example;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.Frame;
import com.google.ar.core.Plane;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.core.exceptions.UnavailableException;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import uk.co.appoly.arcorelocation.LocationMarker;
import uk.co.appoly.arcorelocation.LocationScene;
import uk.co.appoly.arcorelocation.rendering.LocationNode;
import uk.co.appoly.arcorelocation.rendering.LocationNodeRender;
import uk.co.appoly.arcorelocation.utils.ARLocationPermissionHelper;

import static android.view.LayoutInflater.from;

/**
 * This is a simple example that shows how to create an augmented reality (AR) application using the
 * ARCore and Sceneform APIs.
 */
public class LocationActivity extends AppCompatActivity {
    private boolean installRequested;

    private Snackbar loadingMessageSnackbar = null;
    private int currentApiVersion;
    ArSceneView arSceneView;

    // Renderables for this example
    private ModelRenderable andyRenderable;
    private ViewRenderable exampleLayoutRenderable;


    // Our ARCore-Location scene
    private LocationScene locationScene;
    private Button button;

    private View 	decorView;
    private int	uiOption;

    TextView textView;
    TextView tv;
    SensorManager sm;
    Sensor s1;
    Sensor s2;
    TextView ntextview ;
    ImageView nimagebutton;
    ListView lv;

    FrameLayout f;
    View view;
    Locale en;
    Configuration config;
    LayoutInflater minflater;
    View ll;
    View but;

    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    // CompletableFuture requires api level 24
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_HIDDEN);


         minflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
         view = getLayoutInflater().inflate(R.layout.activity_sceneform,null);
        f=  (FrameLayout) minflater.inflate(R.layout.activity_sceneform,(ViewGroup) view,true);
        //FrameLayout f = (FrameLayout) fl;
        setContentView(f);
//      setContentView(R.layout.activity_sceneform);
        //View view1 = minflater.inflate(R.layout.activity_sceneform,null);
         ll = minflater.inflate(R.layout.loading_layout,null);
        //minflater.inflate(R.layout.loading_layout,f,true);
            but= minflater.inflate(R.layout.listview_button,null);
        f.addView(ll);
        f.addView(but);
        ll.setVisibility(View.GONE);

//        CheckTypesTask task = new CheckTypesTask();
//        task.execute();


        decorView = getWindow().getDecorView();
        uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;



        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        arSceneView = findViewById(R.id.ar_scene_view);

        button = (Button) findViewById(R.id.imgbtn);//button
        registerForContextMenu(button);
//        textView = (TextView) findViewById(R.id.textView);
//        tv= (TextView) findViewById(R.id.textView2);

        //ntextview = (TextView)findViewById(R.id.ntextView);
//        nimagebutton = (ImageView)findViewById(R.id.nimageView);
//        GradientDrawable drawable=
//                (GradientDrawable) getApplicationContext().getDrawable(R.drawable.cirecle_compass);
//
//        nimagebutton.setBackground(new ShapeDrawable(new OvalShape()));
//        nimagebutton.setClipToOutline(true);
//
//        //헤딩
//        Heading heading =  new Heading(this);
//        heading.Heading();




        //GPS위치
        GPSLcation gpsLcation= new GPSLcation(this,this);
        try {
            // GPS 제공자의 정보가 바뀌면 콜백하도록 리스너 등록하기~!!!
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                    100, // 통지사이의 최소 시간간격 (miliSecond)
                    1, // 통지사이의 최소 변경거리 (m)
                    gpsLcation.mLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                    100, // 통지사이의 최소 시간간격 (miliSecond)
                    1, // 통지사이의 최소 변경거리 (m)
                    gpsLcation.mLocationListener);
        }catch (Exception e){
            e.printStackTrace();
        }


        //나침반



        // Build a renderable from a 2D View.
        CompletableFuture<ViewRenderable> exampleLayout =
                ViewRenderable.builder()
                        .setView(this, R.layout.example_layout)
                        .build();

        // When you build a Renderable, Sceneform loads its resources in the background while returning
        // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
        CompletableFuture<ModelRenderable> andy = ModelRenderable.builder()
                .setSource(this, R.raw.andy)
                .build();


        CompletableFuture.allOf(
                exampleLayout,
                andy)
                .handle(
                        (notUsed, throwable) -> {
                            // When you build a Renderable, Sceneform loads its resources in the background while
                            // returning a CompletableFuture. Call handle(), thenAccept(), or check isDone()
                            // before calling get().

                            if (throwable != null) {
                                DemoUtils.displayError(this, "Unable to load renderables", throwable);
                                return null;
                            }

                            try {
                                exampleLayoutRenderable = exampleLayout.get();
                                andyRenderable = andy.get();

                            } catch (InterruptedException | ExecutionException ex) {
                                DemoUtils.displayError(this, "Unable to load renderables", ex);
                            }

                            return null;
                        });

        // Set an update listener on the Scene that will hide the loading message once a Plane is
        // detected.
        arSceneView
                .getScene()
                .setOnUpdateListener(
                        frameTime -> {

                            if (locationScene == null) {
                                // If our locationScene object hasn't been setup yet, this is a good time to do it
                                // We know that here, the AR components have been initiated.
                                locationScene = new LocationScene(this, this, arSceneView);

                                // Now lets create our location markers.
                                // First, a layout
                                LocationMarker layoutLocationMarker = new LocationMarker(
                                               129.039,
                                            35.088,
                                        getExampleView()
                                );

								               locationScene.mLocationMarkers.add(
                                        new LocationMarker(
                                                129.040,
                                                35.090,
                                                getAndy()));
                                locationScene.mLocationMarkers.add(
                                        new LocationMarker(
                                                129.041,
                                                35.090,
                                                getAndy()));

                                // An example "onRender" event, called every frame
                                // Updates the layout with the markers distance
                                layoutLocationMarker.setRenderEvent(new LocationNodeRender() {
                                    @Override
                                    public void render(LocationNode node) {
                                        View eView = exampleLayoutRenderable.getView();
                                        TextView distanceTextView = eView.findViewById(R.id.textView2);
                                        distanceTextView.setText(node.getDistance() + "M");
                                    }
                                });
                                // Adding the marker
                                locationScene.mLocationMarkers.add(layoutLocationMarker);

                                // Adding a simple location marker of a 3D model
                                locationScene.mLocationMarkers.add(
                                        new LocationMarker(
												129.039,
                                                35.088,
                                                getAndy()));
                            }

                            Frame frame = arSceneView.getArFrame();
                            if (frame == null) {
                                return;
                            }

                            if (frame.getCamera().getTrackingState() != TrackingState.TRACKING) {
                                return;
                            }

                            if (locationScene != null) {
                                locationScene.processFrame(frame);
                            }

                            if (loadingMessageSnackbar != null) {
                                for (Plane plane : frame.getUpdatedTrackables(Plane.class)) {
                                    if (plane.getTrackingState() == TrackingState.TRACKING) {
                                        hideLoadingMessage();
                                    }
                                }
                            }
                        });


        // Lastly request CAMERA & fine location permission which is required by ARCore-Location.
        ARLocationPermissionHelper.requestPermission(this);
    }









    /**
     * Example node of a layout
     *
     * @return
     */
    private Node getExampleView() {
        Node base = new Node();
        base.setRenderable(exampleLayoutRenderable);
        Context c = this;
        // Add  listeners etc here
        View eView = exampleLayoutRenderable.getView();
        eView.setOnTouchListener((v, event) -> {
            Toast.makeText(
                    c, "Location marker touched.", Toast.LENGTH_LONG)
                    .show();
            return false;
        });

        return base;
    }

    /***
     * Example Node of a 3D model
     *
     * @return
     */
    private Node getAndy() {
        Node base = new Node();
        base.setRenderable(andyRenderable);
        Context c = this;
        base.setOnTapListener((v, event) -> {
            Toast.makeText(
                    c, "Andy touched.", Toast.LENGTH_LONG)
                    .show();
        });
        return base;
    }

    /**
     * Make sure we call locationScene.resume();
     */
    @Override
    protected void onResume() {
        super.onResume();
        onWindowFocusChanged(true);
        if (locationScene != null) {
            locationScene.resume();
        }

        if (arSceneView.getSession() == null) {
            // If the session wasn't created yet, don't resume rendering.
            // This can happen if ARCore needs to be updated or permissions are not granted yet.
            try {
                Session session = DemoUtils.createArSession(this, installRequested);
                if (session == null) {
                    installRequested = ARLocationPermissionHelper.hasPermission(this);
                    return;
                } else {
                    arSceneView.setupSession(session);
                }
            } catch (UnavailableException e) {
                DemoUtils.handleSessionException(this, e);
            }
        }

        try {
            arSceneView.resume();
        } catch (CameraNotAvailableException ex) {
            DemoUtils.displayError(this, "Unable to get camera", ex);
            finish();
            return;
        }

        if (arSceneView.getSession() != null) {

        }
    }

    /**
     * Make sure we call locationScene.pause();
     */
    @Override
    public void onPause() {
        super.onPause();
        onWindowFocusChanged(true);
        if (locationScene != null) {
            locationScene.pause();
        }

        arSceneView.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        arSceneView.destroy();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] results) {
        if (!ARLocationPermissionHelper.hasPermission(this)) {
            if (!ARLocationPermissionHelper.shouldShowRequestPermissionRationale(this)) {
                // Permission denied with checking "Do not ask again".
                ARLocationPermissionHelper.launchPermissionSettings(this);
            } else {
                Toast.makeText(
                        this, "Camera permission is needed to run this application", Toast.LENGTH_LONG)
                        .show();
            }
            finish();
        }
    }




    private void hideLoadingMessage() {
        if (loadingMessageSnackbar == null) {
            return;
        }

        loadingMessageSnackbar.dismiss();
        loadingMessageSnackbar = null;
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        //super.onWindowFocusChanged(hasFocus);
        if( hasFocus ) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

        if( !hasFocus ) {
            decorView.setSystemUiVisibility( uiOption );
        }



    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        onWindowFocusChanged(true);
        return super.onTouchEvent(event);
    }

    public void menuClick(View view) {
//        lv= (ListView)findViewById(R.id.lv);
//        final MyAdapter myAdapter = new MyAdapter(this);
//
//        lv.setAdapter(myAdapter);
//
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView parent, View v, int position, long id) {
//                Toast.makeText(getApplicationContext(),
//                        myAdapter.getItem(position).toString(),
//                        Toast.LENGTH_LONG).show();
//
//            }}
//        );
//        lv.setVisibility(View.GONE);
        invalidateOptionsMenu();

        PopupMenu popupMenu = new PopupMenu(this,view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        Menu menu = popupMenu.getMenu();
        inflater.inflate(R.menu.menu_main,menu);
        onWindowFocusChanged(true);
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(
                new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId()==R.id.menuItem1){
                             //en = new Locale("language_code");
                            Locale locale = Locale.US;
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());




                            Toast.makeText(getApplicationContext(),"영어선택",Toast.LENGTH_LONG).show();
                            Intent intent = getIntent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            finish();
                            startActivity(intent);


                        }
                        if(menuItem.getItemId()==R.id.menuItem2){
                            Locale locale = Locale.KOREA;
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());


                            Intent intent = getIntent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            finish();
                            startActivity(intent);
                        }


                        return false;
                    }
                }

        );


    }


    public void refresh(View view) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
    }


    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {



        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                    Thread.sleep(5000);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    }



}
