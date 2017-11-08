package com.example.kacper.geopinion;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
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
import android.widget.Button;
import android.widget.Toast;
import com.example.kacper.geopinion.Model.FoursquareSearch;
import com.example.kacper.geopinion.Model.Venue;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import retrofit2.Call;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,GoogleMap.OnMarkerClickListener {
    private List<Marker> markers = new ArrayList<>();

    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location myLocation= new Location(LOCATION_SERVICE);
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap map;
    private MapFragment mMapFragment = MapFragment.newInstance(); // stworzenie mapy
    private String geoLocation = "";
    private List<Venue> item_list = new ArrayList<>();
    private boolean venueFound=false;
    private boolean cameraIsSet=false;
    private int index;
    private Button button;

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
        Log.i("Method:","ONSTART");
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
        Log.i("Method:","onStop");

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
    button=(Button)(findViewById(R.id.expressOpinionButton));
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("LOCATION CHANGED","!");

                    myLocation= new Location(LOCATION_SERVICE);
                    myLocation.set(location);
                    moveCameraToLocation();
                    startSearchingForVenues();
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
                locationManager.requestLocationUpdates("gps", 1000, 20, locationListener);

            }
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i("Method:","onConnected");

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
            startSearchingForVenues();
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
            map.setOnMarkerClickListener(this);
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
       startOpinionActivity(index);

    }





    @Override
    public void onConnectionSuspended(int i) {
        Log.i("Method:","onConnectionSuspended");

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        Log.i("Method:","onPointerCaptureChanged");

        moveCameraToLocation();
            startSearchingForVenues();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("Method:","onConnectionFailed");

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (int i=0;i<markers.size();i++){
        if (marker.equals(markers.get(i))) {

                if (Integer.valueOf(item_list.get(i).getLocation().getDistance())<1000){

                    Log.i("INFO: ","MARKER "+ item_list.get(i).getName());
                    Log.i("BUTTON ENABLED: ",String.valueOf(button.isEnabled()));
                    button.setEnabled(true);
                    Log.i("BUTTON ENABLED: ",String.valueOf(button.isEnabled()));

                    button.setText(String.format("Oceń lokal- %s", item_list.get(i).getName()));
                    index=i;
            }
                else {
                    makeToast(getString(R.string.tooFarAwayFromVenue));
                    button.setText(getString(R.string.tooFarAway));
                    button.setEnabled(false);

                }}

        }

        return false;
    }


    private void  makeProviderDisabledSnackbar(){
        Snackbar mySnackbar= Snackbar.make(findViewById(R.id.map), "Aplikacja wymaga włączenia lokalizacji",Snackbar.LENGTH_INDEFINITE);
        mySnackbar.setAction("OK",new CheckLocationListener());
        mySnackbar.show();

    }
    private void makeProviderEnabledSnackbar(){

        Snackbar mySnackbar= Snackbar.make(findViewById(R.id.map), "Lokalizacja włączona",Snackbar.LENGTH_SHORT);
        mySnackbar.show();

    }
private void moveCameraToLocation(){
        if(!cameraIsSet) {
            LatLng location = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            Log.i("LOCATION", String.valueOf(location));
            LatLng latlng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 17));
            cameraIsSet=true;
        }
}
    public class ExploreAsyncTask extends AsyncTask<Void, Void, List<Venue>> {

        public ExploreAsyncTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Venue> doInBackground(Void... voids) {
            FoursquareIntegration foursquareIntegration = FoursquareIntegration.retrofit.create(FoursquareIntegration.class);
            String client_ID = "CWPUNU2P4XDRSU1ANMXKR2F4UXEDUY1AMOM1DNBGA0HVXORI";
            String client_Secret = "LL5SY2U5G2N0B4GXS244NUHJ43SSN3UCCQX1NP5NXF52HIPV";
            String v = "20171103";

             String limit="50";
             String intent="checkin";
            final Call<FoursquareSearch> call = foursquareIntegration.requestFoursquareModel(client_ID, client_Secret,geoLocation,intent, v,limit);

            try {
               FoursquareSearch explore =  call.execute().body();
          //     FoursquareSearch explore2= call.execute().body();
             //   Log.i("RESPONSE",String.valueOf(explore.getResponse().getGroups()));
//                Log.i("RESPONSE",String.valueOf(explore.getResponse().getGroups().get(0).getItems()));

                  item_list = explore.getResponse().getVenues();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return item_list;
        }

        @Override
        protected void onPostExecute(List<Venue> item_s) {
            super.onPostExecute(item_s);
                for (int i = 0; i < item_s.size(); i++) {
                    Log.i("VENUE " + i, String.valueOf(item_list.get(i).getName()));
                    Log.i("VENUE ID",String.valueOf(item_list.get(i).getId()));
                    Log.i("DISTANCE",String.valueOf(item_list.get(i).getLocation().getDistance()));
                    double lat = item_list.get(i).getLocation().getLat();
                    double lng = item_list.get(i).getLocation().getLng();
                    LatLng venueLatLng = new LatLng(lat, lng);
                    Marker marker = map.addMarker(new MarkerOptions().position(venueLatLng).title("Marker in " + item_list.get(i).getName())); //...
                    markers.add(marker);

                }
                Log.i("MARKER:" ,markers.get(1).getTitle());

        }

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

    private void startSearchingForVenues(){
        Log.i("venueFound BEFORE:",String.valueOf(venueFound));

         if (!venueFound) {
            geoLocation = myLocation.getLatitude() + "," + myLocation.getLongitude();
            Log.i("GEOLOCATION", myLocation.getLatitude() + "," + myLocation.getLongitude());
            ExploreAsyncTask exploreAsyncTask = new ExploreAsyncTask();
            exploreAsyncTask.execute();
            venueFound=true;

         }
        Log.i("venueFound:",String.valueOf(venueFound));

    }

    private void makeToast(String text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }
    private void startOpinionActivity(int index){
        Intent intent = new Intent(this,OpinionActivity.class);
        intent.putExtra("venue_id",item_list.get(index).getId());
        intent.putExtra("venue_name",item_list.get(index).getName());
        startActivity(intent);

            }
}

