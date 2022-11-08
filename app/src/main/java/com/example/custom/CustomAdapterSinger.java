package com.example.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.app_music.MainActivity;
import com.example.app_music.R;
import com.example.model.Singer;
import com.example.model.Song;

import java.util.List;

public class CustomAdapterSinger extends ArrayAdapter<Singer> {

    public CustomAdapterSinger(@NonNull Context context, int resource, @NonNull List<Singer> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        //inflate layout
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.my_list_singer, parent, false); //với layout_my_list_item là cái sườn để ráp dô listview
        }
        //view holder
        TextView textView9 = view.findViewById(R.id.textView9);
        TextView textView10 = view.findViewById(R.id.textView10);
        if (view != null) {
            textView9.setText(String.valueOf(MainActivity.list_singer.get(position).getIdSinger()));
            textView10.setText(MainActivity.list_singer.get(position).getSingerName());
        }
        return view;
    }
}

