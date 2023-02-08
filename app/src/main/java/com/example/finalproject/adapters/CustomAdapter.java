package com.example.finalproject.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Interface.RecyclerViewInterface;
import com.example.finalproject.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private View view;
    private  RecyclerViewInterface recyclerViewInterface;


    private Context context ;
    private Activity activity;
    private ArrayList alarm_name, alarm_time, alarm_on_or_off;
    public CustomAdapter(Activity activity, Context context, ArrayList alarm_name, ArrayList alarm_time, ArrayList alarm_on_or_off,RecyclerViewInterface recyclerViewInterface){
        this.activity = activity;
        this.context = context;
        this.alarm_name = alarm_name;
        this.alarm_time = alarm_time;
        this.alarm_on_or_off = alarm_on_or_off;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.recycleritem_alarm, parent, false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.TV_AlarmName.setText(String.valueOf(alarm_name.get(position)));
        holder.BtnTime.setText(String.valueOf(alarm_time.get(position)));
        holder.BtnSwitch.setText(String.valueOf(alarm_on_or_off.get(position)));
        //Recyclerview onClickListener
//        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, UpdateActivity.class);
//                intent.putExtra("id", String.valueOf(book_id.get(position)));
//                intent.putExtra("title", String.valueOf(book_title.get(position)));
//                intent.putExtra("author", String.valueOf(book_author.get(position)));
//                intent.putExtra("pages", String.valueOf(book_pages.get(position)));
//                activity.startActivityForResult(intent, 1);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return alarm_name.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // grabbing the views from our Cell layout file
        // Kinda like in the onCreate method
        public Button BtnTime;
        public TextView TV_AlarmName;
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        public Switch BtnSwitch;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            BtnTime = itemView.findViewById(R.id.btnTime);
            TV_AlarmName = itemView.findViewById(R.id.AlarmName);
            BtnSwitch = itemView.findViewById(R.id.btnSwitch);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }
//    class MyViewHolder extends RecyclerView.ViewHolder {
//        public Button BtnTime;
//        public TextView TV_AlarmName;
//        @SuppressLint("UseSwitchCompatOrMaterialCode")
//        public Switch BtnSwitch;
//
//        BtnTime = itemView.findViewById(R.id.btnTime);
//        TV_AlarmName = itemView.findViewById(R.id.AlarmName);
//        BtnSwitch = itemView.findViewById(R.id.btnSwitch);
//
//        MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            book_id_txt = itemView.findViewById(R.id.book_id_txt);
//            book_title_txt = itemView.findViewById(R.id.book_title_txt);
//            book_author_txt = itemView.findViewById(R.id.book_author_txt);
//            book_pages_txt = itemView.findViewById(R.id.book_pages_txt);
//            mainLayout = itemView.findViewById(R.id.mainLayout);
//            //Animate Recyclerview
////            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
////            mainLayout.setAnimation(translate_anim);
//        }
//
//    }

}