package com.example.kacper.geopinion;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import static android.content.Context.LOCATION_SERVICE;

public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback {
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location myLocation= new Location(LOCATION_SERVICE);
    private GoogleMap googleMap;
    Snackbar mySnackbar;
    private MapFragment mMapFragment = MapFragment.newInstance(); // stworzenie mapy
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(getApplicationContext(), location.getLongitude()+" "+location.getLatitude(), Toast.LENGTH_SHORT).show();
                myLocation.set(location);
              onMapChanged(googleMap,location);

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
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) { //requestPermissions wymaga od nas conajmniej api23. W przeciwnym razie, nie są wymagane dodatkowe uprawnienia.
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
        FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map_container, mMapFragment);
        fragmentTransaction.commit();
        mMapFragment.getMapAsync(this);


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){

            case 10:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates("gps", 1000, 10, locationListener);

                }
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
                 LatLng sydney = new LatLng(-33.852, 151.211);
              googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
              googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    //        myLocation=googleMap.getMyLocation();

        } else {
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
        Log.i("LOCATION:",String.valueOf(myLocation.getLongitude())+String.valueOf(myLocation.getLatitude()));


    }
        public void onMapChanged(GoogleMap googleMap, Location location){

            LatLng marker= new LatLng(location.getLatitude(),location.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(marker).title("dupa"));
             googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
        }
        public void getLocationButton(){
            locationManager.requestLocationUpdates("gps", 1000, 10, locationListener);

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
    public void  makeProviderDisabledSnackbar(){
        Snackbar mySnackbar= Snackbar.make(findViewById(R.id.map_container), "Aplikacja wymaga włączenia lokalizacji",Snackbar.LENGTH_INDEFINITE);
        mySnackbar.setAction("OK",new CheckLocationListener());
        mySnackbar.show();

    }
    public void makeProviderEnabledSnackbar(){

        Snackbar mySnackbar= Snackbar.make(findViewById(R.id.map_container), "Lokalizacja włączona",Snackbar.LENGTH_SHORT);
        mySnackbar.show();

    }

}
