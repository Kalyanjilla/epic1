package com.example.kalyan.epic;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class mechanicmap extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    LatLng position;
    Marker marker;
    LocationManager locationManager;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanicmap);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
        locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
        if(findViewById(R.id.reqbutton)!=null)
        {
            if(savedInstanceState!=null)
            {
                return;
            }
        }
        //HeadlinesFragment firstfrag=new HeadlinesFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        android.app.Fragment fragment = new android.app.Fragment();
        fragmentTransaction.add(R.id.reqbutton, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        getlocation();
        position = new LatLng(17.500839, 78.394974);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(position, 12);
        mMap.animateCamera(update);
        marker = mMap.addMarker(new MarkerOptions()
                .position(position)
                .alpha(0.7f)
                .flat(true)
                .title("position")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))

        );
        position = new LatLng(17.394092, 78.441504);
        update = CameraUpdateFactory.newLatLngZoom(position, 12);
        mMap.animateCamera(update);
        marker = mMap.addMarker(new MarkerOptions()
                .position(position)
                .alpha(0.7f)
                .flat(true)
                .title("position")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))

        );
        marker.setTag(0);


    }

    private void getlocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 12);
        mMap.animateCamera(update);
        mMap.setMyLocationEnabled(true);


    }
}
