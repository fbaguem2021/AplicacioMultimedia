package com.example.aplicaciomultimedia.classes;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.widget.Toast;

import com.example.aplicaciomultimedia.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MyAddress {
    private MainActivity activity;
    public Location location;
    private String address;
    private Uri uri;

    public MyAddress(MainActivity activity) {
        this.activity = activity;
    }

    public String getAddress() {
        return this.address;
    }

    public Uri getUri() {
        return this.uri;
    }

    public Location getLocation() {
        return this.location;
    }

    public MyAddress generateLocation() {
        FusedLocationProviderClient client =
                LocationServices.getFusedLocationProviderClient(activity);
        try {
            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    MyAddress.this.location = location;
                    MyAddress.this.address = getAddresFromLocation();
                }
            });
        } catch (SecurityException ex) {
            Toast.makeText(activity, "No se ha podido obtener la ubicación actual del dispositivo", Toast.LENGTH_SHORT).show();
        }
        return this;
    }

//    public MyAddress generateLocation() {
//        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
//        {
//            this.locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
//            this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, activity);
//            this.location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//        }
//        return this;
//    }

    public MyAddress generateUri() {
        if (location != null) {
            this.uri = Uri.parse("geo:0,0?q=" + location.getLatitude() + "," + location.getLongitude() + " (My Location)");
        }
        //this.mapsIntentUri = Uri.parse("google.streetview:cbll=" + location.getLatitude() + "," + location.getLongitude());
        //this.uri = Uri.parse("google.streetview:cbll=46.414382,10.013988");
        return this;
    }

    private String getAddresFromLocation() {
        String address = null;

        try {
            Geocoder geocoder = new Geocoder(this.activity, Locale.getDefault());

            List<Address> addresses = geocoder.getFromLocation(this.location.getLatitude(), this.location.getLongitude(), 1);
            address = addresses.get(0).getAddressLine(0);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return address;
    }



}
