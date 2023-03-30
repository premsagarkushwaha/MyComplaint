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
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class sign_up_activity extends AppCompatActivity {

    EditText signupNumber;
    Button signupBtn;
    TextView switchlog,otptxt;

    ProgressBar bar;

    FirebaseAuth mAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signupNumber = findViewById(R.id.signup_email);
        signupBtn = findViewById(R.id.btn_sign_up);
        switchlog = findViewById(R.id.loginNow);
        bar = findViewById(R.id.progressbar);
        otptxt = findViewById(R.id.otptxt);

        mAuth = FirebaseAuth.getInstance();

        switchlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),login_activity.class);
                startActivity(intent);
                finish();
            }
        });
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(signupNumber.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter Mobile Number",Toast.LENGTH_SHORT).show();

                } else if (signupNumber.getText().toString().length()!=10)
                {
                    Toast.makeText(getApplicationContext(),"Enter Valid Mobile Number",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    signupBtn.setVisibility(View.GONE);
                    bar.setVisibility(View.VISIBLE);
                    otptxt.setVisibility(View.VISIBLE);
                    sendotp();
                }
            }

        });
    }

    private void sendotp() {

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
                intent.putExtra("mnumber",signupNumber.getText().toString());
                intent.putExtra("verificationID",verificationId);
                startActivity(intent);

            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+signupNumber.getText().toString())       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }
}