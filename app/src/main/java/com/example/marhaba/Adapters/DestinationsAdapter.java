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
import com.example.marhaba.Domains.DestinationDomain;
import com.example.marhaba.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class DestinationsAdapter extends RecyclerView.Adapter<DestinationsAdapter.ViewHolder> {
    List<DestinationDomain> destinationDomain;
    Context context;
    FirebaseStorage storage;

    public DestinationsAdapter(List<DestinationDomain> destinationDomain, Context activity) {
        this.destinationDomain = destinationDomain;
        this.context = activity;
        this.storage = FirebaseStorage.getInstance();
    }

    @NonNull
    @Override
    public DestinationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.viewholder_destinations,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationsAdapter.ViewHolder holder, int position) {
        try {
            final DestinationDomain myDest = destinationDomain.get(position);
            holder.titleTxt.setText(myDest.getTitle());
            StorageReference imgRef = storage.getReferenceFromUrl(myDest.getImgUrl());
            imgRef.getBytes(Long.MAX_VALUE).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                @Override
                public void onComplete(@NonNull Task<byte[]> task) {
                    if (task.isSuccessful()) {
                        byte[] bytes = task.getResult();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 800, 750, true);
                        holder.pic.setImageBitmap(scaledBitmap);
                    } else {
                        Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, City_Activity.class);
                    intent.putExtra("city", myDest);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
            });
        }
        catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return destinationDomain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTxt;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTxt=itemView.findViewById(R.id.titletxt);
            pic=itemView.findViewById(R.id.picImg);
        }
    }
}
