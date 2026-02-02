package com.example.marhaba;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ProfileActivity extends AppCompatActivity {
    private ImageView profilePic;
    private TextView name,mail;
    private LinearLayout home, profile, assign, setting;

    private AppCompatButton picButton;
    FirebaseUser user;
    FirebaseFirestore firestore;
    FirebaseStorage storage;
    static FirebaseAuth firebaseAuth;
    ActivityResultLauncher<Intent> imagePickLauncher;
    Uri selectedImageUri;
    String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode()== Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data != null && data.getData()!=null){
                            selectedImageUri = data.getData();
                            setProfilePic(getBaseContext(),selectedImageUri, profilePic);
                            uploadFile(selectedImageUri);
                        }
                    }
                });

        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        name = findViewById(R.id.name);
        mail = findViewById(R.id.mail);
        picButton = findViewById(R.id.picButton);
        profilePic = findViewById(R.id.profilePic);

        firestore.collection("usernames").whereEqualTo("email", user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    name.setText((String)task.getResult().getDocuments().get(0).getData().get("name"));
                    mail.setText((String)task.getResult().getDocuments().get(0).getData().get("email"));
                    imgUrl = (String)task.getResult().getDocuments().get(0).getData().get("img");
                    StorageReference imgRef = storage.getReferenceFromUrl(imgUrl);
                    imgRef.getBytes(Long.MAX_VALUE).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                        @Override
                        public void onComplete(@NonNull Task<byte[]> task) {
                            if (task.isSuccessful()) {
                                byte[] bytes = task.getResult();
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                setProfilePic2(getBaseContext(),bitmap, profilePic);
                            } else {
                                Toast.makeText(ProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
        profile.setBackground(drawable);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(ProfileActivity.this, R.color.gris);
                home.setBackground(drawable);
                startActivity(new Intent(ProfileActivity.this,UserAuth_Activity.class));
                finish();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(ProfileActivity.this, R.color.gris);
                profile.setBackground(drawable);
            }
        });

        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(ProfileActivity.this, R.color.gris);
                assign.setBackground(drawable);
                startActivity(new Intent(ProfileActivity.this, PlanningActivity.class));
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();
            }
        });

        picButton.setOnClickListener((v)->{
            ImagePicker.with(this).cropSquare().compress(512).maxResultSize(512,512)
                    .createIntent(new Function1<Intent, Unit>() {
                        @Override
                        public Unit invoke(Intent intent) {
                            imagePickLauncher.launch(intent);
                            return null;
                        }
                    });
        });

    }

    public static void setProfilePic(Context context, Uri imageUri, ImageView imageView){
        Glide.with(context).load(imageUri).apply(RequestOptions.circleCropTransform()).into(imageView);
    }


    private void uploadFile(Uri imageUri) {
        if (imageUri != null) {
            StorageReference fileReference = storage.getReference().child("profile/" + name.getText().toString() + ".png");

            fileReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ProfileActivity.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(ProfileActivity.this, "No File Selected", Toast.LENGTH_SHORT).show();
        }
    }
    public static void setProfilePic2(Context context, Bitmap bitmap, ImageView imageView) {
        Glide.with(context)
                .asBitmap()  // Indique à Glide de charger l'image en tant que Bitmap
                .load(bitmap)  // Charge le Bitmap fourni
                .apply(RequestOptions.circleCropTransform())  // Applique un effet de crop circulaire
                .into(imageView);  // Charge le Bitmap dans l'ImageView spécifié
    }

}