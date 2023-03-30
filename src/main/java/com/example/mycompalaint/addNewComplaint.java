package com.example.mycompalaint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;

public class addNewComplaint extends AppCompatActivity
{

    EditText comp;
    Button sub;

    FirebaseAuth auth;
    FirebaseUser user;

    Calendar calendar;

    String[] basicdetail = new String[2];



    String[] item = {"Hostel","Mess Food","Transport/Bus","Academic"};
    String[] hostel = {"Academic Block","Boys Hostel Block 1","Boys Hostel Block 2","Boys Hostel Block 3","Girls Hostel"};

    AutoCompleteTextView autoCompleteTextView,autoCompleteTextView1;
    ArrayAdapter<String> adapterItems;

    ArrayAdapter<String> hostelBlock;
    ImageView imageView;


    private final int GALLERY_REQ_CODE = 1000;
    private final int CAMERA_REQ_CODE = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_complaint);

        comp = findViewById(R.id.complaint);
        sub = findViewById(R.id.submit);
        calendar = Calendar.getInstance();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        imageView = findViewById(R.id.imgGallery);
        Button btnGallery = findViewById(R.id.btn_Gallery);
        Button btnCamera = findViewById(R.id.btn_Camera);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(iCamera, CAMERA_REQ_CODE);
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery,GALLERY_REQ_CODE);
            }
        });

        autoCompleteTextView = findViewById(R.id.auto_complete_textview);
        autoCompleteTextView1 = findViewById(R.id.auto_complete_textview1);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item,item);
        hostelBlock = new ArrayAdapter<String>(this,R.layout.list_item,hostel);

        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView1.setAdapter(hostelBlock);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(MainActivity.this, item , Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String hostel = adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(MainActivity.this, hostel , Toast.LENGTH_SHORT).show();
            }
        });

        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("detail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    HashMap<String,Object> getDetail = (HashMap<String, Object>) snapshot.getValue();
                    basicdetail[0] = getDetail.get("name").toString();
                    basicdetail[1] = getDetail.get("registerno").toString();


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> hm1 = new HashMap<>();
                HashMap<String,Object> hm2 = new HashMap<>();
                String complaint = comp.getText().toString();
                String category = autoCompleteTextView.getText().toString();
                String block = autoCompleteTextView1.getText().toString();
                String name = basicdetail[0];
                String regno = basicdetail[1];

                hm1.put("category",category);
                hm1.put("block", block);
                hm1.put("complaint",complaint);

                hm2.put("name",name);
                hm2.put("regno",regno);
                hm2.put("category",category);
                hm2.put("block", block);
                hm2.put("complaint",complaint);

                if(category.isEmpty() || block.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Enter category and block",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(complaint.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Enter Description",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    String tempCal = calendar.getTime().toString();
                    FirebaseDatabase.getInstance().getReference().child("management").child("Queries").child("pendingcomplaint").child(user.getUid()).child(tempCal).setValue(hm2);
                    FirebaseDatabase.getInstance().getReference().child("management").child("Queries").child("totalcomplaint").child(user.getUid()).child(tempCal).setValue(hm2);
                    FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("totalcomplaint").child(tempCal).setValue(hm1);
                    FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("pendingcomplaint").child(tempCal).setValue(hm1);
                    if(user!=null) {
                        Toast.makeText(getApplicationContext(), "Query added successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), dashboard_activity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Intent intent = new Intent(getApplicationContext(), login_activity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){

            if(requestCode==GALLERY_REQ_CODE){

                imageView.setImageURI(data.getData());
            }
            else if(requestCode==CAMERA_REQ_CODE){

                Bitmap bitmap = (Bitmap) (data.getExtras().get("data"));
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}