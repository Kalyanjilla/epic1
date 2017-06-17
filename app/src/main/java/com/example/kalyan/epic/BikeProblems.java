package com.example.kalyan.epic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BikeProblems extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner bikeprblme;
    TextView tv,price;
    Button retprice,mechanic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_problems);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv=(TextView)findViewById(R.id.tv);
        price=(TextView)findViewById(R.id.price);
        price.setEnabled(false);
        retprice=(Button)findViewById(R.id.retprice);
        mechanic=(Button)findViewById(R.id.reqmechanic);
        mechanic.setEnabled(false);
        bikeprblme=(Spinner)findViewById(R.id.bikeprblm);
        ArrayAdapter adapter=ArrayAdapter.createFromResource(this,R.array.bikeprblm,android.R.layout.simple_spinner_item);
        bikeprblme.setAdapter(adapter);
        bikeprblme.setOnItemSelectedListener(this);
        retprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setEnabled(true);
                price.setEnabled(true);
                tv.setText("Estimated prices");
                price.setText("54");
                mechanic.setEnabled(true);
            }
        });
        mechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               callmechanic();
            }
        });

    }


    public void callmechanic()
    {
        Intent i = new Intent(this,com.example.kalyan.epic.mechanicmap.class);
        startActivity(i);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView tv=(TextView)view;
        Toast.makeText(getBaseContext(),tv.getText()+"is selected",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // MenuInflater menuInflater=getMenuInflater();
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_contact:
                // Single menu item is selected do something
                // Ex: launching new activity/screen or show alert message
                Toast.makeText(getApplicationContext(), "Contact is Selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menu_help:
                Toast.makeText(getApplicationContext(), "Help is Selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menu_about:
                Toast.makeText(getApplicationContext(), "About is Selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menu_share:
                Toast.makeText(getApplicationContext(), "Report is Selected", Toast.LENGTH_SHORT).show();
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
}
