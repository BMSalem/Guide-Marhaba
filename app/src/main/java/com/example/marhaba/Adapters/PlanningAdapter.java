package com.example.marhaba.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marhaba.Add_Attraction_Activity;
import com.example.marhaba.Domains.PlanningDomain;
import com.example.marhaba.PlanningActivity;
import com.example.marhaba.R;

import java.util.ArrayList;

public class PlanningAdapter extends RecyclerView.Adapter<PlanningAdapter.ViewHolder> {

    ArrayList<PlanningDomain> items;
    Context context;

    public PlanningAdapter(ArrayList<PlanningDomain> items, PlanningActivity activity) {

        this.items = items;
        this.context = activity;
    }

    @NonNull
    @Override
    public PlanningAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_plannings,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanningAdapter.ViewHolder holder, int position) {
        final PlanningDomain planning = items.get(position);
        holder.titleTxt.setText(items.get(position).getTitle());
        holder.villeTxt.setText(items.get(position).getDestination());
        holder.scoreTxt.setText(""+ items.get(position).getScore());
        holder.dateTxt.setText("" + items.get(position).getStartDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , Add_Attraction_Activity.class);
                try {
                    intent.putExtra("planning", planning);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }
                catch (Exception e){
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }

                Toast.makeText(context, planning.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTxt,villeTxt,scoreTxt, dateTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTxt=itemView.findViewById(R.id.titletxt);
            villeTxt= itemView.findViewById(R.id.locationtxt);
            scoreTxt= itemView.findViewById(R.id.scoreTxt);
            dateTxt = itemView.findViewById(R.id.datetxt);
        }
    }

}
