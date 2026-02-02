package com.example.marhaba.Dao;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marhaba.Adapters.AttractionAdapter;
import com.example.marhaba.Adapters.CategoryAdapter;
import com.example.marhaba.Adapters.DestinationsAdapter;
import com.example.marhaba.Adapters.WishListItemAdapter;
import com.example.marhaba.City_Activity;
import com.example.marhaba.Domains.AttractionDomain;
import com.example.marhaba.Domains.CategoryDomain;
import com.example.marhaba.Domains.DestinationDomain;
import com.example.marhaba.Domains.WishListItemDomain;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
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
import java.util.stream.Collectors;

public class Consulting {
    FirebaseFirestore firestore;
    FirebaseStorage storage;
    Context context;

    public Consulting(Context context) {
        this.firestore = FirebaseFirestore.getInstance();
        this.context = context;
        this.storage = FirebaseStorage.getInstance();
    }

    public List<CategoryDomain> categories(RecyclerView recyclerViewCat,RecyclerView recyclerView, List<DestinationDomain> dest){
        List<CategoryDomain> cat = new ArrayList<>();
        firestore.collection("categories").whereEqualTo("type", "city").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshots) {
                for (QueryDocumentSnapshot document : snapshots) {
                    Map<String, Object> categ = document.getData();
                    cat.add(new CategoryDomain((long) categ.get("id"), (String) categ.get("title"), (String) categ.get("image"), (String) categ.get("type")));
                }
                CategoryAdapter categoryAdapter = new CategoryAdapter(cat, dest, context, recyclerView);
                recyclerViewCat.setAdapter(categoryAdapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return cat;
    }

    public List<DestinationDomain> cities(RecyclerView recyclerView){
        List<DestinationDomain> destinatons = new ArrayList<>();
        firestore.collection("cities").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshots) {

                for (QueryDocumentSnapshot document : snapshots) {

                    Map<String, Object> city = document.getData();
                    Map<String, Object> detail = (Map<String, Object>) city.get("casablanca");
                    GeoPoint geoPoint = (GeoPoint) detail.get("location");

                    List<AttractionDomain> attractions = new ArrayList<>();

                    if (detail.get("attractions") instanceof List) {
                        for (Map<String, Object> element : (List<Map<String, Object>>) detail.get("attractions")) {
                            GeoPoint geoPoint2 = (GeoPoint) element.get("location");
                            attractions.add(new AttractionDomain((long) element.get("id"), (String) element.get("name"), geoPoint2, Double.parseDouble((String) element.get("score")), Double.parseDouble((String) element.get("price")), (String) element.get("image"), (String) element.get("description"), (String) element.get("close"), (String) element.get("city")));
                        }
                    }
                    destinatons.add(new DestinationDomain((long) city.get("id"), (String) detail.get("title"), geoPoint, (String) detail.get("description"), (String) detail.get("image"), attractions, (long) detail.get("categorie")));

                }
                DestinationsAdapter destinationAdapter = new DestinationsAdapter(destinatons, context);
                recyclerView.setAdapter(destinationAdapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return destinatons;
    }

    public List<DestinationDomain> justCities(){
        List<DestinationDomain> destinatons = new ArrayList<>();
        firestore.collection("cities").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshots) {

                for (QueryDocumentSnapshot document : snapshots) {

                    Map<String, Object> city = document.getData();
                    Map<String, Object> detail = (Map<String, Object>) city.get("casablanca");
                    GeoPoint geoPoint = (GeoPoint) detail.get("location");

                    List<AttractionDomain> attractions = new ArrayList<>();

                    if (detail.get("attractions") instanceof List) {
                        for (Map<String, Object> element : (List<Map<String, Object>>) detail.get("attractions")) {
                            GeoPoint geoPoint2 = (GeoPoint) element.get("location");
                            attractions.add(new AttractionDomain((long) element.get("id"), (String) element.get("name"), geoPoint2, Double.parseDouble((String) element.get("score")), Double.parseDouble((String) element.get("price")), (String) element.get("image"), (String) element.get("description"), (String) element.get("close"), (String) element.get("city")));
                        }
                    }
                    destinatons.add(new DestinationDomain((long) city.get("id"), (String) detail.get("title"), geoPoint, (String) detail.get("description"), (String) detail.get("image"), attractions, (long) detail.get("categorie")));

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return destinatons;
    }

    public List<DestinationDomain> searchKey(List<DestinationDomain> dest, String keyword){
        return dest.stream().filter(d -> d.getTitle().toLowerCase().contains(keyword.toLowerCase())).collect(Collectors.toList());
    }

    public List<DestinationDomain> searchCat(List<DestinationDomain> dest, long id){
        return dest.stream().filter(d -> d.getCategorie()==id).collect(Collectors.toList());
    }

    public AttractionDomain getAttr(long idCity, long idAttr){
        final AttractionDomain[] attr = {null};
        firestore.collection("cities").whereEqualTo("id", idCity).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshots) {
                    Map<String, Object> city = snapshots.getDocuments().get(0).getData();
                    Map<String, Object> detail = (Map<String, Object>) city.get("casablanca");
                    if (detail.get("attractions") instanceof List) {
                        for (Map<String, Object> element : (List<Map<String, Object>>) detail.get("attractions")) {
                            if(((long)element.get("id")) == idAttr){
                                GeoPoint geoPoint2 = (GeoPoint) element.get("location");
                                attr[0] = new AttractionDomain((long) element.get("id"), (String) element.get("name"), geoPoint2, Double.parseDouble((String) element.get("score")), Double.parseDouble((String) element.get("price")), (String) element.get("image"), (String) element.get("description"), (String) element.get("close"), (String) element.get("city"));
                                break;
                            }
                        }
                    }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return attr[0];
    }

    public List<WishListItemDomain> wishList(RecyclerView recyclerView,FirebaseUser user, List<DestinationDomain> dest){
        List<WishListItemDomain> items = new ArrayList<>();
        firestore.collection("wishlists").whereEqualTo("name", user.getEmail()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshots) {
                for(QueryDocumentSnapshot document : snapshots){
                    Map<String, Object> data = document.getData();
                    for(DestinationDomain d : dest){
                        if(d.getId() == (long)data.get("idCity")){
                            for(AttractionDomain a : d.getAttractions()){
                                if(a.getId() == (long)data.get("idAttr")){
                                    items.add(new WishListItemDomain(a, (Timestamp)data.get("date")));
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
                WishListItemAdapter adapterPlan = new WishListItemAdapter(items, (Activity) context);
                recyclerView.setAdapter(adapterPlan);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return items;
    }

    public void justwishList(FirebaseUser user, List<DestinationDomain> dest, AppCompatButton addTo,  AttractionDomain attr){
        List<WishListItemDomain> items = new ArrayList<>();
        firestore.collection("wishlists").whereEqualTo("name", user.getEmail()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshots) {
                for(QueryDocumentSnapshot document : snapshots){
                    Map<String, Object> data = document.getData();
                    for(DestinationDomain d : dest){
                        if(d.getId() == (long)data.get("idCity")){
                            for(AttractionDomain a : d.getAttractions()){
                                if(a.getId() == (long)data.get("idAttr")){
                                    items.add(new WishListItemDomain(a, (Timestamp)data.get("date")));
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
                for (WishListItemDomain w : items){
                    if(attr.equals(w.getAttr())){
                        addTo.setVisibility(View.GONE);
                        Toast.makeText(context, "Already added", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
