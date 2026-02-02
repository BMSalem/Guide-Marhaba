package com.example.marhaba;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marhaba.Adapters.CategoryAdapter;
import com.example.marhaba.Adapters.DestinationsAdapter;
import com.example.marhaba.Dao.Consulting;
import com.example.marhaba.Domains.AttractionDomain;
import com.example.marhaba.Domains.CategoryDomain;
import com.example.marhaba.Domains.DestinationDomain;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserNoAuth extends AppCompatActivity {
    ImageView back;
    private LinearLayout home, profile, assign, setting;
    FirebaseFirestore firestore;
    Consulting consulting;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_no_auth);
            firestore = FirebaseFirestore.getInstance();
            consulting = new Consulting(this);

            home = findViewById(R.id.homeBtn);
            profile = findViewById(R.id.profileBtn);
            assign = findViewById(R.id.assignBtn);
            setting = findViewById(R.id.setBtn);
            back = findViewById(R.id.back);

            Drawable drawable = ContextCompat.getDrawable(this, R.color.gris);
            home.setBackground(drawable);

            // Destinations
            RecyclerView recyclerView = findViewById(R.id.view_dest);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new
                    LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            List<DestinationDomain> destinatons = consulting.cities(recyclerView);

            //Category
            RecyclerView recyclerViewCat = findViewById(R.id.view_cat);
            recyclerViewCat.setHasFixedSize(true);
            recyclerViewCat.setLayoutManager(new
                    LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            List<CategoryDomain> cat = consulting.categories(recyclerViewCat, recyclerView,destinatons);

        editText = findViewById(R.id.editText);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String inputText = editText.getText().toString();
                    DestinationsAdapter destinationAdapter = new DestinationsAdapter(consulting.searchKey(destinatons, inputText), UserNoAuth.this);
                    recyclerView.setAdapter(destinationAdapter);
                    return true;
                }
                return false;
            }
        });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(UserNoAuth.this, MainActivity.class));
                    finish();
                }
            });
            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Drawable drawable = ContextCompat.getDrawable(UserNoAuth.this, R.color.gris);
                    home.setBackground(drawable);
                }
            });

            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(UserNoAuth.this, LoginActivity.class));
                    finish();
                    Toast.makeText(UserNoAuth.this, "Login is required", Toast.LENGTH_SHORT).show();

                }
            });
            assign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(UserNoAuth.this, LoginActivity.class));
                    finish();
                    Toast.makeText(UserNoAuth.this, "Login is required", Toast.LENGTH_SHORT).show();
                }
            });
            setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(UserNoAuth.this, LoginActivity.class));
                    finish();
                    Toast.makeText(UserNoAuth.this, "Login is required", Toast.LENGTH_SHORT).show();
                }
            });

    }
}