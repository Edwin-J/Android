package com.example.sunrin.myapplication;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ReadFileActivity extends AppCompatActivity {

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_file);

        result = (TextView) findViewById(R.id.fileResult);

        File file = new File(Environment.getExternalStorageDirectory().
                getAbsolutePath() + "/myApp/myfile.txt");

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            StringBuffer buffer = new StringBuffer();
            String line;
            buffer.append(file.getAbsolutePath().toString() + "\n");
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
            }
            result.setText(buffer.toString());
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
