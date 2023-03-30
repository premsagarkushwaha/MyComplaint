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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class pinVerification extends AppCompatActivity {

    Button verifyPin;
    EditText p1,p2,p3,p4,p5,p6;

    FirebaseAuth mAuth;
    FirebaseUser user;

    ProgressBar bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_verification);

        verifyPin = findViewById(R.id.btnVerify);
        p1 = findViewById(R.id.etC1);
        p2 = findViewById(R.id.etC2);
        p3 = findViewById(R.id.etC3);
        p4 = findViewById(R.id.etC4);
        p5 = findViewById(R.id.etC5);
        p6 = findViewById(R.id.etC6);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        bar = findViewById(R.id.progressBarVerify);

        if(user.getEmail().toString().isEmpty())
        {
            Intent intent = new Intent(getApplicationContext(),AccountSetup.class);
            startActivity(intent);
            finish();
        }

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

        verifyPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(p1.getText().toString().isEmpty() || p2.getText().toString().isEmpty() ||
                        p3.getText().toString().isEmpty() || p4.getText().toString().isEmpty() ||
                        p5.getText().toString().isEmpty() || p6.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter Valid PIN",Toast.LENGTH_SHORT).show();
                }

                else
                {
                    if(user!=null)
                    {
                        verifyPin.setVisibility(View.GONE);
                        bar.setVisibility(View.VISIBLE);

                        String enteredpin = p1.getText().toString()+
                                p2.getText().toString()+
                                p3.getText().toString()+
                                p4.getText().toString()+
                                p5.getText().toString()+
                                p6.getText().toString();
//                        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid().toString()).child("pin").addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                if(snapshot.exists())
//                                {
//                                    String str = snapshot.getValue().toString();
//
//                                    changecall(str,enteredpin);
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });


                        String email = user.getEmail().toString();

                            AuthCredential credential = EmailAuthProvider
                                    .getCredential(email, enteredpin);

                            user.reauthenticate(credential)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {

                                                Intent intent = new Intent(getApplicationContext(),dashboard_activity.class);
                                                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else {
                                                Toast.makeText(getApplicationContext(),"PIN is not correct",Toast.LENGTH_SHORT).show();
                                                verifyPin.setVisibility(View.VISIBLE);
                                                bar.setVisibility(View.GONE);
                                            }
                                        }
                                    });










                    }


                }
            }
        });
    }
}