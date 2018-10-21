package com.example.mostafa.task;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText emailInput;
    EditText passwordInput;
    Button login;
    Button register;
    FirebaseAuth auth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, DisplayData.class));
            finish();
        }
        setContentView(R.layout.activity_main);

        emailInput=(EditText)findViewById(R.id.email);
        passwordInput=(EditText)findViewById(R.id.password);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        login=(Button)findViewById(R.id.button);
        login.setOnClickListener(this);
        register=(Button)findViewById(R.id.button2);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button:
                String UserEmail=emailInput.getText().toString();
                final String UserPassword=passwordInput.getText().toString();
                if (TextUtils.isEmpty(UserEmail)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(UserPassword)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (UserPassword.length() < 6) {
                    Toast.makeText(getApplicationContext(),
                            "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                auth.signInWithEmailAndPassword(UserEmail,UserPassword).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful())
                        {
                            Intent intent = new Intent(MainActivity.this, DisplayData.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            if (UserPassword.length() < 6) {
                                passwordInput.setError("Password must more than 6 characters");
                            } else {
                                Toast.makeText(MainActivity.this, "Authentication failed, check your email and password or sign up",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
                break;

                case R.id.button2:
                        Intent intent=new Intent(MainActivity.this,SignUp.class);
                        startActivity(intent);
                        finish();
                        break;
        }
    }
}
