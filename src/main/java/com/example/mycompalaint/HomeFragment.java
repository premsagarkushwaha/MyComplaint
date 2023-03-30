package com.example.mycompalaint;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycompalaint.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();

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

    FragmentHomeBinding binding;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        binding.userid.setVisibility(View.GONE);
        binding.usernamepb.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("detail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    HashMap<String,Object> hm = (HashMap<String, Object>) snapshot.getValue();
                    binding.userid.setText(hm.get("name").toString());
                    binding.userid.setVisibility(View.VISIBLE);
                    binding.usernamepb.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.pendingcomp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),pendingComplaints.class);
                startActivity(intent);


            }
        });

        binding.totalcomp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(),totalComplaints.class);
                startActivity(intent);

            }
        });
        binding.resolved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),resolvedComplaint.class);
                startActivity(intent);
            }
        });

        binding.totalcount.setVisibility(View.GONE);
        binding.usernamepbt.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("totalcomplaint").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    int v=(int) snapshot.getChildrenCount();
                    binding.totalcount.setText(v+"");
                    binding.totalcount.setVisibility(View.VISIBLE);
                    binding.usernamepbt.setVisibility(View.GONE);
                }
                else
                {
                    binding.totalcount.setVisibility(View.VISIBLE);
                    binding.usernamepbt.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.activecom.setVisibility(View.GONE);
        binding.usernamepba.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("pendingcomplaint").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    int v=(int) snapshot.getChildrenCount();
                    binding.activecom.setText(v+"");
                    binding.activecom.setVisibility(View.VISIBLE);
                    binding.usernamepba.setVisibility(View.GONE);
                }
                else {
                    binding.activecom.setVisibility(View.VISIBLE);
                    binding.usernamepba.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.resolvedcom.setVisibility(View.GONE);
        binding.usernamepbr.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("resolvedcomplaint").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    int v=(int) snapshot.getChildrenCount();
                    binding.resolvedcom.setText(v+"");
                    binding.resolvedcom.setVisibility(View.VISIBLE);
                    binding.usernamepbr.setVisibility(View.GONE);
                }
                else {
                    binding.resolvedcom.setVisibility(View.VISIBLE);
                    binding.usernamepbr.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}