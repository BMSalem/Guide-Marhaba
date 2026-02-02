package com.example.marhaba;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    ImageView back;
    TextView rdRegister;
    EditText etMail, etPasswd;
    AppCompatButton btLogin;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        back = findViewById(R.id.back);
        rdRegister = findViewById(R.id.rdRegister);
        etMail = findViewById(R.id.etMail);
        etPasswd = findViewById(R.id.etPasswd);
        btLogin = findViewById(R.id.btLogin);

        mAuth = FirebaseAuth.getInstance();

        rdRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(etMail.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(etPasswd.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }

                SignIn(etMail.getText().toString(), etPasswd.getText().toString());
            }
        });
    }

    private void SignIn(String mail, String passwd) {
        mAuth.signInWithEmailAndPassword(mail, passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Authenticated successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, Intro_Activity.class));
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Not connected to internet Or Username or password incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}