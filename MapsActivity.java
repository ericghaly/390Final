package com.example.ryan.bananafinder;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;

import java.util.List;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback, GoogleMap.OnMapClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private String mLatitudeText;
    private String mLongitudeText;
    private int bananaCount = 0;
    private DBHandler db = new DBHandler(this);
    Button settingButton;
    Button aboutButton;
    SharedPreferences settings;
    String col;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        col = settings.getString("pins","yellow");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //db = new DBHandler(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Create an instance of GoogleAPIClient.
        // This is necessary to connect to Google services such as maps
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }


        // Inserting Shop/Rows
        Log.d("Insert: ", "Inserting ..");
      //  db.addBanana(new Banana(db.getBananaCount(), 1, 0, 0));
        //db.addBanana(new Banana(db.getBananaCount(), 2, 15, 15));
        //db.addBanana(new Banana(db.getBananaCount(), 3, 20, 20));
        //db.addBanana(new Banana(db.getBananaCount(), 4, 25, 25));

        // Reading all shops
        settingButton = (Button)findViewById(R.id.settingButton);
        aboutButton = (Button)findViewById(R.id.aboutButton);

        settingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MapsActivity.this, settings.class));
            }
        });
        aboutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MapsActivity.this, about.class));
            }
        });


    }

    /**
     * Use the lifecycle methods to connect/disconnect to google services.
     * Necessary for using maps
     */
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void addMarker(LatLng myLoc){
        mMap.addMarker(new MarkerOptions().position(myLoc).title("Banana Here!!"));
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    protected void onResume(){
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        col = settings.getString("pins","yellow");
        mGoogleApiClient.reconnect();
        super.onResume();


    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            //showMissingPermissionError();
            Log.v("onResumeFragments", "Permission was denied");
            mPermissionDenied = false;
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMapClickListener(this);
        enableMyLocation();
        Log.d("Reading: ", "Reading all bananas..");
        List<Banana> bananas = db.getAllBananas();

        for (Banana banana : bananas) {
            String log = "Id: " + banana.getId() + " ,Amount: " + banana.getAmount() + " ,Long: " + banana.getLongitude() + ",Lat: " + banana.getLatitude();
            //Writing shops to log
            Log.d("Banana: : ", log);
            LatLng myLoc = new LatLng(banana.getLatitude(), banana.getLongitude());
            String amount = Integer.toString(banana.getAmount());
            switch (col) {
                case "cyan":
                    mMap.addMarker(new MarkerOptions().position(myLoc).title("Banana Here!!" + "["+amount+"]").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                    break;
                case "rose":
                    mMap.addMarker(new MarkerOptions().position(myLoc).title("Banana Here!!" + "["+amount+"]").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
                    break;
                case "yellow":
                    mMap.addMarker(new MarkerOptions().position(myLoc).title("Banana Here!!" + "["+amount+"]").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                    break;
                case "violet":
                    mMap.addMarker(new MarkerOptions().position(myLoc).title("Banana Here!!" + "["+amount+"]").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                    break;
            }
        }
    }

    /**
     * This is the callback method for the mapClickListener
     * @param point the place on the map that was clicked.  Contains latitude and longitude
     */
    @Override
    public void onMapClick(LatLng point) {
        Toast.makeText(this, "Who Knows How Many Bananas Could Be Lurking Nearby", Toast.LENGTH_SHORT).show();
        Log.v("DEBUG", "Map clicked [" + point.latitude + " / " + point.longitude + "]");


        //Do your stuff with LatLng here
        //Then pass LatLng to other activity
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE
            );
            /*
            ActivityCompat.requestPermissions(thisActivity,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            */
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
            Log.v("enableMyLocation: ", "Permission granted");
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "Banana Found!", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        Log.v("MapsActivity:", "Map click detected");
        // Add a marker in Sydney and move the camera
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return false;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        LatLng myLoc = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLoc));
        double lat = myLoc.latitude;
        double lon = myLoc.longitude;
        Intent intent = new Intent (this, SubmitBanana.class);
        intent.putExtra("Longitude", lon);
        intent.putExtra("Latitude", lat);
        startActivity(intent);
        return false;
    }

    /**
     * These next three methods are part of the interface that must be implemented to
     * connect to google services.  We're basically connecting to google to use their API
     * including maps
     * @param connectionHint
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeText = String.valueOf(mLastLocation.getLatitude());
            mLongitudeText = String.valueOf(mLastLocation.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int result) {
        Log.v("Connection Suspended: ", " " + result);
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.v("Connection Failed ", result.getErrorMessage());
    }

    /**
     *  These next methods are used to obtain permissions for using maps
     *
     **/
    @Override
    // public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
    //                                       @NonNull int[] grantResults) {
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
            Log.v("onRequestPermRslt:  ", "enabled permissions");
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
            Log.v("onRequestPermRslt:  ", "DISABLED permissions");
        }
    }

    /**
     * Checks if the result contains a {@link PackageManager#PERMISSION_GRANTED} result for a
     * permission from a runtime permissions request.
     *
     * @see android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback
     */
    public static boolean isPermissionGranted(String[] grantPermissions, int[] grantResults,
                                              String permission) {
        for (int i = 0; i < grantPermissions.length; i++) {
            if (permission.equals(grantPermissions[i])) {
                return grantResults[i] == PackageManager.PERMISSION_GRANTED;
            }
        }
        return false;
    }





}