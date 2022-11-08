package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.app_music.MainActivity;
import com.example.app_music.R;
import com.example.custom.CustomAdapterSinger;

public class SingerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_singer, container, false);
        CustomAdapterSinger customAdapterSinger = new CustomAdapterSinger(getContext(), R.layout.my_list_singer, MainActivity.list_singer);
        ListView listView = view.findViewById(R.id.ListView4);
        listView.setAdapter(customAdapterSinger);
        return view;


    }
}
