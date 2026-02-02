package com.example.marhaba;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Intro_Activity extends AppCompatActivity {
    FirebaseUser mAuth;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intro);

        i = 0;
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (i <= 50) {
                    i++;
                    handler.postDelayed(this, 50);
                } else {
                    handler.removeCallbacks(this);
                    startActivity(new Intent(Intro_Activity.this, UserAuth_Activity.class));
                    finish();
                }
            }
        });

    }
}