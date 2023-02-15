package com.example.emaillogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        TextView username = findViewById(R.id.username);
        String name = ("Email: "+getIntent().getStringExtra("username").toString());
        username.setText(name);

        TextView user = findViewById(R.id.uid);
        String Uid = ("UID: "+getIntent().getStringExtra("Uid").toString());
        user.setText(Uid);
        Button logout = findViewById(R.id.button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeScreen.this,MainActivity.class);
                startActivity(i);
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(HomeScreen.this, "Logout Successful!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}