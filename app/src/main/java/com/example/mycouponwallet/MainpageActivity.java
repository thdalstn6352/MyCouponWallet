package com.example.mycouponwallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainpageActivity extends AppCompatActivity {

    private Button createQRBtn;
    private Button scanQRBtn;
    private ImageView coupons[] = new ImageView[10];
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mReference = firebaseDatabase.getReference();
    private ChildEventListener mChild;

    public int count;

    //private ListView listView;
    //private ArrayAdapter<String> adapter;
    //List<Object> Array = new ArrayList<Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        createQRBtn = (Button) findViewById(R.id.createQR);
        scanQRBtn = (Button) findViewById(R.id.scanQR);
        mAuth = FirebaseAuth.getInstance();
        initDatabase();

        createQRBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainpageActivity.this, CreateQR.class); // class 바꾸기
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

//        coupons[0].setTag("empty");
//        coupons[1].setTag("empty");
//        coupons[2].setTag("empty");
//        coupons[3].setTag("empty");
//        coupons[4].setTag("empty");
//        coupons[5].setTag("empty");
//        coupons[6].setTag("empty");
//        coupons[7].setTag("empty");
//        coupons[8].setTag("empty");
//        coupons[9].setTag("empty");


        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String uid = user.getUid();
            mReference = firebaseDatabase.getReference().child("users").child(uid).child("cafeName").child("Starbucks"); // 변경값을 확인할 child 이름
            mReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //adapter.clear();
                    //FirebaseUser user = mAuth.getCurrentUser();
                    //String uid = user.getUid();
                    for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                        String msg2 = messageData.getValue().toString();
                        count = Integer.parseInt(msg2);
                        if(count >= 10) {
                            count = count % 10;
                            for(int i = 0; i < count; i++)
                            {
                                coupons[i].setImageDrawable(getResources().getDrawable(R.drawable.coupon_normal));
                            }
                        }
                        else {
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

    private void initDatabase() {
        mReference = firebaseDatabase.getReference("log");
        mReference.child("log").setValue("check");

        mChild = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mReference.addChildEventListener(mChild);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mReference.removeEventListener(mChild);
    }


}