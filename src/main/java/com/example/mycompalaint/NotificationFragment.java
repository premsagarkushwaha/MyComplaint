package com.example.mycompalaint;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mycompalaint.databinding.FragmentNotificationBinding;
import com.example.mycompalaint.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;

    FirebaseAuth auth;
    FirebaseUser user;


    ArrayList<String> arrcid = new ArrayList<>();
    ArrayList<String> arrdate = new ArrayList<>();
    ArrayList<String> arrcategory = new ArrayList<>();
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentNotificationBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_notification, container, false);
        binding = FragmentNotificationBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        myadapter adapter = new myadapter(getContext(),arrcid,arrdate,arrcategory);
        binding.notificationList.setAdapter(adapter);
//        arrcid.add("1232");
//        arrcategory.add("new");
//        arrdate.add("23");
//        adapter.notifyDataSetChanged();

        binding.notificationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(),resolvedUserView.class);
                intent.putExtra("cid",arrcid.get(i));  // complaint ID
                FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("notification").child(arrcid.get(i)).removeValue();
                startActivity(intent);

            }
        });
        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("notification").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {

                    for(DataSnapshot snapshot1 : snapshot.getChildren())
                    {
                        HashMap<String,Object> hm1 = (HashMap<String, Object>) snapshot1.getValue();
                        String a = snapshot1.getKey().toString();
                        arrcid.add(a);
                        //arrblock.add(hm1.get("block").toString());
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
    class myadapter extends ArrayAdapter<String>
    {

        Context context;
        ArrayList<String> cid;
        ArrayList<String> date;
        ArrayList<String> category;


        public myadapter( Context context, ArrayList<String> cid,ArrayList<String> date, ArrayList<String> category) {
            super(context,R.layout.items,R.id.item_block,cid);

            this.cid = cid;
            this.date = date;
            this.category = category;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflater=(LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view=inflater.inflate(R.layout.notificationitem,parent,false);

//            TextView username =view.findViewById(R.id.item_user);
//            TextView usercid=view.findViewById(R.id.item_block);
            TextView userdate =view.findViewById(R.id.item_date);
            TextView usercategory=view.findViewById(R.id.item_category);

//            username.setText(name.get(position));
//            userblock.setText(block.get(position));
            userdate.setText(date.get(position));
            usercategory.setText(category.get(position));

            return view;
        }
    }
}