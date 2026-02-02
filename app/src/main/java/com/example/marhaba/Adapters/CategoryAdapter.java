package com.example.marhaba.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marhaba.City_Activity;
import com.example.marhaba.Dao.Consulting;
import com.example.marhaba.Domains.CategoryDomain;
import com.example.marhaba.Domains.DestinationDomain;
import com.example.marhaba.R;
import com.example.marhaba.UserNoAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.Objects;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    List<CategoryDomain> categoryDomains;
    RecyclerView recyclerView;
    List<DestinationDomain> dest;
    Context context;
    FirebaseStorage storage;
    Consulting consulting;

    public CategoryAdapter(List<CategoryDomain> categoryDomains, List<DestinationDomain> dest, Context activity, RecyclerView recyclerView) {
        this.categoryDomains = categoryDomains;
        this.context = activity;
        this.storage = FirebaseStorage.getInstance();
        this.dest = dest;
        this.consulting = new Consulting(context);
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.viewholder_category,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        try {
            final CategoryDomain myCat = categoryDomains.get(position);
            holder.titleTxt.setText(myCat.getTitles());
            StorageReference imgRef = storage.getReferenceFromUrl(myCat.getCatUrl());
            imgRef.getBytes(Long.MAX_VALUE).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                @Override
                public void onComplete(@NonNull Task<byte[]> task) {
                    if (task.isSuccessful()) {
                        byte[] bytes = task.getResult();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        holder.picImg.setImageBitmap(bitmap);
                    } else {
                        Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DestinationsAdapter destinationAdapter = new DestinationsAdapter(consulting.searchCat(dest, myCat.getId()), context);
                    recyclerView.setAdapter(destinationAdapter);
                }
            });
        }
        catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return categoryDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTxt;
        ImageView picImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            picImg = itemView.findViewById(R.id.catImg);
        }
    }
}
