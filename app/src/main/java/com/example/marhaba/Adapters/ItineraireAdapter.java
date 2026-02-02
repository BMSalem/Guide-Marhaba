package com.example.marhaba.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marhaba.Add_Attraction_Activity;
import com.example.marhaba.AttractionDetails;
import com.example.marhaba.Domains.ItineraireDomain;
import com.example.marhaba.R;

import java.util.ArrayList;

public class ItineraireAdapter extends RecyclerView.Adapter<ItineraireAdapter.ViewHolder> {

    ArrayList<ItineraireDomain> items;
    Context context;

    public ItineraireAdapter(ArrayList<ItineraireDomain> items, Add_Attraction_Activity activity) {
        this.items = items;
        this.context = activity;
    }

    @NonNull
    @Override
    public ItineraireAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_itineraires,parent,false);
        return new ItineraireAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ItineraireAdapter.ViewHolder holder, int position) {
        ItineraireDomain itineraire = items.get(position);
        holder.titleTxt.setText(itineraire.getAttraction());
        holder.dateTxt.setText(itineraire.getHoraire());
        holder.scoreTxt.setText(""+ itineraire.getPerso_score());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent intent = new Intent(context, AttractionDetails.class);
                        context.startActivity(intent);
                        ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTxt,dateTxt,scoreTxt;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTxt=itemView.findViewById(R.id.titletxt);
            dateTxt= itemView.findViewById(R.id.datetxt);
            scoreTxt= itemView.findViewById(R.id.scoreTxt);
            pic=itemView.findViewById(R.id.picImg);
        }
    }
}
