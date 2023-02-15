package com.example.emaillogin;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    ProgressBar progressBar;
    TextView  forget_pass;
    CheckBox rememberme;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        EditText email = findViewById(R.id.email);

        EditText password = findViewById(R.id.password);
   
        Button login = findViewById(R.id.login);

        auth = FirebaseAuth.getInstance();


        progressBar = findViewById(R.id.progressBar2);


        rememberme = findViewById(R.id.remembeMe);
        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String mail = sharedPreferences.getString("email", "");
        String passwords = sharedPreferences.getString("password", "");


        email.setText(mail);
        password.setText(passwords);

        rememberme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rememberme.isChecked()) {
                    editor.putString("email", email.getText().toString());
                    editor.putString("password", password.getText().toString());
                    Toast.makeText(MainActivity.this, "Checked", Toast.LENGTH_SHORT).show();
                    editor.commit();
                }else if(!rememberme.isChecked()) {
                    editor.putString("email", email.getText().toString());
                    editor.putString("password", password.getText().toString());
                    Toast.makeText(MainActivity.this, "UnChecked", Toast.LENGTH_SHORT).show();
                    editor.commit();
                }
            }});









        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = password.getText().toString();
                String useremail = email.getText().toString();

                if (TextUtils.isEmpty(useremail) && TextUtils.isEmpty(pass)) {
                    Toast.makeText(MainActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();


                } else {
                    loginUser(useremail, pass);
                }
            }
        });

    }

    private void loginUser(String useremail, String pass) {
        auth.signInWithEmailAndPassword(useremail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(MainActivity.this, HomeScreen.class);
                            i.putExtra("username", auth.getCurrentUser().getEmail());
                            i.putExtra("Uid", auth.getCurrentUser().getUid());
                            startActivity(i);
                            finish();

                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, "Invalid email/password", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
    @Override
    protected void onStart() {
        super.onStart();
        forget_pass = findViewById(R.id.forget);

        TextView signup = findViewById(R.id.signup);
        
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SignUp.class);
                startActivity(i);
                finish();
                Toast.makeText(MainActivity.this, "Register yourself!", Toast.LENGTH_SHORT).show();
            }
        });

        forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ForgetPassword.class);
                startActivity(i);
                finish();
            }
        });

    }

}




