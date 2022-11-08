package com.example.app_music;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.custom.CustomAdapterAlbum;
import com.example.custom.CustomAdapterAuthor;
import com.example.model.Album;
import com.example.model.Author;

public class SixActivity extends AppCompatActivity {

    CustomAdapterAlbum customAdapterAlbum = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //Tải dữ liệu lên custom adapter singer
        customAdapterAlbum = new CustomAdapterAlbum(SixActivity.this
                , R.layout.my_list_album, MainActivity.list_album);
        ListView lv = findViewById(R.id.ListView2);
        lv.setAdapter(customAdapterAlbum);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Album album = (Album) lv.getItemAtPosition(position);
                Intent intent = new Intent(SixActivity.this, MainActivity.class);
                intent.putExtra("id",album.getIdAlbum());
                intent.putExtra("name",album.getAlbumName());
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });

    }
}
