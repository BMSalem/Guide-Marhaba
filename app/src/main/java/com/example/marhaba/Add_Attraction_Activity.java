package com.example.marhaba;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marhaba.Adapters.ItineraireAdapter;
import com.example.marhaba.Domains.ItineraireDomain;
import com.example.marhaba.Domains.PlanningDomain;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Add_Attraction_Activity extends AppCompatActivity {

    private RecyclerView.Adapter adapterItin;
    private RecyclerView recyclerViewItin;

    private TextView titleAttr, rating, startDt, endDt, location, comment;
    private RatingBar ratingBar;
    private ImageView back;
    private LinearLayout home, profile, assign, setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attraction);

        home = findViewById(R.id.homeBtn);
        profile = findViewById(R.id.profileBtn);
        assign = findViewById(R.id.assignBtn);
        setting = findViewById(R.id.setBtn);


        titleAttr = findViewById(R.id.titleAttr);
        rating = findViewById(R.id.rating);
        startDt = findViewById(R.id.startDt);
        endDt = findViewById(R.id.endDt);
        location = findViewById(R.id.location);
        ratingBar = findViewById(R.id.ratingBar);
        comment = findViewById(R.id.comment);

        Intent intent = getIntent();
        PlanningDomain planningDomain = (PlanningDomain) intent.getSerializableExtra("planning");
        titleAttr.setText(planningDomain.getTitle());
        rating.setText("" + planningDomain.getScore());
        ratingBar.setRating((float) planningDomain.getScore());
        startDt.setText(planningDomain.getStartDate().toString());
        endDt.setText(planningDomain.getEndDate().toString());
        location.setText(planningDomain.getDestination());
        comment.setText(planningDomain.getComment());

        ArrayList<ItineraireDomain> items = new ArrayList<>();

        recyclerViewItin = findViewById(R.id.recyclerViewItin);
        recyclerViewItin.setHasFixedSize(true);
        recyclerViewItin.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterItin = new ItineraireAdapter(items, this);
        recyclerViewItin.setAdapter(adapterItin);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Add_Attraction_Activity.this, PlanningActivity.class));
                finish();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(Add_Attraction_Activity.this, R.color.gris);
                home.setBackground(drawable);
                startActivity(new Intent(Add_Attraction_Activity.this, UserAuth_Activity.class));
                finish();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Drawable drawable = ContextCompat.getDrawable(Add_Attraction_Activity.this, R.color.gris);
                //profile.setBackground(drawable);

            }
        });
        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(Add_Attraction_Activity.this, R.color.gris);
                assign.setBackground(drawable);
                startActivity(new Intent(Add_Attraction_Activity.this, UserAuth_Activity.class));
                finish();
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Drawable drawable = ContextCompat.getDrawable(Add_Attraction_Activity.this, R.color.gris);
                //setting.setBackground(drawable);
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Add_Attraction_Activity.this, MainActivity.class));
                finish();
            }
        });

    }
}