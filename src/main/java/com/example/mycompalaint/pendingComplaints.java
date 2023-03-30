package com.example.mycompalaint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

public class pendingComplaints extends AppCompatActivity
{

    FirebaseAuth auth;
    FirebaseUser user;
    Button dashButton;
    ListView listView;

    ArrayList<String> arrname = new ArrayList<>();
    ArrayList<String> arrblock = new ArrayList<>();
    ArrayList<String> arrdate = new ArrayList<>();
    ArrayList<String> arrcategory = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_complaints);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        listView = findViewById(R.id.pending_list);

//        arrname.add("test");
//        arrblock.add("test");
//        arrcategory.add("test");
//        arrdate.add("test");

        if(user==null)
        {

        }
        else
        {

            myadapter adapter = new myadapter(this, arrname, arrblock,arrdate,arrcategory);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(),complaintDetail.class);
                    intent.putExtra("cid",arrname.get(i));
                    startActivity(intent);
                }
            });

            FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("pendingcomplaint").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.exists())
                    {

                        for(DataSnapshot snapshot1 : snapshot.getChildren())
                        {
                            HashMap<String,Object> hm1 = (HashMap<String, Object>) snapshot1.getValue();
                            String a = snapshot1.getKey().toString();
                            arrname.add(a);
                            arrblock.add(hm1.get("block").toString());
                            arrcategory.add(hm1.get("category").toString());

                            arrdate.add(a.substring(0,10)+", "+a.substring(a.length()-5));

                        }



                        adapter.notifyDataSetChanged();


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
    }

    class myadapter extends ArrayAdapter<String>
    {

        Context context;
        ArrayList<String> name;
        ArrayList<String> block;
        ArrayList<String> date;
        ArrayList<String> category;


        public myadapter( Context context, ArrayList<String> name, ArrayList<String> block, ArrayList<String> date, ArrayList<String> category) {
            super(context,R.layout.items,R.id.item_block,name);

            this.name = name;
            this.block = block;
            this.date = date;
            this.category = category;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view=inflater.inflate(R.layout.items,parent,false);

//            TextView username =view.findViewById(R.id.item_user);
            TextView userblock=view.findViewById(R.id.item_block);
            TextView userdate =view.findViewById(R.id.item_date);
            TextView usercategory=view.findViewById(R.id.item_category);

//            username.setText(name.get(position));
            userblock.setText(block.get(position));
            userdate.setText(date.get(position));
            usercategory.setText(category.get(position));

            return view;
        }
    }


}