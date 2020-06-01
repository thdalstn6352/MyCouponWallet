package com.example.mycouponwallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycouponwallet.model.CafeModel;
import com.example.mycouponwallet.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class AccountActivity extends AppCompatActivity {

    Button sign_button;
    EditText name_input;
    EditText email_input;
    EditText pw_input;
    EditText pw_reinput;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    ImageView setImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)  ;
        setContentView(R.layout.activity_account);

        name_input = (EditText) findViewById(R.id.sign_up_name);
        email_input = (EditText) findViewById(R.id.sign_up_email);
        pw_input = (EditText) findViewById(R.id.sign_up_pw);
        pw_reinput = (EditText) findViewById(R.id.pw_rewrite);
        setImage = (ImageView) findViewById(R.id.setImage);
        mAuth = FirebaseAuth.getInstance();
        database =  FirebaseDatabase.getInstance();
        pw_reinput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(pw_input.getText().toString().equals(pw_reinput.getText().toString())) {
                    setImage.setImageResource(R.drawable.correct);
                }
                else {

                    setImage.setImageResource(R.drawable.error);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        sign_button = (Button) findViewById(R.id.sign_button);
        sign_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = email_input.getText().toString().trim();
                String pw = pw_input.getText().toString().trim();
                final String name = name_input.getText().toString().trim();

                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    Toast.makeText(AccountActivity.this,"이메일 형식이 아닙니다",Toast.LENGTH_SHORT).show();
                }

                if ((email != null && !email.isEmpty()) && (pw != null && !pw.isEmpty())) {

                    mAuth.createUserWithEmailAndPassword(email, pw)
                            .addOnCompleteListener(AccountActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    UserModel userModel = new UserModel();
                                    CafeModel cafeModel = new CafeModel();

                                    if (task.isSuccessful()) {
                                        userModel.userName = name;
                                        userModel.userEmail = email;

                                        String uid = task.getResult().getUser().getUid();
                                        database.getReference().child("users").child(uid).setValue(userModel);
                                        database.getReference().child("users").child(uid).child("cafeName").child("Ediya").child("Stamp").child("number").setValue(0);
                                        database.getReference().child("users").child(uid).child("cafeName").child("Angel in us").child("Stamp").child("number").setValue(0);
                                        database.getReference().child("users").child(uid).child("cafeName").child("Blue bottle").child("Stamp").child("number").setValue(0);
                                        database.getReference().child("users").child(uid).child("cafeName").child("Hollys").child("Stamp").child("number").setValue(0);
                                        database.getReference().child("users").child(uid).child("cafeName").child("Starbucks").child("Stamp").child("number").setValue(0);
                                        database.getReference().child("users").child(uid).child("cafeName").child("TomNToms").child("Stamp").child("number").setValue(0);
                                        database.getReference().child("users").child(uid).child("cafeName").child("빽다방").child("Stamp").child("number").setValue(0);

                                        Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "회원가입이 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });




    }
}
