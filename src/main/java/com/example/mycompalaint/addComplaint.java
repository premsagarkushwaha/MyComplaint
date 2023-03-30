package com.example.mycompalaint;

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
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class addComplaint extends AppCompatActivity {

    String[] item = {"Hostel","Mess Food","Transport/Bus","Academic"};
    String[] hostel = {"Academic Block","Boys Hostel Block 1","Boys Hostel Block 2","Boys Hostel Block 3","Girls Hostel"};

    AutoCompleteTextView autoCompleteTextView,autoCompleteTextView1;
    ArrayAdapter<String> adapterItems;

    ArrayAdapter<String> hostelBlock;
    EditText descrip;
    Button submit;
    ImageView imageView;

    FirebaseAuth auth;
    FirebaseUser user;
    Calendar calendar;
    private final int GALLERY_REQ_CODE = 1000;
    private final int CAMERA_REQ_CODE = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaint);

        imageView = findViewById(R.id.imgGallery);
        Button btnGallery = findViewById(R.id.btn_Gallery);
        Button btnCamera = findViewById(R.id.btn_Camera);
        descrip = findViewById(R.id.complaint);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        calendar = Calendar.getInstance();

        submit = findViewById(R.id.submit);

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

//        String category = autoCompleteTextView.getText().toString();
//        String  block = autoCompleteTextView1.getText().toString();
        String hm = descrip.getText().toString();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
//                if(category.isEmpty() || block.isEmpty())
//                {
//                    Toast.makeText(getApplicationContext(), "Select category and block",Toast.LENGTH_SHORT).show();
//                }
                if(hm.length()==0)
                {
                    Toast.makeText(getApplicationContext(), "Enter Description",Toast.LENGTH_SHORT).show();
                    return;
                }

//                HashMap<String,Object> hm = new HashMap<>();
//                hm.put("category",category);
//                hm.put("block",block);
//                hm.put("description",description);


                String tempCal = calendar.getTime().toString();
                FirebaseDatabase.getInstance().getReference().child("management").child("Queries").child(user.getUid()).child(tempCal).setValue(hm);
                FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("totalcomplaint").child(tempCal).setValue(hm);
                FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("pendingcomplaint").child(tempCal).setValue(hm);
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