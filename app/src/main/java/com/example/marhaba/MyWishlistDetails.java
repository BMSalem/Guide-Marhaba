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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.marhaba.Domains.AttractionDomain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MyWishlistDetails extends AppCompatActivity {
    ImageView placeImage, back;
    TextView titleAttr, rating, time, desc, price, location;
    private LinearLayout home, profile, assign, setting;
    FirebaseStorage storage;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wishlist_details);
        storage = FirebaseStorage.getInstance();

        placeImage = findViewById(R.id.placeImage);
        titleAttr = findViewById(R.id.titleAttr);
        rating = findViewById(R.id.rating);
        back = findViewById(R.id.back);
        ratingBar = findViewById(R.id.ratingBar);
        time = findViewById(R.id.time);
        desc = findViewById(R.id.desc);
        price = findViewById(R.id.price);
        location = findViewById(R.id.location);

        home = findViewById(R.id.homeBtn);
        profile = findViewById(R.id.profileBtn);
        assign = findViewById(R.id.assignBtn);
        setting = findViewById(R.id.setBtn);

        Intent intent = getIntent();
        AttractionDomain attraction = (AttractionDomain) intent.getSerializableExtra("attraction");
        StorageReference imgRef = storage.getReferenceFromUrl(attraction.getImgUrl());
        imgRef.getBytes(Long.MAX_VALUE).addOnCompleteListener(new OnCompleteListener<byte[]>() {
            @Override
            public void onComplete(@NonNull Task<byte[]> task) {
                if(task.isSuccessful()){
                    byte[] bytes = task.getResult();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    placeImage.setBackground(new BitmapDrawable(getResources(), bitmap));
                }
                else {
                    Toast.makeText(MyWishlistDetails.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        titleAttr.setText(attraction.getName());
        rating.setText(""+attraction.getScore());
        ratingBar.setRating((float) attraction.getScore());
        time.setText("9:00 - "+attraction.getClose());
        desc.setText(attraction.getDescription());
        price.setText(attraction.getPrice()+"MAD");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyWishlistDetails.this, PlanningActivity.class);
                startActivity(intent);
                finish();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(MyWishlistDetails.this, R.color.gris);
                home.setBackground(drawable);
                if(FirebaseAuth.getInstance().getCurrentUser() == null) {
                    startActivity(new Intent(MyWishlistDetails.this, UserNoAuth.class));
                    finish();
                }
                else {
                    startActivity(new Intent(MyWishlistDetails.this, UserAuth_Activity.class));
                    finish();
                }
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Drawable drawable = ContextCompat.getDrawable(AttractionDetails.this, R.color.gris);
                //profile.setBackground(drawable);
                if(FirebaseAuth.getInstance().getCurrentUser() == null) {
                    startActivity(new Intent(MyWishlistDetails.this, LoginActivity.class));
                    finish();
                }
            }
        });
        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FirebaseAuth.getInstance().getCurrentUser() == null) {
                    startActivity(new Intent(MyWishlistDetails.this, LoginActivity.class));
                    finish();
                }
                else{
                    Drawable drawable = ContextCompat.getDrawable(MyWishlistDetails.this, R.color.gris);
                    assign.setBackground(drawable);
                    startActivity(new Intent(MyWishlistDetails.this, PlanningActivity.class));
                    finish();
                }
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Drawable drawable = ContextCompat.getDrawable(AttractionDetails.this, R.color.gris);
                //setting.setBackground(drawable);
                if(FirebaseAuth.getInstance().getCurrentUser() == null) {
                    startActivity(new Intent(MyWishlistDetails.this, LoginActivity.class));
                    finish();
                }
                else {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MyWishlistDetails.this, MainActivity.class));
                    finish();
                }
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MyWishlistDetails.this, MapsActivity3.class);
                intent1.putExtra("attr", attraction);
                startActivity(intent1);
                finish();
            }
        });
    }
}