package com.example.mycompalaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class closingComplaint extends AppCompatActivity {

    Button close;
    EditText response,check;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closing_complaint);

        close = findViewById(R.id.btn_close);
        response = findViewById(R.id.response);
        textView = findViewById(R.id.content);
        check = findViewById(R.id.check);


        String a = getIntent().getStringExtra("a");
        String b = getIntent().getStringExtra("b");
        HashMap<String,String> hm = new HashMap<>();


        FirebaseDatabase.getInstance().getReference().child("management").child("Queries").child("pendingcomplaint").child(a).child(b).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    HashMap<String,Object> hm1 = (HashMap<String, Object>) snapshot.getValue();
                    hm.put("name",hm1.get("name").toString());
                    hm.put("regno",hm1.get("regno").toString());
                    hm.put("category",hm1.get("category").toString());
                    hm.put("block",hm1.get("block").toString());
                    hm.put("complaint",hm1.get("complaint").toString());
                    textView.setText(hm1.get("complaint").toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(response.getText().toString().length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Enter Your Response",Toast.LENGTH_SHORT).show();
                    return;
                }
                hm.put("response",response.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("management").child("Queries").child("pendingcomplaint").child(a).child(b).removeValue();
                FirebaseDatabase.getInstance().getReference().child("users").child(a).child("pendingcomplaint").child(b).removeValue();
                FirebaseDatabase.getInstance().getReference().child("management").child("Queries").child("resolvedcomplaint").child(a).child(b).setValue(hm);
                FirebaseDatabase.getInstance().getReference().child("users").child(a).child("resolvedcomplaint").child(b).setValue(hm);
                FirebaseDatabase.getInstance().getReference().child("users").child(a).child("notification").child(b).setValue(hm);
                Toast.makeText(getApplicationContext(),"Complaint closed successfully",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),managementDashboard.class);
                startActivity(intent);
                finish();

            }
        });
    }
}