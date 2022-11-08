package com.example.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.app_music.MainActivity;
import com.example.app_music.R;
import com.example.model.Album;
import com.example.model.Author;

import java.util.List;

public class CustomAdapterAlbum extends ArrayAdapter<Album> {

    public CustomAdapterAlbum(@NonNull Context context, int resource, @NonNull List<Album> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        //inflate layout
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.my_list_album, parent, false); //với layout_my_list_item là cái sườn để ráp dô listview
        }
        //view holder
        TextView textView = view.findViewById(R.id.textView13);
        if (view != null) {
            textView.setText(MainActivity.list_album.get(position).getAlbumName());
        }
        return view;
    }
}

