package com.example.app_music;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.custom.CustomAdapterDetailAlbum;
import com.example.model.DetailAlbum;
import com.example.model.Song;

import java.util.ArrayList;

public class NineActivity extends AppCompatActivity {

    private CustomAdapterDetailAlbum customAdapterDetailAlbum = null;
    private ListView listView8;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);

        Intent intent = getIntent();
        String album_id = intent.getStringExtra("album_id");
        ArrayList<Song> list_song_detail_album = new ArrayList<>();
        //Xử lý list chứa detail album
        if(MainActivity.list_detail_album.size() > 0) {
            for (DetailAlbum detailAlbum : MainActivity.list_detail_album) {
                if(detailAlbum.getIdAlbum() == Integer.parseInt(album_id.trim())) {
                    int id_song = detailAlbum.getIdSong();
                    if(MainActivity.list_song.size() > 0) {
                        for (Song song : MainActivity.list_song) {
                            if(song.getIdSong() == id_song) {
                                Song song1 = new Song();
                                song1.setSongName(song.getSongName());
                                song1.setIdSong(song.getIdSong());
                                song1.setCheckFavorite(song.getCheckFavorite());
                                song1.setSongPath(song.getSongPath());
                                song1.setSongImg(song.getSongImg());
                                song1.setIdSinger(song.getIdSinger());
                                song1.setIdAuthor(song.getIdAuthor());
                                song1.setLyricPath(song.getLyricPath());
                                list_song_detail_album.add(song1);
                            }
                        }
                    }
                     else {
                         Toast.makeText(this, "Không đọc được dữ liệu bài hát", Toast.LENGTH_SHORT).show();
                         return;
                    }
                }
            }
        }
        else {
            Toast.makeText(this, "Không đọc được dữ liệu album", Toast.LENGTH_SHORT).show();
            return;
        }

        listView8 = findViewById(R.id.ListView8);
        customAdapterDetailAlbum = new CustomAdapterDetailAlbum(NineActivity.this, R.layout.my_list_song, list_song_detail_album);
        listView8.setAdapter(customAdapterDetailAlbum);
    }
}
