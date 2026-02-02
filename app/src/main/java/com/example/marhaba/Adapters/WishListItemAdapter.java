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


import com.example.marhaba.Domains.AttractionDomain;
import com.example.marhaba.Domains.WishListItemDomain;
import com.example.marhaba.MyWishlistDetails;
import com.example.marhaba.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class WishListItemAdapter extends RecyclerView.Adapter<WishListItemAdapter.ViewHolder>{
    List<WishListItemDomain> wishListItems;
    Context context;
    FirebaseStorage storage;

    public WishListItemAdapter(List<WishListItemDomain> wishListItems, Activity context) {
        this.wishListItems = wishListItems;
        this.context = context;
        this.storage = FirebaseStorage.getInstance();
    }

    @NonNull
    @Override
    public WishListItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.viewholder_wishlistitem,parent,false);
        WishListItemAdapter.ViewHolder viewHolder = new WishListItemAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WishListItemAdapter.ViewHolder holder, int position) {
        final WishListItemDomain items = wishListItems.get(position);
        final AttractionDomain myAttr = items.getAttr();
        holder.titleTxt.setText(myAttr.getName());
        holder.locationTxt.setText(myAttr.getCity());
        String date = items.getDate().toDate().toString();
        holder.scoreTxt.setText(date.split(" ")[0]+" "+date.split(" ")[1]+" "+date.split(" ")[2]);
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
                    Intent intent = new Intent(context, MyWishlistDetails.class);
                    intent.putExtra("attraction", myAttr);
                    context.startActivity(intent);

                }
                catch (Exception e) {
                    String err = e.getMessage();
                    Toast.makeText(context, err.split("/" )[1], Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return wishListItems.size();
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
