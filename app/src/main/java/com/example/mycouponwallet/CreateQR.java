package com.example.mycouponwallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.widget.TextView;

import com.example.mycouponwallet.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


import java.io.UnsupportedEncodingException;
import java.util.Random;

public class CreateQR extends AppCompatActivity {
    private ImageView iv;
    private ImageView logo;
    //private TextView text;
    private Button bt;
    private String text;
    StringBuffer temp = new StringBuffer();
    Random rnd = new Random();

    FirebaseAuth mAuth;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_qr);

        StringBuilder textToSend = new StringBuilder(); //qr코드에 담을 데이터
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("스탬프 적립");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String cafe_name = bundle.getString("cafe_name");


        byte[] mBytes = bundle.getByteArray("logo");
        Bitmap logo_img = BitmapFactory.decodeByteArray(mBytes, 0, mBytes.length);


        for (int i = 0; i < 20; i++) {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
                case 0:
                    // a-z
                    temp.append((char) ((int) (rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    // A-Z
                    temp.append((char) ((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    // 0-9
                    temp.append((rnd.nextInt(10)));
                    break;
            }
        } //qr코드 고유 시리얼넘버 지정

        iv = (ImageView)findViewById(R.id.qrcode);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        bt = (Button) findViewById(R.id.coupon_bt);
        logo = (ImageView) findViewById(R.id.imageView);

        logo.setImageBitmap(logo_img);

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            //String Serial_num = new String(temp); //시리얼넘버
            String uid = user.getUid(); //사용자uid
            textToSend.append(uid + "/" + cafe_name);
            text = textToSend.toString();
            try {
                text = new String(text.getBytes("UTF-8"), "ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            iv.setImageBitmap(bitmap);
        }catch (Exception e){}

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateQR.this, RecyclerActivity.class);
                startActivity(intent);
            }
        });




    }
}