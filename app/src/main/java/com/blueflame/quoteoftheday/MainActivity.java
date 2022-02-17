package com.blueflame.quoteoftheday;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button nextWisdomButton = findViewById(R.id.button);
        TextView wisdomLabel = findViewById(R.id.textView);

        nextWisdomButton.setOnClickListener(view -> wisdomLabel.setText(PizzaWisdomSelector.getNextWisdom()));
    }
}