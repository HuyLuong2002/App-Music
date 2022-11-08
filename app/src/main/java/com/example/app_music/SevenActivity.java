package com.example.app_music;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.custom.CustomAdapterAuthor;
import com.example.custom.CustomAdapterUser;
import com.example.model.Author;
import com.example.model.User;

public class SevenActivity extends AppCompatActivity {
    CustomAdapterUser customAdapterUser = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //Tải dữ liệu lên custom adapter singer
        customAdapterUser = new CustomAdapterUser(SevenActivity.this
                , R.layout.my_list_user, MainActivity.list_all_user);
        ListView lv = findViewById(R.id.ListView2);
        lv.setAdapter(customAdapterUser);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) lv.getItemAtPosition(position);
                Intent intent = new Intent(SevenActivity.this, MainActivity.class);
                if(user.getId_auth() == 0) {
                    intent.putExtra("id",user.getId());
                    intent.putExtra("idAuth",user.getId_auth());
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                } else {
                    Toast.makeText(SevenActivity.this, "Không thể đổi quyền admin", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });

    }
}
