package com.example.mycouponwallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button login_button;
    TextView alert_message;
    TextView make_account;

    EditText Edit_email;
    EditText Edit_pw;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Animation alertMessageAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alert_message_animation);

        mAuth = FirebaseAuth.getInstance();
        Edit_email = (EditText) findViewById(R.id.input_email);
        Edit_pw = (EditText) findViewById(R.id.input_pw);

        login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Edit_email.getText().toString().trim();
                String pw = Edit_pw.getText().toString().trim();

                final String TAG = "Login_Activity";

                if ((email != null && !email.isEmpty()) && (pw != null && !pw.isEmpty())) {
                    mAuth.signInWithEmailAndPassword(email, pw).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(MainActivity.this, "로그인에 성공하였습니다.",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, RecyclerActivity.class); // class 변경하기
                                startActivity(intent);
                            }
                            else {
                                // If sign in fails, display a message to the user.
                                //Log.w(TAG, "signInWithEmail:failure", task.getException());
                                alert_message = (TextView) findViewById(R.id.alert_message);
                                alert_message.startAnimation(alertMessageAnim);

                            }
                        }
                    });
                }
            }
        });

        make_account = (TextView) findViewById(R.id.make_account);
        make_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });
    }
}
