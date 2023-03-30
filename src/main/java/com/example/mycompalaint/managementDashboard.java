package com.example.mycompalaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class managementDashboard extends AppCompatActivity {

    CardView pending,total,resolve;
    TextView totalview,pendingview,resolvedview;

    ImageView logout;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_dashboard);

        pending  = findViewById(R.id.pendingcomp);
        total = findViewById(R.id.totalcomp);
        resolve = findViewById(R.id.resolvedcomp);

        totalview = findViewById(R.id.totalcount);
        pendingview = findViewById(R.id.pendingcount);
        resolvedview = findViewById(R.id.resolvedcount);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        logout = findViewById(R.id.logoutadmin);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(getApplicationContext(),user_admin_activity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }
        });
        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),adminPendingComplaints.class);
                startActivity(intent);

            }
        });

        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),adminTotalComplaints.class);
                startActivity(intent);

            }
        });

        resolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),adminResolvedComplaint.class);
                startActivity(intent);
            }
        });

        FirebaseDatabase.getInstance().getReference().child("management").child("Queries").child("pendingcomplaint").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int v=0;
                if(snapshot.exists())
                {
                    for(DataSnapshot snapshot1 : snapshot.getChildren())
                    {
                        v+= (int)snapshot1.getChildrenCount();
                    }
                    pendingview.setText(v+"");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("management").child("Queries").child("totalcomplaint").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int v=0;
                if(snapshot.exists())
                {
                    for(DataSnapshot snapshot1 : snapshot.getChildren())
                    {
                        v+= (int)snapshot1.getChildrenCount();
                    }
                    totalview.setText(v+"");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("management").child("Queries").child("resolvedcomplaint").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int v=0;
                if(snapshot.exists())
                {
                    for(DataSnapshot snapshot1 : snapshot.getChildren())
                    {
                        v+= (int)snapshot1.getChildrenCount();
                    }
                    resolvedview.setText(v+"");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}