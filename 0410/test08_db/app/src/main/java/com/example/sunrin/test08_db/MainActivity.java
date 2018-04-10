package com.example.sunrin.test08_db;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText input_title, input_singer, input_song;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input_title = (EditText) findViewById(R.id.title);
        input_singer = (EditText) findViewById(R.id.singer);
        input_song = (EditText) findViewById(R.id.song);
        save = (Button) findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertRecord();
            }
        });

    }

    public void insertRecord() {
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into " + helper.TABLE_NAME
                + "(title, singer, song) values ("
                + "'" + input_title.getText().toString() + "', "
                + "'" + input_singer.getText().toString() + "', "
                + "'" + input_song.getText().toString() + "');");
        db.close();
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }

}
