package com.example.batteryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class OperationScreen extends AppCompatActivity {

    TextView t1;
    TextView t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_screen);

        t1 = findViewById(R.id.textView1);
        t2 = findViewById(R.id.textView2);
        // Receiving values into activity using intent.
        String FirstNumber = getIntent().getStringExtra("FirstNumber");
        String SecondNumber = getIntent().getStringExtra("SecondNumber");
        // Setting up received value into EditText.
        t1.setText(FirstNumber);
        t2.setText(SecondNumber);

        Double N1=Double.parseDouble(FirstNumber);
        Double N2=Double.parseDouble(SecondNumber);


        Button b1 = (Button) findViewById(R.id.add);
        Button b2 = (Button) findViewById(R.id.subtract);
        Button b3 = (Button) findViewById(R.id.multiply);
        Button b4 = (Button) findViewById(R.id.divide);

        TextView t3 = (TextView)findViewById(R.id.textView3);
        t3.setTextColor(Color.RED);
        t3.setTextSize(40);

        b1.setOnClickListener(view -> {
            Double res = N1+N2;
            t3.setText(String.valueOf(res));

        });

        b2.setOnClickListener(view -> {
            Double res = N1-N2;
            t3.setText(String.valueOf(res));

        });

        b3.setOnClickListener(view -> {
            Double res = N1*N2;
            t3.setText(String.valueOf(res));

        });
        b4.setOnClickListener(view -> {
            if (N2!=0){
                Double res = N1/N2;
                t3.setText(String.valueOf(res));

            }else{
             //handle case of cant divide by 0
            }
        });

    }
}