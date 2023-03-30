package com.example.mycompalaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AccountSetup extends AppCompatActivity {

    EditText name,email,register;

    Button finish;

    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup);

        name = findViewById(R.id.as_name);
        email = findViewById(R.id.as_email);
        register = findViewById(R.id.as_registerno);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        finish = findViewById(R.id.btn_as);

        String pin = getIntent().getStringExtra("pin");

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> hm = new HashMap<>();
                hm.put("name",name.getText().toString());
                hm.put("registerno",register.getText().toString());

                if(user!=null) {
                    FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("detail").setValue(hm);
                    user.updateEmail(email.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(getApplicationContext(),"Email updated",Toast.LENGTH_SHORT).show();

                                    }
                                    else {

                                    }
                                }
                            });

                    Toast.makeText(getApplicationContext(), "Account updated successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), dashboard_activity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), login_activity.class);
                    startActivity(intent);
                    finish();

//                    LayoutInflater inflater = (LayoutInflater)getApplicationContext();
                }
            }
        });
    }
}