package com.example.sunrin.myapplication;

import android.Manifest;
import android.content.Context;
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
  }

    @Override
    public void onClick(View v) {
        if(v==contactsBtn) {
            //연락처 앱 연동
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, 10);
        } else if(v==cameraDataBtn) {
            //카메라 앱 연동
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 30);
        } else if(v==cameraFileBtn) {
            //카메라 앱 연동 - 외부저장장치 저장하기
            //외부저장장치 권한
            if(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED) {
                //허락되어 있다면
                //외부저장장치 디렉토리 생성
                String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/myApp";
                File dir = new File(dirPath);
                if(!dir.exists()) {
                    dir.mkdir();
                }
                //파일 생성
                try {
                    filePath = File.createTempFile("IMG", ".jpg", dir);
                    if(!filePath.exists()){
                        filePath.createNewFile();
                    }
                    //api 24이전
                    //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(filepath));
                    Uri photoUri = FileProvider.getUriForFile(this,
                            "com.example.myapplication.provider", filePath);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                    startActivityForResult(intent, 40);

                }catch(Exception e){
                    e.printStackTrace();

                }
            } else {
                //허락되어 있지 않다면 권한 요청
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);

            }
        } else if(v == resultImageView){
            //사진 보여주기
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.example.myapplication.provider", filePath);
            intent.setDataAndType(photoURI, "image/*");
            //외부 앱에서 우리 데이터를 이용
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } else if (v == galleryBtn) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 20);
        } else if (v == mapBtn) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5662952, 126.9779451"));
            startActivity(intent);
        } else if (v == browserBtn) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.seoul.go.kr"));
            startActivity(intent);
        } else if (v == callBtn) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:02-120"));
                startActivity(intent);
            } else {
                ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CALL_PHONE },
                        100);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10 && resultCode==RESULT_OK) {
            String result = data.getDataString();
            resultView.setText(result);
            //선택한 연락처 상세화면으로 보여주기
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(ContactsContract.Contacts.CONTENT_URI+"/"+1));
            startActivity(intent);
        } else if(requestCode== 30 && resultCode==RESULT_OK) {
            //카메라 이미지 가져오기
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            resultImageView.setImageBitmap(bitmap);
        } else if(requestCode == 40 && resultCode==RESULT_OK){
            if(filePath != null){
                Bitmap bitmap = BitmapFactory.decodeFile(filePath.getAbsolutePath());
                resultImageView.setImageBitmap(bitmap);
            }
        } else if (requestCode == 20 && resultCode == RESULT_OK) {
            String result = data.getDataString();
            resultView.setText(result);

            // 선택한 이미지 바로 보기
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri photoUri = Uri.parse(result);
            intent.setDataAndType(photoUri, "image/*");
            // 권한 요청
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        }
    }
}
