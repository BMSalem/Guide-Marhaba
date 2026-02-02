package com.example.marhaba;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    ImageView back;
    EditText etMail, etPasswd, etName, etConfg;
    AppCompatButton btRegister;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    final String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$\\%^&*()_+-]).{8,}$";
    Pattern pattern;
    Matcher matcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);


        back = findViewById(R.id.back);
        etMail = findViewById(R.id.etMail);
        etPasswd = findViewById(R.id.etPasswd);
        etName = findViewById(R.id.etName);
        etConfg = findViewById(R.id.etConfg);
        btRegister = findViewById(R.id.btRegister);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        pattern = Pattern.compile(regex);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                matcher = pattern.matcher(etPasswd.getText().toString());

                if(TextUtils.isEmpty(etMail.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(etName.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(etPasswd.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(etPasswd.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!matcher.matches()){
                    Toast.makeText(getApplicationContext(),"Password must be at least 8 characters, 1 lowercase, 1 uppercase, 1 number, 1 special character",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!etPasswd.getText().toString().equals(etConfg.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Please confirm your password",Toast.LENGTH_SHORT).show();
                    return;
                }

                Register(etName.getText().toString(), etMail.getText().toString(), etPasswd.getText().toString());
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void Register(String name, String mail, String passwd) {
        mAuth.createUserWithEmailAndPassword(mail, passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Map<String, String> user = new HashMap<String, String>();
                    user.put("name", name);
                    user.put("email", mail);
                    firestore.collection("usernames").add(user);

                    Toast.makeText(RegisterActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Not connected to internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}