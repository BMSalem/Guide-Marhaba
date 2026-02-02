package com.example.marhaba;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.marhaba.Adapters.CategoryAdapter;
import com.example.marhaba.Adapters.DestinationsAdapter;
import com.example.marhaba.Dao.Consulting;
import com.example.marhaba.Domains.AttractionDomain;
import com.example.marhaba.Domains.CategoryDomain;
import com.example.marhaba.Domains.DestinationDomain;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserAuth_Activity extends AppCompatActivity {
    private RecyclerView.Adapter adapterDestination, adapterCat;
    private RecyclerView recyclerViewDestination, recyclerViewCat;
    private TextView name;
    private ImageView imgProfile;
    private LinearLayout home, profile, assign, setting;
    FirebaseUser user;
    Consulting consulting;
    FirebaseFirestore firestore;
    FirebaseStorage storage;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_auth);
        consulting = new Consulting(this);
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        name = findViewById(R.id.name);
        imgProfile = findViewById(R.id.imageView4);

        firestore.collection("usernames").whereEqualTo("email", user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    name.setText((String)task.getResult().getDocuments().get(0).getData().get("name"));
                    String imgUrl = (String)task.getResult().getDocuments().get(0).getData().get("img");
                    StorageReference imgRef = storage.getReferenceFromUrl(imgUrl);
                    imgRef.getBytes(Long.MAX_VALUE).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                        @Override
                        public void onComplete(@NonNull Task<byte[]> task) {
                            if (task.isSuccessful()) {
                                byte[] bytes = task.getResult();
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                                setProfilePic2(getBaseContext(),scaledBitmap, imgProfile);
                            } else {
                                Toast.makeText(UserAuth_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        home = findViewById(R.id.homeBtn);
        profile = findViewById(R.id.profileBtn);
        assign = findViewById(R.id.assignBtn);
        setting = findViewById(R.id.setBtn);

        Drawable drawable = ContextCompat.getDrawable(this, R.color.gris);
        home.setBackground(drawable);

        // Destinations
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
                    DestinationsAdapter destinationAdapter = new DestinationsAdapter(consulting.searchKey(destinatons, inputText), UserAuth_Activity.this);
                    recyclerView.setAdapter(destinationAdapter);
                    return true;
                }
                return false;
            }
        });



        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(UserAuth_Activity.this, R.color.gris);
                home.setBackground(drawable);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(UserAuth_Activity.this, R.color.gris);
                profile.setBackground(drawable);
                startActivity(new Intent(UserAuth_Activity.this, ProfileActivity.class));
                finish();

            }
        });
        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(UserAuth_Activity.this, R.color.gris);
                assign.setBackground(drawable);
                startActivity(new Intent(UserAuth_Activity.this, PlanningActivity.class));
                finish();
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Drawable drawable = ContextCompat.getDrawable(UserAuth_Activity.this, R.color.gris);
                //setting.setBackground(drawable);
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(UserAuth_Activity.this, MainActivity.class));
                finish();
            }
        });
    }
    public static void setProfilePic2(Context context, Bitmap bitmap, ImageView imageView) {
        Glide.with(context)
                .asBitmap()  // Indique à Glide de charger l'image en tant que Bitmap
                .load(bitmap)  // Charge le Bitmap fourni
                .apply(RequestOptions.circleCropTransform())  // Applique un effet de crop circulaire
                .into(imageView);  // Charge le Bitmap dans l'ImageView spécifié
    }
}