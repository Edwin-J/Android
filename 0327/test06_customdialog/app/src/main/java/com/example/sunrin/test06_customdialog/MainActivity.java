package com.example.sunrin.test06_customdialog;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView depart;
    TextView arrival;

    TextView date_depart;
    TextView date_arrival;

    TextView tickets_depart;
    TextView tickets_arrival;

    Button search;

    String[] cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        depart = (TextView) findViewById(R.id.depart);
        arrival = (TextView) findViewById(R.id.arrival);
        date_depart = (TextView) findViewById(R.id.date_depart);
        date_arrival = (TextView) findViewById(R.id.date_arrival);
        tickets_depart = (TextView) findViewById(R.id.tickets_depart);
        tickets_arrival = (TextView) findViewById(R.id.tickets_arrival);

        search = (Button)findViewById(R.id.btn_search);

        depart.setOnClickListener(this);
        arrival.setOnClickListener(this);
        date_depart.setOnClickListener(this);
        date_arrival.setOnClickListener(this);
        tickets_depart.setOnClickListener(this);
        tickets_arrival.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.depart :
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Select DEPART");
                cities = getResources().getStringArray(R.array.city);
                builder.setItems(cities, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        showToast("Depart Selected : " + cities[i]);
                        depart.setText(cities[i]);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;

            case R.id.arrival :
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle("Select ARRIVAL");
                cities = getResources().getStringArray(R.array.city2);
                builder2.setItems(cities, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        showToast("ARRIVAL Selected : " + cities[i]);
                        arrival.setText(cities[i]);
                    }
                });
                AlertDialog dialog2 = builder2.create();
                dialog2.show();
                break;

            case R.id.date_depart :
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int date = c.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int y, int m, int d) {
                        showToast("DEPART date : " + y + "-" + m+1 + "-" + d);
                        date_depart.setText(y + "-" + m + "-" + d);
                    }
                }, year, month, date);
                datePickerDialog.show();
                break;

            case R.id.date_arrival :

            case R.id.tickets_depart:
                AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
                LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.custom_dialog, null);
                builder3.setView(view);
                builder3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText adult = view.findViewById(R.id.num1);
                        EditText children = view.findViewById(R.id.num2);
                        EditText kids = view.findViewById(R.id.num3);
                        tickets_depart.setText("Adult : "+ adult.getText() + " Children : "+ children.getText() + "Kids : "+ kids.getText());
                        showToast("Adult : "+ adult.getText() + " Children : " + children.getText() + " Kids : " + kids.getText());
                    }
                });
                AlertDialog dialog3 = builder3.create();
                dialog3.show();
                break;

            case R.id.tickets_arrival :

        }

    }

    public void showToast(String string){
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
}
