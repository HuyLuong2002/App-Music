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
import com.example.model.Author;
import com.example.model.Singer;

import java.util.List;

public class CustomAdapterAuthor extends ArrayAdapter<Author> {

    public CustomAdapterAuthor(@NonNull Context context, int resource, @NonNull List<Author> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        //inflate layout
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.my_list_author, parent, false); //với layout_my_list_item là cái sườn để ráp dô listview
        }
        //view holder
        TextView textView = view.findViewById(R.id.textView11);
        TextView textView1 = view.findViewById(R.id.textView12);
        if (view != null) {
            textView.setText(String.valueOf(MainActivity.list_author.get(position).getIdAuthor()));
            textView1.setText(MainActivity.list_author.get(position).getAuthorName());
        }
        return view;
    }
}

