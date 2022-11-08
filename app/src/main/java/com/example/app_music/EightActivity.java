package com.example.app_music;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.custom.CustomAdapterSong;
import com.example.model.Song;

public class EightActivity extends AppCompatActivity {
    CustomAdapterSong customAdapterSong = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //Tải dữ liệu lên custom adapter song
        customAdapterSong = new CustomAdapterSong(EightActivity.this
                , R.layout.my_list_song, MainActivity.list_song);
        ListView lv = findViewById(R.id.ListView2);
        lv.setAdapter(customAdapterSong);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song song = (Song) lv.getItemAtPosition(position);
                Intent intent = new Intent(EightActivity.this, MainActivity.class);
                intent.putExtra("idSong",song.getIdSong());

                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });

    }
}
