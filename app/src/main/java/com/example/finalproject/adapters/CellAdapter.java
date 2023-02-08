package com.example.finalproject.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Cell;
import com.example.finalproject.Interface.RecyclerViewInterface;
import com.example.finalproject.MainActivity;
import com.example.finalproject.MyDatabaseHelper;
import com.example.finalproject.R;
import com.example.finalproject.UpdateActivity;

import java.util.ArrayList;
import java.util.Locale;

public class CellAdapter extends RecyclerView.Adapter<CellAdapter.CellViewHolder>{

    private ArrayList <Cell> cells;
    private  RecyclerViewInterface recyclerViewInterface;


    public CellAdapter(ArrayList<Cell> cells, RecyclerViewInterface recyclerViewInterface) {
        this.cells = cells;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public CellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //This is where you inflate the layout (Giving a look to our rows)
        View cellView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleritem_alarm, parent, false);

        return new CellViewHolder(cellView, recyclerViewInterface);
    }

    public CellAdapter() {
        super();
    }

    public void onBindViewHolder(@NonNull CellViewHolder holder, int position) {
        // assigning values to the views we created in the recycler_view row layout file
        // based on the position of the recycler view
        //שינוי של הערכים
        Cell currentCell = cells.get(position);

        holder.TV_AlarmName.setText(String.valueOf(currentCell.getAlarm_name().get(position)));
        holder.BtnTime.setText(String.valueOf(currentCell.getAlarm_time().get(position)));


        holder.BtnSwitch.setChecked(/*true*/currentCell.isCellOnOrOff(position));


    }
//    CellViewHolder holder;

    @Override
    public int getItemCount() {
        // the recycler view just wants to know the number of items you want displayed
        return cells.size();
    }

    public static class CellViewHolder extends RecyclerView.ViewHolder  {
        // grabbing the views from our Cell layout file
        // Kinda like in the onCreate method
        public Button BtnTime;
        public TextView TV_AlarmName;
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        public Switch BtnSwitch;
        private Context context;


        public CellViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            BtnTime = itemView.findViewById(R.id.btnTime);
            TV_AlarmName = itemView.findViewById(R.id.AlarmName);
            BtnSwitch = itemView.findViewById(R.id.btnSwitch);

            BtnTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("VVVVWWWWAAAAAATTTTTT", "BtnTime: ");

                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onButtonTimeClick(BtnTime, pos);
                        }
                    }

                }
            });
//            BtnSwitch.setOnClickListener(this);
            BtnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if(isChecked){
                        Log.d("VVVVWWWWAAAAAATTTTTT", "BtnSwitch: Checked");

                        if(recyclerViewInterface != null){
                            int pos = getAdapterPosition();

                            if(pos != RecyclerView.NO_POSITION){
                                recyclerViewInterface.onSwitchClick(pos,"on");
                            }
                        }
                    }
                    else{
                        Log.d("VVVVWWWWAAAAAATTTTTT", "BtnSwitch: Not Checked");
                        if(recyclerViewInterface != null){
                            int pos = getAdapterPosition();

                            if(pos != RecyclerView.NO_POSITION){
                                recyclerViewInterface.onSwitchClick(pos,"off");
                            }
                        }

                    }

                }
            });
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


}
