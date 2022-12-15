package com.example.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.app_music.MainActivity;
import com.example.app_music.R;
import com.example.model.Album;
import com.example.model.DetailAlbum;
import com.example.model.Singer;
import com.example.model.Song;

import java.util.List;

public class CustomAdapterDetailAlbum extends ArrayAdapter<Song> {
    private TextView textView5, textView8;
    private ImageView imageView5;
    private List<Song> list_song_detail_album;

    public CustomAdapterDetailAlbum(@NonNull Context context, int resource, @NonNull List<Song> objects) {
        super(context, resource, objects);
        this.list_song_detail_album = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        //inflate layout
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.my_list_song, parent, false); //với layout_my_list_item là cái sườn để ráp dô listview
        }
        //view holder
        textView5 = view.findViewById(R.id.textView5);
        textView8 = view.findViewById(R.id.textView8);
        imageView5 = view.findViewById(R.id.imageView5);
        if (view != null) {
            if(!list_song_detail_album.get(position).getSongImg().equals("unknown")){
                Glide.with(getContext()).load(list_song_detail_album.get(position).getSongImg()).into(imageView5);
            }
            else {
                Glide.with(getContext()).load(R.drawable.music_image).into(imageView5);
            }
            textView5.setText(list_song_detail_album.get(position).getSongName());
            Song song = list_song_detail_album.get(position);
            String singer_name = "";
            for (Singer singer : MainActivity.list_singer) {
                if (song.getIdSinger() != -1) {
                    if (song.getIdSinger() == singer.getIdSinger()) {
                        singer_name = singer.getSingerName();
                    }
                } else {
                    singer_name = "unknown";
                }
            }
            textView8.setText(singer_name);
        }
        return view;
    }
}

