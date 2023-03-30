package com.example.mycompalaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class user_admin_activity extends AppCompatActivity {

    Button buttonUser, buttonAdmin;
    FirebaseAuth mAuth;

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
        {
            String s1 = "fH7fY9lYRmPgTednHKSaQxsqJ1o2";
            String s2 = currentUser.getUid().toString();
            if(s1.equals(s2))
            {
                Intent intent = new Intent(getApplicationContext(),adminLogin.class);
                startActivity(intent);
                finish();
            }
            else if(currentUser.getEmail().isEmpty()==true)
            {
                Intent intent = new Intent(getApplicationContext(),pinSetup.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Intent intent = new Intent(getApplicationContext(),pinVerification.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admin);
        buttonUser = findViewById(R.id.btn_user);
        buttonAdmin = findViewById(R.id.btn_admin);

        buttonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), login_activity.class);
                startActivity(intent);
            }
        });

        buttonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), adminLogin.class);
                startActivity(intent);
            }
        });
    }
}