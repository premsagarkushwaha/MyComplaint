package com.example.mycompalaint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class newClose extends AppCompatActivity {
    EditText editText;
    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_close);

        editText = findViewById(R.id.newentry);
        textView = findViewById(R.id.show);
        button = findViewById(R.id.check);


        String a = getIntent().getStringExtra("a");
        String b = getIntent().getStringExtra("b");
        HashMap<String,String> hm = new HashMap<>();
        String str = editText.getText().toString();
        hm.put("response",editText.getText().toString());



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Enter content",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

                    textView.setText(editText.getText().toString());
                }
            }
        });
    }
}