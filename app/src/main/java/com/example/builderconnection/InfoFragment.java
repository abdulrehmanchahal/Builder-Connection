package com.example.builderconnection;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class InfoFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    String name,  phone, pass,  pimage;


    public static InfoFragment newInstance(String param1, String param2) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public InfoFragment() {

    }

    public InfoFragment(String name, String phone, String pass, String pimage ) {

        this.name=name;
        this.phone=phone;
        this.pass=pass;
        this.pimage=pimage;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info, container, false);

        ImageView imageholder = view.findViewById(R.id.imageholder);
        TextView passholder = view.findViewById(R.id.passholder);
        TextView nameholer = view.findViewById(R.id.nameholder);
        TextView phoneholder = view.findViewById(R.id.phoneholder);

        passholder.setText(pass);
        nameholer.setText(name);
        phoneholder.setText(phone);

        Glide.with(getContext()).load(pimage).into(imageholder);



        return view;
    }

    public void onbackpress(){
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,new RecyclerFragment()).addToBackStack(null).commit();
    }
}