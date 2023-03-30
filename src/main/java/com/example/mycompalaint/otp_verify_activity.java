package com.example.mycompalaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class otp_verify_activity extends AppCompatActivity {

    String verficationId;
    Button verifyBtn;
    EditText p1,p2,p3,p4,p5,p6;
    FirebaseAuth auth;
    FirebaseUser user;

    ProgressBar bar;

    TextView mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);

        p1 = findViewById(R.id.etC1);
        p2 = findViewById(R.id.etC2);
        p3 = findViewById(R.id.etC3);
        p4 = findViewById(R.id.etC4);
        p5 = findViewById(R.id.etC5);
        p6 = findViewById(R.id.etC6);
        auth = FirebaseAuth.getInstance();

        bar = findViewById(R.id.progressBarVerify);

        mobile = findViewById(R.id.tvMobile);

        verifyBtn = findViewById(R.id.btnVerify);

        verficationId = getIntent().getStringExtra("verificationID");

        mobile.setText("+91-"+getIntent().getStringExtra("mnumber").toString());

        p1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!p1.getText().toString().isEmpty())
                {
                    p2.requestFocus();
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        p2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(p2.getText().toString().isEmpty())
                {
                    p1.requestFocus();
                }
                else {
                    p3.requestFocus();

                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        p3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(p3.getText().toString().isEmpty())
                {
                    p2.requestFocus();
                }
                else {
                    p4.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        p4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(p4.getText().toString().isEmpty())
                {
                    p3.requestFocus();
                }
                else {
                    p5.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        p5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(p5.getText().toString().isEmpty())
                {
                    p4.requestFocus();
                }
                else {
                    p6.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        p6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(p6.getText().toString().isEmpty())
                {
                    p5.requestFocus();
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(p1.getText().toString().isEmpty() || p2.getText().toString().isEmpty() ||
                        p3.getText().toString().isEmpty() ||p4.getText().toString().isEmpty() ||
                        p5.getText().toString().isEmpty() ||p6.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Complete the OTP",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(verficationId != null)
                    {
                        verifyBtn.setVisibility(View.GONE);
                        bar.setVisibility(View.VISIBLE);
                        String optcode = p1.getText().toString()+p2.getText().toString()+p3.getText().toString()+p4.getText().toString()+
                                p5.getText().toString()+p6.getText().toString();

                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verficationId, optcode);
                        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful())
                                {
//                                    Intent intent = new Intent(getApplicationContext(),pinsetting.class);
//                                    intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
//                                    startActivity(intent);
                                    user = auth.getCurrentUser();
                                    if(user.getEmail()==null)
                                    {
                                        Intent intent = new Intent(getApplicationContext(),pinSetup.class);
                                        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                    else {
                                        Intent intent = new Intent(getApplicationContext(),pinVerification.class);
                                        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                                else {
                                    verifyBtn.setVisibility(View.VISIBLE);
                                    bar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(),"Invalid OTP",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                }
            }
        });


    }
}