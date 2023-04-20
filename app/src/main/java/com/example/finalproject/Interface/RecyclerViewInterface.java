package com.example.finalproject.Interface;

import android.widget.Button;

public interface RecyclerViewInterface {

    void onItemClick(int position);

    void onButtonTimeClick(Button btnTime, int position);

    void onSwitchClick( int position/*, String flag*/);


}
