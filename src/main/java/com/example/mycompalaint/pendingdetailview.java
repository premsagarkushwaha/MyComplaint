package com.example.mycompalaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class pendingdetailview extends AppCompatActivity {

    TextView name, regno,category,block,description;

    Button close;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendingdetailview);

        name = findViewById(R.id.txt_name);
        regno = findViewById(R.id.txt_regno);
        category = findViewById(R.id.txt_category);
        block = findViewById(R.id.txt_block);
        description = findViewById(R.id.txt_content);

        close = findViewById(R.id.btn_close);

        String a = getIntent().getStringExtra("cuid");
        String b = getIntent().getStringExtra("cid");

//        name.setText(a);
//        regno.setText(b);

//        FirebaseDatabase
        FirebaseDatabase.getInstance().getReference().child("management").child("Queries").child("pendingcomplaint").child(a).child(b).addValueEventListener(new ValueEventListener() {
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

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getApplicationContext(),closingComplaint.class);
                intent.putExtra("a",a);
                intent.putExtra("b",b);
                startActivity(intent);
                finish();

            }
        });


    }
}