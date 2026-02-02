package com.example.marhaba;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;


import com.example.marhaba.Dao.Consulting;
import com.example.marhaba.Domains.AttractionDomain;
import com.example.marhaba.Domains.DestinationDomain;
import com.example.marhaba.Domains.WishListItemDomain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttractionDetails extends AppCompatActivity {
    ImageView placeImage, back;
    TextView titleAttr, rating, time, desc, price, location;
    private LinearLayout home, profile, assign, setting;
    FirebaseStorage storage;
    FirebaseFirestore firestore;
    RatingBar ratingBar;
    AppCompatButton addTo;
    AttractionDomain attraction;
    Consulting consulting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_details);
        storage = FirebaseStorage.getInstance();
        firestore = FirebaseFirestore.getInstance();
        consulting = new Consulting(this);

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
        attraction = (AttractionDomain) intent.getSerializableExtra("attraction");
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
                    Toast.makeText(AttractionDetails.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        titleAttr.setText(attraction.getName());
        rating.setText(""+attraction.getScore());
        ratingBar.setRating((float) attraction.getScore());
        time.setText("9:00 - "+attraction.getClose());
        desc.setText(attraction.getDescription());
        price.setText(attraction.getPrice()+" MAD");


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttractionDetails.this, City_Activity.class);
                intent.putExtra("city1", (DestinationDomain)getIntent().getSerializableExtra("City"));

                startActivity(intent);
                finish();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(AttractionDetails.this, R.color.gris);
                home.setBackground(drawable);
                if(FirebaseAuth.getInstance().getCurrentUser() == null) {
                    startActivity(new Intent(AttractionDetails.this, UserNoAuth.class));
                    finish();
                }
                else {
                    startActivity(new Intent(AttractionDetails.this, UserAuth_Activity.class));
                    finish();
                }
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(AttractionDetails.this, R.color.gris);
                profile.setBackground(drawable);
                if(FirebaseAuth.getInstance().getCurrentUser() == null) {
                    startActivity(new Intent(AttractionDetails.this, LoginActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(AttractionDetails.this, ProfileActivity.class));
                    finish();
                }
            }
        });
        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FirebaseAuth.getInstance().getCurrentUser() == null) {
                    startActivity(new Intent(AttractionDetails.this, LoginActivity.class));
                    finish();
                }
                else{
                    Drawable drawable = ContextCompat.getDrawable(AttractionDetails.this, R.color.gris);
                    assign.setBackground(drawable);
                    startActivity(new Intent(AttractionDetails.this, PlanningActivity.class));
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
                    startActivity(new Intent(AttractionDetails.this, LoginActivity.class));
                    finish();
                }
                else {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(AttractionDetails.this, MainActivity.class));
                    finish();
                }
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AttractionDetails.this, MapsActivity2.class);
                intent1.putExtra("attr", attraction);
                startActivity(intent1);
                finish();
            }
        });

        addTo = findViewById(R.id.addTo);

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            addTo.setVisibility(View.GONE);
        }
        else {
            List<DestinationDomain> dest = consulting.justCities();
            consulting.justwishList(FirebaseAuth.getInstance().getCurrentUser(), dest, addTo, attraction);


        }
        addTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }
    private void showDatePickerDialog() {
        // Récupérer la date actuelle
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Créer le DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AttractionDetails.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Convertir la sélection en un objet Date
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(year, month, dayOfMonth);

                        // Afficher l'AlertDialog avec les boutons "Confirm" et "Return"
                        showConfirmationDialog(selectedCalendar.getTime());
                    }
                },
                year, month, dayOfMonth);

        // Afficher le DatePickerDialog
        datePickerDialog.show();
    }

    private void showConfirmationDialog(Date selectedDate) {
        // Créer un AlertDialog pour inclure les boutons "Confirm" et "Return"
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AttractionDetails.this);
        alertDialogBuilder.setTitle("Confirmer la sélection");
        alertDialogBuilder.setMessage("Voulez-vous confirmer la date sélectionnée ?");
        alertDialogBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Faire quelque chose avec la date sélectionnée (par exemple, enregistrer dans une variable)
                processSelectedDate(selectedDate);
            }
        });
        alertDialogBuilder.setNegativeButton("Return", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Fermer le dialog sans rien faire
                dialog.dismiss();
            }
        });

        // Afficher l'AlertDialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void processSelectedDate(Date date) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        item.put("idCity", ((DestinationDomain)getIntent().getSerializableExtra("City")).getId());
        item.put("idAttr", attraction.getId());
        item.put("date", date);
        firestore.collection("wishlists").add(item);
        startActivity(new Intent(AttractionDetails.this, PlanningActivity.class));
        finish();
    }
}