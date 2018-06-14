package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button contactsBtn;
    Button cameraDataBtn;
    Button cameraFileBtn;
    Button speechBtn;
    Button mapBtn;
    Button browserBtn;
    Button callBtn;
    Button galleryBtn;

    TextView resultView;
    ImageView resultImageView;

    File filePath;

    int reqWidth;
    int reqHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultView=(TextView)findViewById(R.id.resultView);
        contactsBtn=(Button)findViewById(R.id.btn_contacts);
        cameraDataBtn=(Button)findViewById(R.id.btn_camera_data);
        cameraFileBtn=(Button)findViewById(R.id.btn_camera_file);
        speechBtn=(Button)findViewById(R.id.btn_speech);
        mapBtn=(Button)findViewById(R.id.btn_map);
        browserBtn=(Button)findViewById(R.id.btn_browser);
        callBtn=(Button)findViewById(R.id.btn_call);
        resultImageView=(ImageView)findViewById(R.id.resultImageView);
        galleryBtn=(Button)findViewById(R.id.btn_gallery);

        contactsBtn.setOnClickListener(this);
        cameraDataBtn.setOnClickListener(this);
        cameraFileBtn.setOnClickListener(this);
        speechBtn.setOnClickListener(this);
        mapBtn.setOnClickListener(this);
        browserBtn.setOnClickListener(this);
        callBtn.setOnClickListener(this);
        resultImageView.setOnClickListener(this);
        galleryBtn.setOnClickListener(this);

        //reqWidth = getResources().getDimensionPixelSize(R.dimen.request_image_width);
        //reqHeight = getResources().getDimensionPixelSize(R.dimen.request_image_height);
    }

    @Override
    public void onClick(View v) {
        if (v == contactsBtn) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, 10);
        } else if (v == cameraDataBtn) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 30);
        } else if (v == cameraFileBtn) {
            // 권한 체크
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myApp" ;
                File dir = new File(dirPath);
                if (!dir.exists())
                    dir.mkdir();
                try {
                    filePath = File.createTempFile("IMG", ".jpg", dir);
                    if (!filePath.exists())
                        filePath.createNewFile();
                    Uri photoUri = FileProvider.getUriForFile(this, "com.example.myapplication.provider", filePath);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(intent, 40);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                // 권한 없을 시 권한 주기
                ActivityCompat.requestPermissions(this,
                        new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 30);
            }
        } else if (v == resultImageView) {
            // 이미지 크게 보기
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri photoUri = FileProvider.getUriForFile(this, "com.example.myapplication.provider", filePath);
            intent.setDataAndType(photoUri, "image/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10 && resultCode == RESULT_OK) {
            String result = data.getDataString();
            resultView.setText(result);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(ContactsContract.Contacts.CONTENT_URI + "/" + 1));
            startActivity(intent);
        } else if (requestCode == 30 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            resultImageView.setImageBitmap(bitmap);
        } else if (requestCode == 40 && resultCode == RESULT_OK) {
            if (filePath != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(filePath.getAbsolutePath());
                resultImageView.setImageBitmap(bitmap);
            }
        }



    }
}
