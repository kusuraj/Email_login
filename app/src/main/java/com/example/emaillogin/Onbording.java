package com.example.emaillogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Onbording extends AppCompatActivity {
Button signIn, login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onbording);
        signIn = findViewById(R.id.btnsign_Up);
      login = findViewById(R.id.log_in);
      login.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent i = new Intent(Onbording.this,MainActivity.class);
              startActivity(i);
              finish();
          }
      });
       signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Onbording.this,SignUp.class);
                startActivity(i);
                finish();
            }
        });
    }
}