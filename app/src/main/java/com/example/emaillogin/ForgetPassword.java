package com.example.emaillogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;



public class ForgetPassword extends AppCompatActivity {
    ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        TextView back = findViewById(R.id.back);
        progressBar = findViewById(R.id.progressbar);
        auth=FirebaseAuth.getInstance();
        Button btn_forgot= findViewById(R.id.btn_forget);
        EditText edt_mail= findViewById(R.id.edt_email);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(ForgetPassword.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
//        getSupportActionBar().setTitle("Forget Password");

        btn_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String send_email= edt_mail.getText().toString();
                if(TextUtils.isEmpty(send_email)){
                    Toast.makeText(ForgetPassword.this, "Enter your registered email", Toast.LENGTH_SHORT).show();
                    edt_mail.setError("Email is required");
                    edt_mail.requestFocus();
                }if(!Patterns.EMAIL_ADDRESS.matcher(send_email).matches()) {
                    Toast.makeText(ForgetPassword.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                    edt_mail.setError("valid email is required");
                    edt_mail.requestFocus();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    resetPassword(send_email);
                }

            }
        });

    }

    private void resetPassword(String send_email) {
      auth =FirebaseAuth.getInstance();
auth.sendPasswordResetEmail(send_email).addOnCompleteListener(new OnCompleteListener<Void>() {
    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if(task.isSuccessful()){
            Toast.makeText(ForgetPassword.this, "Please check your inbox for password reset link", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ForgetPassword.this,MainActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }
        else {
            Toast.makeText(ForgetPassword.this, "Something went wrong", Toast.LENGTH_SHORT).show();

        }
        progressBar.setVisibility(View.GONE);
    }
});
    }
}