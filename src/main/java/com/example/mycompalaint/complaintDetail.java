package com.example.mycompalaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class complaintDetail extends AppCompatActivity {

    TextView category,block,description;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_detail);

        category = findViewById(R.id.txt_category);
        block = findViewById(R.id.txt_block);
        description = findViewById(R.id.txt_content);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        String tempPath = getIntent().getStringExtra("cid");

        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("pendingcomplaint").child(tempPath).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    HashMap<String,Object> hm1 = (HashMap<String, Object>) snapshot.getValue();

                    category.setText(hm1.get("category").toString());
                    block.setText(hm1.get("block").toString());
                    description.setText(hm1.get("complaint").toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        cid = findViewById(R.id.cid);
//        cid.setText(getIntent().getStringExtra("cid").toString());
    }
}