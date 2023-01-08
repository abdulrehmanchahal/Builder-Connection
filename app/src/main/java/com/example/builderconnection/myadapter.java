package com.example.builderconnection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewholder>  {


    public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model) {

        holder.passtext.setText(model.getPass());
        holder.nametext.setText(model.getName());
        holder.phonetext.setText(model.getPhome_no());

        Glide.with(holder.img1.getContext()).load(model.getPimage()).into(holder.img1);

        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,new InfoFragment(model.getName(),model.getPhome_no(),model.getPass(),model.getPimage())).addToBackStack(null).commit();


            }
        });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,parent,false);
        return  new myviewholder(view);
    }

    public  class myviewholder extends RecyclerView.ViewHolder {

        ImageView img1;
        TextView passtext,nametext,phonetext;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            img1=itemView.findViewById(R.id.img1);
            passtext=itemView.findViewById(R.id.passtext);
            nametext=itemView.findViewById(R.id.nametext);
            phonetext=itemView.findViewById(R.id.phonetext);

        }
    }
}
