package com.example.kalyan.epic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ToggleButton;

public class problems extends AppCompatActivity {
    ToggleButton type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problems);
        type=(ToggleButton)findViewById(R.id.toggleButton);
    }
}
