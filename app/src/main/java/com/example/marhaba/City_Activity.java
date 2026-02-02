package com.example.marhaba;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marhaba.Adapters.AttractionAdapter;
import com.example.marhaba.Dao.Consulting;
import com.example.marhaba.Domains.AttractionDomain;
import com.example.marhaba.Domains.DestinationDomain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class City_Activity extends AppCompatActivity {
    ImageView back, placeImg;
    private LinearLayout home, profile, assign, setting;
    TextView cityName, desc, loc;
    FirebaseStorage storage;
    FirebaseFirestore firestore;
    DestinationDomain city;
    Consulting consulting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        storage = FirebaseStorage.getInstance();
        firestore = FirebaseFirestore.getInstance();
        consulting = new Consulting(this);

        home = findViewById(R.id.homeBtn);
        profile = findViewById(R.id.profileBtn);
        assign = findViewById(R.id.assignBtn);
        setting = findViewById(R.id.setBtn);
        placeImg = findViewById(R.id.placeImage);
        cityName = findViewById(R.id.cityName);
        desc = findViewById(R.id.desc);
        loc = findViewById(R.id.loc);

        //Attractions
        RecyclerView recyclerView = findViewById(R.id.view_attract);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new
                LinearLayoutManager(this));


        Intent intent = getIntent();

        if(intent.getSerializableExtra("city1") != null) {
            city = (DestinationDomain) intent.getSerializableExtra("city1");
            StorageReference imgRef = storage.getReferenceFromUrl(city.getImgUrl());
            imgRef.getBytes(Long.MAX_VALUE).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                @Override
                public void onComplete(@NonNull Task<byte[]> task) {
                    if (task.isSuccessful()) {
                        byte[] bytes = task.getResult();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        placeImg.setBackground(new BitmapDrawable(getResources(), bitmap));
                    } else {
                        Toast.makeText(City_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            cityName.setText(city.getTitle());
            desc.setText(city.getDescription());

            AttractionAdapter attractionAdapter = new AttractionAdapter(city.getAttractions(), city,City_Activity.this);
            recyclerView.setAdapter(attractionAdapter);
            intent.removeExtra("city1");
        }

        if(intent.getSerializableExtra("city") != null) {
           city = (DestinationDomain) intent.getSerializableExtra("city");
            StorageReference imgRef = storage.getReferenceFromUrl(city.getImgUrl());
            imgRef.getBytes(Long.MAX_VALUE).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                @Override
                public void onComplete(@NonNull Task<byte[]> task) {
                    if (task.isSuccessful()) {
                        byte[] bytes = task.getResult();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        placeImg.setBackground(new BitmapDrawable(getResources(), bitmap));
                    } else {
                        Toast.makeText(City_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            cityName.setText(city.getTitle());
            desc.setText(city.getDescription());

            AttractionAdapter attractionAdapter = new AttractionAdapter(city.getAttractions(), city,City_Activity.this);
            recyclerView.setAdapter(attractionAdapter);
            intent.removeExtra("city");
        }


        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FirebaseAuth.getInstance().getCurrentUser() == null) {
                    startActivity(new Intent(City_Activity.this, UserNoAuth.class));
                    finish();
                }
                else {
                    startActivity(new Intent(City_Activity.this, UserAuth_Activity.class));
                    finish();
                }
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(City_Activity.this, R.color.gris);
                home.setBackground(drawable);
                if(FirebaseAuth.getInstance().getCurrentUser() == null) {
                    startActivity(new Intent(City_Activity.this, UserNoAuth.class));
                    finish();
                }
                else {
                    startActivity(new Intent(City_Activity.this, UserAuth_Activity.class));
                    finish();
                }
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(City_Activity.this, R.color.gris);
                profile.setBackground(drawable);
                if(FirebaseAuth.getInstance().getCurrentUser() == null) {
                    startActivity(new Intent(City_Activity.this, LoginActivity.class));
                    finish();
                }
                else{
                    startActivity(new Intent(City_Activity.this, ProfileActivity.class));
                    finish();
                }
            }
        });
        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FirebaseAuth.getInstance().getCurrentUser() == null) {
                    startActivity(new Intent(City_Activity.this, LoginActivity.class));
                    finish();
                }
                else {
                    Drawable drawable = ContextCompat.getDrawable(City_Activity.this, R.color.gris);
                    assign.setBackground(drawable);
                    startActivity(new Intent(City_Activity.this, PlanningActivity.class));
                    finish();
                }
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Drawable drawable = ContextCompat.getDrawable(Casablanca.this, R.color.gris);
                //setting.setBackground(drawable);
                if(FirebaseAuth.getInstance().getCurrentUser() == null) {
                    startActivity(new Intent(City_Activity.this, LoginActivity.class));
                    finish();
                }
                else {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(City_Activity.this, MainActivity.class));
                    finish();
                }
            }
        });
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(City_Activity.this, MapsActivity.class);
                intent1.putExtra("city", city);
                startActivity(intent1);
                finish();
            }
        });

        }
    }