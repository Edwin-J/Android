package com.example.sunrin.test08_db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    EditText input_search;
    Button btn_search;

    TextView title, singer, song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        input_search = (EditText) findViewById(R.id.search_title);
        btn_search = (Button) findViewById(R.id.search);
        title = (TextView) findViewById(R.id.title);
        singer = (TextView) findViewById(R.id.singer);
        song = (TextView) findViewById(R.id.song);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper helper = new DBHelper(getApplicationContext());
                SQLiteDatabase db = helper.getReadableDatabase();

                Cursor cursor = db.rawQuery("select * from " + helper.TABLE_NAME + " where title = " + "'" + input_search.getText().toString() + "';", null);

                cursor.moveToNext();

                title.setText(cursor.getString(1));
                singer.setText(cursor.getString(2));
                song.setText(cursor.getString(3));


            }
        });

    }
}
