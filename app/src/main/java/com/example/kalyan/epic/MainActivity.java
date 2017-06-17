package com.example.kalyan.epic;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    GoogleApiClient mGoogleapiclient;
    LocationManager locationManager;
    Context mContext;
    private ShareActionProvider mShareActionProvider;


    Intent menui;
    LatLng hyderabad;

    private GoogleMap mMap;

    private GoogleApiClient client;
    private Location location;
    private Switch car,bike;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Button menubutton = (Button) findViewById(R.id.menubutton);
        menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });
        Button locb = (Button) findViewById(R.id.setlocation);
        locb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setlocation();
            }
        });

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        car=(Switch)findViewById(R.id.car_switch);
        bike=(Switch)findViewById(R.id.bike_switch);

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (car.isChecked())
                bike.setEnabled(false);
                else
                    bike.setEnabled(true);
            }
        });
        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bike.isChecked())
                    car.setEnabled(false);
                else
                    car.setEnabled(true);
            }
        });



    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in hyderabad, Australia, and move the camera.
        //locate();
        mGoogleapiclient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleapiclient.connect();
    }


    LocationRequest mlocationRequest;

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mlocationRequest = LocationRequest.create();
        mlocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mlocationRequest.setInterval(1000);
        mlocationRequest.setFastestInterval(1000);

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
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleapiclient, mlocationRequest,(LocationListener ) this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onLocationChanged(Location location) {
        if (location == null) {
            Toast.makeText(this, "cant get current location", Toast.LENGTH_LONG).show();
        } else {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 15);
            mMap.animateCamera(update);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "cant get current location", Toast.LENGTH_LONG).show();

                return;
            } else {
                mMap.setMyLocationEnabled(true);
            }
        }
    }


    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public void setlocation() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


        LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 15);
        mMap.animateCamera(update);

    }



    public void call() {

        if (bike.isChecked()) {
            Intent intent = new Intent(this, BikeProblems.class);
            Toast.makeText(getApplicationContext(), "Vehilce type bike is selected", Toast.LENGTH_SHORT).show();

            startActivity(intent);
        } else if (car.isChecked()) {
            Toast.makeText(getApplicationContext(), "Vehicle type car is selected", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, CarProblems.class);
            startActivity(intent);

        } else {
            Toast.makeText(getApplicationContext(), "Please select your vehicle", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

       // MenuInflater menuInflater=getMenuInflater();
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem share=menu.findItem(R.id.menu_share);
        //android.support.v4.view.MenuItemCompat menuItemCompat=menu.findItem(R.id.menu_share);
        mShareActionProvider=(ShareActionProvider) MenuItemCompat.getActionProvider(share);

        return true;
    }

    public void shareText() {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBodyText = "Dowload the app E-mechanic and get therir service on your way";
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(intent, "Choose sharing method"));
        setShareIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {

            case R.id.menu_contact:

                Toast.makeText(getApplicationContext(), "Contact is Selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menu_help:
                Toast.makeText(getApplicationContext(), "Help is Selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menu_about:
                menui=new Intent(this,about.class);
                Toast.makeText(getApplicationContext(), "About is Selected", Toast.LENGTH_SHORT).show();
                startActivity(menui);
                return true;

            case R.id.menu_share:
                shareText();

                Toast.makeText(getApplicationContext(), "Share is Selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menu_user:
                Toast.makeText(getApplicationContext(), "User is Selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menu_history:
                Toast.makeText(getApplicationContext(), "History is Selected", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setShareIntent(Intent shareInent){
        if(mShareActionProvider!=null)
            mShareActionProvider.setShareIntent(shareInent);
    }



}
