package com.example.sunrin.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    boolean FILE_READE_PERMISSION, FILE_WRITE_PERMISSION;

    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.content);
        button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            FILE_READE_PERMISSION = true;
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            FILE_WRITE_PERMISSION = true;
        }

        if (!FILE_READE_PERMISSION || !FILE_WRITE_PERMISSION) {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    100);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                FILE_READE_PERMISSION = true;
            if (grantResults[1] == PackageManager.PERMISSION_GRANTED)
                FILE_WRITE_PERMISSION = true;
        }
    }

    @Override
    public void onClick(View view) {
        if (FILE_READE_PERMISSION && FILE_WRITE_PERMISSION) {
            Toast.makeText(this,
                    "Read/Write Permission Allowed", Toast.LENGTH_LONG).show();

                try {

                    String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myApp";

                    File dir = new File(dirPath);
                    if(!dir.exists())
                        dir.mkdir();

                    File dirFile = new File(dir + "/myFile.txt");
                    if (!dirFile.exists())
                        dirFile.createNewFile();

                    FileWriter fileWriter = new FileWriter(dirFile, true);
                    fileWriter.write(editText.getText().toString());

                    fileWriter.flush();
                    fileWriter.close();

                    Intent intent = new Intent(this, ReadFileActivity.class);
                    startActivity(intent);

                } catch (IOException e) {
                    e.printStackTrace();
                }

        } else {
            Toast.makeText(this,
                    "Read/Write Permission Denied", Toast.LENGTH_LONG).show();
        }
    }
}
