package com.example.app_music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.DAO.DBHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button loginButton;
    private EditText editTextName;
    private EditText editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.loginButton);
        editTextName = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);

        loginButton.setOnClickListener(this);
    }

    public void onLoginClick(View View){
        startActivity(new Intent(this,RegisterActivity.class));
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.loginButton) {
            String username = editTextName.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            if(username.equals("") || password.equals("")) {
                Toast.makeText(this, "Mời nhập đầy đủ username và password", Toast.LENGTH_SHORT).show();
            } else {
                DBHelper db = new DBHelper(this);
                Cursor res = db.checkUserLogin(username,password);
                if(res.getCount()==0) {
                    Toast.makeText(this, "Username/Password nhập sai", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    intent.putExtra("username",username);
                    intent.putExtra("password",password);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }
}
