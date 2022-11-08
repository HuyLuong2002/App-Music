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
import com.example.model.Singer;
import com.example.model.User;

import java.util.List;

public class CustomAdapterUser extends ArrayAdapter<User> {

    public CustomAdapterUser(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        //inflate layout
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.my_list_user, parent, false); //với layout_my_list_item là cái sườn để ráp dô listview
        }
        //view holder
        TextView textView = view.findViewById(R.id.textView9);
        TextView textView1 = view.findViewById(R.id.textView14);
        TextView textView2 = view.findViewById(R.id.textView15);
        TextView textView3 = view.findViewById(R.id.textView16);

        if (view != null) {
            textView.setText(String.valueOf(MainActivity.list_all_user.get(position).getId()));
            textView1.setText(MainActivity.list_all_user.get(position).getUsername());
            textView2.setText(MainActivity.list_all_user.get(position).getPassword());
            textView3.setText(String.valueOf(MainActivity.list_all_user.get(position).getId_auth()));
        }
        return view;
    }
}

