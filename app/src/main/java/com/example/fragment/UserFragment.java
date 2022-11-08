package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_music.MainActivity;
import com.example.app_music.R;
import com.example.custom.CustomAdapterSinger;
import com.example.custom.CustomAdapterUser;

public class UserFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_user, container, false);
        CustomAdapterUser customAdapterUser = new CustomAdapterUser(getContext(), R.layout.my_list_user, MainActivity.list_all_user);
        ListView listView = view.findViewById(R.id.ListView6);
        listView.setAdapter(customAdapterUser);
        return view;


    }
}
