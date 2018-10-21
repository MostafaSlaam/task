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

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    EditText emailInput;
    EditText passwordInput;
    EditText confirmPassInput;
    Button login;
    Button register;
    FirebaseAuth auth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        emailInput=(EditText)findViewById(R.id.email);
        passwordInput=(EditText)findViewById(R.id.password);
        confirmPassInput=(EditText)findViewById(R.id.confirmPassword);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        login=(Button)findViewById(R.id.button2);
        login.setOnClickListener(this);
        register=(Button)findViewById(R.id.button);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button2:
                Intent intent=new Intent(SignUp.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.button:
                String UserEmail=emailInput.getText().toString();
                final String UserPassword=passwordInput.getText().toString();
                String confirm=confirmPassInput.getText().toString();
                if (TextUtils.isEmpty(UserEmail)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(UserPassword)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(confirm)) {
                    Toast.makeText(getApplicationContext(), "Enter confirm password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!UserPassword.equals(confirm)) {
                    Toast.makeText(getApplicationContext(), "check your confirm password", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                auth.createUserWithEmailAndPassword(UserEmail, UserPassword)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignUp.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(SignUp.this, DisplayData.class));
                                    finish();
                                }
                            }
                        });

                break;
        }
    }
}
