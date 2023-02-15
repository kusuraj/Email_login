package com.example.emaillogin;

import static android.text.TextUtils.isEmpty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import com.google.firebase.database.DatabaseError;
import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
 private FirebaseAuth auth;
 ProgressBar bar;
 TextView login;
 EditText contact_No,re_pass;
 public DatabaseReference myRef;
 public FirebaseDatabase database;

 UserInfo userInfo;
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        EditText name = findViewById(R.id.name);
        EditText emailid = findViewById(R.id.emailid);
        EditText pass = findViewById(R.id.pass);
        bar = findViewById(R.id.progressBar);
        login = findViewById(R.id.log);
        contact_No =findViewById(R.id.phone);
        re_pass = findViewById(R.id.re_pass);
        CheckBox checkBox= findViewById(R.id.checkBox);


        Button Sign_in = findViewById(R.id.Sign_in);
        auth = FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUp.this,MainActivity.class);

                Toast.makeText(SignUp.this, "Login your account", Toast.LENGTH_SHORT).show();
                startActivity(i);
                finish();
            }
        });

        database = FirebaseDatabase.getInstance();
        myRef= database.getReference("UserInfo");
        userInfo = new UserInfo();


            Sign_in.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View view) {
                    String emailuser = emailid.getText().toString();
                    String password = pass.getText().toString();
                    String contact = contact_No.getText().toString();
                    String re_password = re_pass.getText().toString();
                    String username = name.getText().toString();

                    if (isEmpty(username) && isEmpty(emailuser) && isEmpty(password) && isEmpty(contact)) {

                        Toast.makeText(SignUp.this, "all fields required", Toast.LENGTH_SHORT).show();

                    }else if (pass.length() < 5) {
                        Toast.makeText(SignUp.this, "Password too short", Toast.LENGTH_SHORT).show();
                    }
//                    }else if(isEmpty(re_password))
//                    {
//                        re_pass.setError("Enter your confirmation password");
//                        if (!re_password.equals(password))
//                        {
//                            Toast.makeText(SignUp.this, "Password do not match", Toast.LENGTH_SHORT).show();
//                        }
//                    }
                    if(!EMAIL_ADDRESS_PATTERN.matcher(emailuser).matches()){
                        Toast.makeText(SignUp.this,"Invalid Email Address",Toast.LENGTH_SHORT).show();
                    }
                    else if(!password.equals(re_password)){
                        Toast.makeText(SignUp.this,"Password Not matching",Toast.LENGTH_SHORT).show();
                    }

                    else if (contact.length() !=10 ) {
                        Toast.makeText(SignUp.this, "Enter valid mobile Number!", Toast.LENGTH_SHORT).show();

                    }
                    else if (!checkBox.isChecked() ) {
                        Toast.makeText(SignUp.this, "Please select terms & Conditions!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        addDatatoFirebase(username, contact);
                        registeruser(emailuser, password);
                    }
                }
            });
        }


    private void addDatatoFirebase(String username, String contact) {
        userInfo.setUserName(username);
        userInfo.setUserContact(contact);
      myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                myRef.setValue(userInfo);

                Toast.makeText(SignUp.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(SignUp.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void registeruser(String emailuser, String password) {
        auth.createUserWithEmailAndPassword(emailuser, password).addOnCompleteListener(SignUp.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    updateUI(user);
                }
                else {
                    updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user !=null){
            bar.setVisibility(View.VISIBLE);
            Intent i = new Intent(SignUp.this, MainActivity.class);

            Toast.makeText(SignUp.this, "Registered Successful!", Toast.LENGTH_SHORT).show();
            startActivity(i);
            finish();
        }
        else{
            bar.setVisibility(View.GONE);
            Toast.makeText(SignUp.this, "Register Failed!", Toast.LENGTH_SHORT).show();
        }
    }
}