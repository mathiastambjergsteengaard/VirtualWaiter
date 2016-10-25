package com.example.mathias.virtualwaiter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Method;
import java.util.List;

public class Loading extends AppCompatActivity implements LocationListener {
    LocationManager locationManager;
    Location location;
    private int RADIUS = 2500;
    double latitude = 0;
    double longitude = 0;
    private static final String GOOGLE_API_KEY = "AIzaSyBFAURxeUfhIeRI71GkNrA3wkrmkAqIBEs";
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 144;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        TextView loadingTextView = (TextView) findViewById(R.id.loadingText);
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            if (extra.get("state").equals("restaurant")) {
                loadingTextView.setText(getResources().getString(R.string.loading_nearby_restaurants));
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            this.MY_PERMISSION_ACCESS_FINE_LOCATION);
                    return;
                }
                if ((!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))) {
                    Toast.makeText(this, "no location service", Toast.LENGTH_SHORT).show();
                    finish();
                }
                Criteria criteria = new Criteria();
                String bestProvider = locationManager.getBestProvider(criteria, true);
                location = locationManager.getLastKnownLocation(bestProvider);
                if (location != null) {
                    onLocationChanged(location);
                }else{
                    locationManager.requestLocationUpdates(bestProvider, 0, 0, this);
                }
            } else if (extra.get("state").equals("menu")) {
                String findId = extra.getString("restaurantId");
                loadingTextView.setText(getResources().getString(R.string.loading_menu));
                getMenu(findId);
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Loading.MY_PERMISSION_ACCESS_FINE_LOCATION) {
            Criteria criteria = new Criteria();
            String bestProvider = locationManager.getBestProvider(criteria, true);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        Loading.MY_PERMISSION_ACCESS_FINE_LOCATION);
                return;
            }
            location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                onLocationChanged(location);

            }
            locationManager.requestLocationUpdates(bestProvider, 10000, 0, this);
        }
    }

    public void getMenu(String id) {
        Object[] toPass = new Object[2];
        toPass[0] = id;
        toPass[1] = this;
        MenuLoading menuLoading = new MenuLoading();
        menuLoading.execute(toPass);
    }

    public void getNearRestaurants() {
        String type = "restaurant";
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + RADIUS);
        googlePlacesUrl.append("&types=" + type);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + GOOGLE_API_KEY);
        GooglePlacesRead googlePlacesReadTask = new GooglePlacesRead();
        Object[] toPass = new Object[4];
        toPass[0] = latitude;
        toPass[1] = longitude;
        toPass[2] = googlePlacesUrl.toString();
        toPass[3] = this;
        googlePlacesReadTask.execute(toPass);
    }


    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    Loading.MY_PERMISSION_ACCESS_FINE_LOCATION);
            return;
        }
        locationManager.removeUpdates(this);
        getNearRestaurants();

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
