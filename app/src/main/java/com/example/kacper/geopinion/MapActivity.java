package com.example.kacper.geopinion;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.content.Context.LOCATION_SERVICE;

public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location myLocation= new Location(LOCATION_SERVICE);
    Snackbar mySnackbar;
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap map;
    private MapFragment mMapFragment = MapFragment.newInstance(); // stworzenie mapy

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("LOCATION CHANGED","!");

                    myLocation= new Location(LOCATION_SERVICE);
                    myLocation.set(location);
                    moveCameraToLocation();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                makeProviderEnabledSnackbar();
            }

            @Override
            public void onProviderDisabled(String provider) {
                makeProviderDisabledSnackbar();
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //requestPermissions wymaga od nas conajmniej api23. W przeciwnym razie, nie są wymagane dodatkowe uprawnienia.
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, 10);
                return;
            } else {
                locationManager.requestLocationUpdates("gps", 1000, 10, locationListener);

            }
        }
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map_container, mMapFragment);
        fragmentTransaction.commit();
        mMapFragment.getMapAsync(this);


    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("NO PERMISSIONS","!");

            /// / TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        myLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        if (myLocation != null) {
            Log.i("LOCATION ","GRANTED!");

            moveCameraToLocation();
        }
        else{

            Log.i("NO LAST KNOW  LOCATION","!");

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates("gps", 1000, 10, locationListener);

                }
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);

            map=googleMap;

            //        myLocation=googleMap.getMyLocation();

        } else {
            Log.i("ERROR","NO PERRMISIONS");
            // Show rationale and request permission.
        }
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        //     LatLng sydney = new LatLng(-33.852, 151.211);
        //  googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //  googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }


    public void onButtonClick(View view) {
        getLocationButton();
        Log.i("LOCATION:", String.valueOf(myLocation.getLongitude()) + String.valueOf(myLocation.getLatitude()));


    }



    private void getLocationButton() {
        locationManager.requestLocationUpdates("gps", 1000, 10, locationListener);

    }




    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
            moveCameraToLocation();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public class CheckLocationListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent= new Intent (Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
            if(!locationManager.isProviderEnabled("gps")){
                makeProviderDisabledSnackbar();
            }
        }

    }
    private void  makeProviderDisabledSnackbar(){
        Snackbar mySnackbar= Snackbar.make(findViewById(R.id.map_container), "Aplikacja wymaga włączenia lokalizacji",Snackbar.LENGTH_INDEFINITE);
        mySnackbar.setAction("OK",new CheckLocationListener());
        mySnackbar.show();

    }
    private void makeProviderEnabledSnackbar(){

        Snackbar mySnackbar= Snackbar.make(findViewById(R.id.map_container), "Lokalizacja włączona",Snackbar.LENGTH_SHORT);
        mySnackbar.show();

    }
private void moveCameraToLocation(){
    LatLng location= new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
    Log.i("LOCATION",String.valueOf(location));
    LatLng latlng= new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,17));

}
}
