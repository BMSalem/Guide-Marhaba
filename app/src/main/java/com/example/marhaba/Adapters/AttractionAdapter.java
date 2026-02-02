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

import com.example.marhaba.AttractionDetails;
import com.example.marhaba.Domains.AttractionDomain;
import com.example.marhaba.Domains.DestinationDomain;
import com.example.marhaba.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.ViewHolder> {

    List<AttractionDomain> attractionDomains;
    Context context;
    FirebaseStorage storage;
    DestinationDomain city;

    public AttractionAdapter(List<AttractionDomain> attractionDomains, DestinationDomain city,Activity activity) {
        this.attractionDomains=attractionDomains;
        this.context= activity;
        this.storage = FirebaseStorage.getInstance();
        this.city = city;
    }

    @NonNull
    @Override
    public AttractionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.viewholder_attractions,parent,false);
        AttractionAdapter.ViewHolder viewHolder = new AttractionAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AttractionAdapter.ViewHolder holder, int position) {
        final AttractionDomain myAttr = attractionDomains.get(position);
        holder.titleTxt.setText(myAttr.getName());
        holder.locationTxt.setText(myAttr.getCity());
        holder.scoreTxt.setText(""+ myAttr.getScore());
        StorageReference imgRef = storage.getReferenceFromUrl(myAttr.getImgUrl());
        imgRef.getBytes(Long.MAX_VALUE).addOnCompleteListener(new OnCompleteListener<byte[]>() {
            @Override
            public void onComplete(@NonNull Task<byte[]> task) {
                if(task.isSuccessful()){
                    byte[] bytes = task.getResult();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 800, 750, true);
                    holder.pic.setImageBitmap(scaledBitmap);
                }
                else {
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(context, AttractionDetails.class);
                    intent.putExtra("attraction", myAttr);
                    intent.putExtra("City", city);
                    context.startActivity(intent);
                }
                catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return attractionDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTxt,locationTxt,scoreTxt;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTxt=itemView.findViewById(R.id.titletxt);
            locationTxt= itemView.findViewById(R.id.locationtxt);
            scoreTxt= itemView.findViewById(R.id.scoreTxt);
            pic=itemView.findViewById(R.id.picImg);
        }
    }
}
