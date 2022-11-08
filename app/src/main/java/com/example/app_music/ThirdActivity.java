package com.example.app_music;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.custom.CustomAdapterSinger;
import com.example.model.Singer;

public class ThirdActivity extends AppCompatActivity {
    CustomAdapterSinger customAdapterSinger = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //Tải dữ liệu lên custom adapter singer
        customAdapterSinger = new CustomAdapterSinger(ThirdActivity.this
                , R.layout.my_list_singer, MainActivity.list_singer);
        ListView lv = findViewById(R.id.ListView2);
        lv.setAdapter(customAdapterSinger);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Singer singer = (Singer) lv.getItemAtPosition(position);
                Intent intent = new Intent(ThirdActivity.this, MainActivity.class);
                intent.putExtra("idSinger",singer.getIdSinger());
                intent.putExtra("singerName",singer.getSingerName());
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });

    }
}
