package com.example.mycouponwallet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Button;
import android.widget.ImageView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class AnotherActivity extends AppCompatActivity {

    TextView mTitleTv, mDesTv;
    ImageView mImageIv;
    private Button createQRBtn;
    private Button scanQRBtn;
    private ImageView coupons[] = new ImageView[10];
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mReference = firebaseDatabase.getReference();
    private String uid;
    public int count;
    StringBuffer temp = new StringBuffer();
    Random rnd = new Random();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        ActionBar actionBar = getSupportActionBar();

        mTitleTv = findViewById(R.id.title);
        mDesTv = findViewById(R.id.description);
        mImageIv = findViewById(R.id.imageView);

        createQRBtn = (Button) findViewById(R.id.createQR);
        scanQRBtn = (Button) findViewById(R.id.scanQR);
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        final String mTitle = intent.getStringExtra("iTitle");
        //String mDescription = intent.getStringExtra("iDesc");

        final byte[] mBytes = getIntent().getByteArrayExtra("iImage");
        Bitmap bitmap = BitmapFactory.decodeByteArray(mBytes, 0, mBytes.length);

        actionBar.setTitle(mTitle);

        mTitleTv.setText(" 스탬프 현황");
        mImageIv.setImageBitmap(bitmap);

        createQRBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(AnotherActivity.this, CreateQR.class); // class 바꾸기
                intent.putExtra("cafe_name", mTitle);
                intent.putExtra("logo", mBytes);
                startActivity(intent);
            }
        });

        scanQRBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(AnotherActivity.this, CouponBook.class);
                startActivity(intent);
            }
        });

        coupons[0] = (ImageView) findViewById(R.id.coupon1);
        coupons[1] = (ImageView) findViewById(R.id.coupon2);
        coupons[2] = (ImageView) findViewById(R.id.coupon3);
        coupons[3] = (ImageView) findViewById(R.id.coupon4);
        coupons[4] = (ImageView) findViewById(R.id.coupon5);
        coupons[5] = (ImageView) findViewById(R.id.coupon6);
        coupons[6] = (ImageView) findViewById(R.id.coupon7);
        coupons[7] = (ImageView) findViewById(R.id.coupon8);
        coupons[8] = (ImageView) findViewById(R.id.coupon9);
        coupons[9] = (ImageView) findViewById(R.id.coupon10);



        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            uid = user.getUid();
            mReference = firebaseDatabase.getReference().child("users").child(uid).child("cafeName").child(mTitle).child("Stamp"); // 변경값을 확인할 child 이름
            mReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //adapter.clear();
                    //FirebaseUser user = mAuth.getCurrentUser();
                    //String uid = user.getUid();
                    for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                        String msg2 = messageData.getValue().toString();
                        count = Integer.parseInt(msg2);
                        mDesTv.setText(count%10 + " / 10개   ");
                        temp.setLength(0);

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
                        }
                        String serial_num = temp.toString();

                        if (count >= 10) {
                            count = count % 10;
                            firebaseDatabase.getReference().child("users").child(uid).child("cafeName").child(mTitle).child("Stamp").child("number").setValue(count);
                            firebaseDatabase.getReference().child("users").child(uid).child("cafeName").child(mTitle).child("Coupon").child("SerialNum").push().setValue(serial_num);
                            temp.setLength(0); //버퍼 초기화

                            for (int i = 0; i < 10; i++) {
                                coupons[i].setImageDrawable(getResources().getDrawable(R.drawable.coupon_normal));
                            }
                            for (int i = 0; i < count; i++) {
                                coupons[i].setImageDrawable(getResources().getDrawable(R.drawable.coffee_stamp));
                            }
                        } else {
                            for (int i = 0; i < count; i++) {
                                coupons[i].setImageDrawable(getResources().getDrawable(R.drawable.coffee_stamp));
                            }
                        }// child 내에 있는 데이터만큼 반복합니다.
                    }
                    //adapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }

    }
