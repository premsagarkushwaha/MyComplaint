package com.example.mycompalaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class adminTotalComplaintView extends AppCompatActivity {

    TextView name, regno,category,block,description,response;

    Button close;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_total_complaint_view);

        name = findViewById(R.id.txt_name);
        regno = findViewById(R.id.txt_regno);
        category = findViewById(R.id.txt_category);
        block = findViewById(R.id.txt_block);
        description = findViewById(R.id.txt_content);

        String a = getIntent().getStringExtra("cuid");
        String b = getIntent().getStringExtra("cid");

        FirebaseDatabase.getInstance().getReference().child("management").child("Queries").child("totalcomplaint").child(a).child(b).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    HashMap<String,Object> hm1 = (HashMap<String, Object>) snapshot.getValue();
                    name.setText(hm1.get("name").toString());
                    regno.setText(hm1.get("regno").toString());
                    category.setText(hm1.get("category").toString());
                    block.setText(hm1.get("block").toString());
                    description.setText(hm1.get("complaint").toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}