package com.example.javaview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout linearLayout = new LinearLayout(this);

        Button button = new Button(this);
        button.setText("Java Sourced Button");
        linearLayout.addView(button);

        Button button2 = new Button(this);
        button2.setText("Java Sourced Button2");
        linearLayout.addView(button2);

        setContentView(linearLayout);
    }
}
