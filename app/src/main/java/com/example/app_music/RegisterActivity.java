package com.example.app_music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.DAO.DBHelper;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button registerButton;
    private EditText editTextName;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();

        registerButton = findViewById(R.id.registerButton);
        editTextName = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);

        registerButton.setOnClickListener(this);
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view){
        startActivity(new Intent(this,LoginActivity.class));
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.registerButton) {
            if(editTextName.getText().toString().trim().equals("") || editTextPassword.getText().toString().trim().equals("")) {
                Toast.makeText(this, "Mời nhập username/password để đăng kí", Toast.LENGTH_SHORT).show();
            }
            else {
                DBHelper db = new DBHelper(this);
                String username = editTextName.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                Cursor kt = db.checkUserRegistered(username);
                if(kt.getCount()==0) {
                    String res = db.addRecordUser(username,password,0);
                    Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Username này đã được sử dụng", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}
