package com.example.app_music;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.custom.CustomAdapterAuthor;
import com.example.custom.CustomAdapterSinger;
import com.example.model.Author;
import com.example.model.Singer;

public class FiveActivity extends AppCompatActivity {
    CustomAdapterAuthor customAdapterAuthor = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //Tải dữ liệu lên custom adapter singer
        customAdapterAuthor = new CustomAdapterAuthor(FiveActivity.this
                , R.layout.my_list_author, MainActivity.list_author);
        ListView lv = findViewById(R.id.ListView2);
        lv.setAdapter(customAdapterAuthor);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Author author = (Author) lv.getItemAtPosition(position);
                Intent intent = new Intent(FiveActivity.this, MainActivity.class);
                intent.putExtra("idAuthor",author.getIdAuthor());
                intent.putExtra("authorName",author.getAuthorName());
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });

    }
}
