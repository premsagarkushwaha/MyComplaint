package com.example.mycompalaint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class adminResolvedComplaint extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    Button dashButton;
    ListView listView;

    ArrayList<String> arrname = new ArrayList<>();
    ArrayList<String> arrregno = new ArrayList<>();
    ArrayList<String> arrblock = new ArrayList<>();
    ArrayList<String> arrdate = new ArrayList<>();
    ArrayList<String> arrcategory = new ArrayList<>();
    ArrayList<String> arrdescription = new ArrayList<>();
    ArrayList<String> arrcuid = new ArrayList<>();
    ArrayList<String> arrcid = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_resolved_complaint);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        listView = findViewById(R.id.pending_list);

        if(user==null)
        {

        }
        else {

            myadapter adapter = new myadapter(this, arrname, arrregno, arrblock, arrdate, arrcategory, arrdescription, arrcuid, arrcid);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(), adminResolvedTotalView.class);
                    intent.putExtra("cuid", arrcuid.get(i));
                    intent.putExtra("cid", arrcid.get(i));

                    startActivity(intent);
                }
            });

            FirebaseDatabase.getInstance().getReference().child("management").child("Queries").child("resolvedcomplaint").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {

                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                                HashMap<String, Object> hm1 = (HashMap<String, Object>) snapshot2.getValue();
                                String bcuid = snapshot1.getKey().toString();
                                String a = snapshot2.getKey().toString();
                                arrname.add(hm1.get("name").toString());
                                arrregno.add(hm1.get("regno").toString());
                                arrblock.add(hm1.get("block").toString());
                                arrcategory.add(hm1.get("category").toString());
                                arrdescription.add(hm1.get("complaint").toString());
                                arrdate.add(a.substring(0, 10) + ", " + a.substring(a.length() - 5));
                                arrcuid.add(bcuid);
                                arrcid.add(a);
                            }


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
        ArrayList<String> regno;
        ArrayList<String> block;
        ArrayList<String> date;
        ArrayList<String> category;
        ArrayList<String> description;

        ArrayList<String> cuid;
        ArrayList<String> cid;


        public myadapter( Context context, ArrayList<String> name,ArrayList<String> regno, ArrayList<String> block, ArrayList<String> date, ArrayList<String> category,ArrayList<String> description,ArrayList<String> cuid, ArrayList<String> cid) {
            super(context,R.layout.items,R.id.item_block,name);

            this.name = name;
            this.regno = regno;
            this.block = block;
            this.date = date;
            this.category = category;
            this.description = description;
            this.cuid = cuid;
            this.cid = cid;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view=inflater.inflate(R.layout.adminitem,parent,false);

            TextView username =view.findViewById(R.id.item_name);
            TextView userregno =view.findViewById(R.id.item_regno);
            TextView userblock=view.findViewById(R.id.item_block);
            TextView userdate =view.findViewById(R.id.item_date);
            TextView usercategory=view.findViewById(R.id.item_category);


            username.setText(name.get(position));
            userregno.setText(regno.get(position));
            userblock.setText(block.get(position));
            userdate.setText(date.get(position));
            usercategory.setText(category.get(position));

            return view;
        }
    }
}