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
import com.example.custom.CustomAdapterAuthor;
import com.example.custom.CustomAdapterSinger;

public class AuthorFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_author,container,false);
        CustomAdapterAuthor customAdapterAuthor = new CustomAdapterAuthor(getContext(), R.layout.my_list_author, MainActivity.list_author);
        ListView listView = view.findViewById(R.id.ListView5);
        listView.setAdapter(customAdapterAuthor);
        return view;

    }
}
