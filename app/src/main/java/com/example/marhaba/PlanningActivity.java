package com.example.marhaba;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.marhaba.Adapters.PlanningAdapter;
import com.example.marhaba.Adapters.WishListItemAdapter;
import com.example.marhaba.Dao.Consulting;
import com.example.marhaba.Domains.DestinationDomain;
import com.example.marhaba.Domains.PlanningDomain;
import com.example.marhaba.Domains.WishListItemDomain;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlanningActivity extends AppCompatActivity {
    private ImageView back;
    private LinearLayout home, profile, assign, setting;
    Consulting consulting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);
        consulting = new Consulting(this);

        home = findViewById(R.id.homeBtn);
        profile = findViewById(R.id.profileBtn);
        assign = findViewById(R.id.assignBtn);
        setting = findViewById(R.id.setBtn);


        Drawable drawable = ContextCompat.getDrawable(this, R.color.gris);
        assign.setBackground(drawable);


        RecyclerView recyclerView = findViewById(R.id.recycleViewPlan);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        List<DestinationDomain> cities = consulting.justCities();

        List<WishListItemDomain> items = consulting.wishList(recyclerView,FirebaseAuth.getInstance().getCurrentUser(), cities);



        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlanningActivity.this, UserAuth_Activity.class));
                finish();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(PlanningActivity.this, R.color.gris);
                home.setBackground(drawable);
                startActivity(new Intent(PlanningActivity.this, UserAuth_Activity.class));
                finish();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(PlanningActivity.this, R.color.gris);
                profile.setBackground(drawable);
                startActivity(new Intent(PlanningActivity.this, ProfileActivity.class));
                finish();

            }
        });
        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(PlanningActivity.this, R.color.gris);
                assign.setBackground(drawable);

            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Drawable drawable = ContextCompat.getDrawable(PlanningActivity.this, R.color.gris);
                //setting.setBackground(drawable);
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(PlanningActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}