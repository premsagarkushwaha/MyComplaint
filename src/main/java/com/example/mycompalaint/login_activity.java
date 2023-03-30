package com.example.mycompalaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class login_activity extends AppCompatActivity {

    TextView textView;

    EditText loginNumber;
    Button loginBtn;
    TextView bart;

    ProgressBar bar;

    FirebaseAuth mAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            String s1 = "fH7fY9lYRmPgTednHKSaQxsqJ1o2";
//            String s2 = currentUser.getUid().toString();
//            if(s1.equals(s2))
//            {
//                mAuth.signOut();
//                Intent intent = new Intent(getApplicationContext(),login_activity.class);
//                startActivity(intent);
//                finish();
//
//            }
//            else {
//
//                FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid().toString()).child("pin").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.exists())
//                        {
//                            Intent intent = new Intent(getApplicationContext(),pinVerification.class);
//                            startActivity(intent);
//                            finish();
//
//                        }
//                        else
//                        {
//                            Intent intent = new Intent(getApplicationContext(),pinSetup.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//
//            }
//
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textView = findViewById(R.id.registerNow);
        loginNumber = findViewById(R.id.login_number);
        loginBtn = findViewById(R.id.login_btn);

        bar = findViewById(R.id.loginpb);

        mAuth = FirebaseAuth.getInstance();

        bart = findViewById(R.id.sendotptxt);



        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),sign_up_activity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loginNumber.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter Mobile Number",Toast.LENGTH_SHORT).show();

                } else if (loginNumber.getText().toString().length()!=10)
                {
                    Toast.makeText(getApplicationContext(),"Enter Valid Mobile Number",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loginBtn.setVisibility(View.GONE);
                    bar.setVisibility(View.VISIBLE);
                    bart.setVisibility(View.VISIBLE);
                    sendotp();
//                    loginBtn.setVisibility(View.GONE);
//                    bar.setVisibility(View.VISIBLE);
                }
            }

        });
    }

    private void sendotp()
    {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e)
            {
                Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token)
            {
                Intent intent = new Intent(getApplicationContext(),otp_verify_activity.class);
                intent.putExtra("mnumber",loginNumber.getText().toString());
                intent.putExtra("verificationID",verificationId);
                startActivity(intent);

            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+loginNumber.getText().toString())       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }
}