package com.example.test05;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class test5_widget extends AppCompatActivity {

    EditText input_id;
    EditText input_name;
    EditText input_address;

    RadioGroup input_age;

    CheckBox checkBox;

    Button save;
    Button close;

    TextView result;

    String id;
    String name;
    String address;
    String age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test5_widget);

        input_id = (EditText)findViewById(R.id.input_id);
        input_name = (EditText)findViewById(R.id.input_name);
        input_address = (EditText)findViewById(R.id.input_address);
        input_age = (RadioGroup) findViewById(R.id.age);
        checkBox = (CheckBox)findViewById(R.id.checkbox);
        save = (Button)findViewById(R.id.btn_save);
        close = (Button)findViewById(R.id.btn_close);
        result = (TextView)findViewById(R.id.text_result);

        id = input_id.getText().toString();
        name = input_name.getText().toString();
        address = input_address.getText().toString();
        input_age.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.age_10)
                    age = "10대";
                else if (i == R.id.age_20)
                    age = "20대";
                else if (i == R.id.age_30)
                    age = "30대";
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id = input_id.getText().toString();
                name = input_name.getText().toString();
                address = input_address.getText().toString();

                if (id == null)
                    Toast.makeText(getApplicationContext(), "아이디를 입력하여 주세요.", Toast.LENGTH_LONG).show();

                else if (name == null)
                    Toast.makeText(getApplicationContext(), "이름을 입력하여 주세요.", Toast.LENGTH_LONG).show();

                else if (address == null)
                    Toast.makeText(getApplicationContext(), "주소를 입력하여 주세요.", Toast.LENGTH_LONG).show();

                else if (age == null)
                    Toast.makeText(getApplicationContext(), "나이를 체크하여 주세요.", Toast.LENGTH_LONG).show();

                else if (checkBox.isChecked() == false)
                    Toast.makeText(getApplicationContext(), "약관을 확인하여주세요.", Toast.LENGTH_LONG).show();

                else
                    result.setText(id + " " + name + " " + address + " " + age + "로 저장되었습니다.");
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
