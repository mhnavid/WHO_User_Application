package com.example.whouserapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whouserapplication.model.CenterListDetails;

import java.util.List;

public class CenterListAdapter  extends
        RecyclerView.Adapter<CenterListAdapter.ViewHolder>{

    private List<CenterListDetails> centerListDetails;

    public CenterListAdapter(List<CenterListDetails> centerListDetails){
        this.centerListDetails = centerListDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View CenterDetailsView = inflater.inflate(R.layout.item_center_details, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(CenterDetailsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CenterListDetails center = this.centerListDetails.get(position);
        ImageView centerImageView;
        TextView textListCenterTitle,
                textListCenterLocation,
                textListCenterMobileNo,
                textListCenterTime,
                textListCenterVexinationTime;
        centerImageView = holder.centerImageView;
        centerImageView.setImageResource(R.drawable.ic_image_black_24dp);
        textListCenterTitle = holder.textListCenterTitle;
        textListCenterTitle.setText(center.getCenterName());
        textListCenterLocation = holder.textListCenterLocation;
        textListCenterLocation.setText(center.getCenterLocation());
        textListCenterMobileNo = holder.textListCenterMobileNo;
        textListCenterMobileNo.setText(center.getCenterPhoneNo());
        textListCenterTime = holder.textListCenterTime;

        textListCenterVexinationTime = holder.textListCenterVexinationTime;
        textListCenterVexinationTime.setText(center.getVaxinationHour());
    }

    @Override
    public int getItemCount() {
        return this.centerListDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView centerImageView;

        private TextView textListCenterTitle,
                textListCenterLocation,
                textListCenterMobileNo,
                textListCenterTime,
                textListCenterVexinationTime;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            centerImageView = itemView.findViewById(R.id.centerImageView);

            textListCenterTitle = itemView.findViewById(R.id.textListCenterTitle);
            textListCenterLocation = itemView.findViewById(R.id.textListCenterLocation);
            textListCenterMobileNo = itemView.findViewById(R.id.textListCenterPhoneNo);
            textListCenterTime = itemView.findViewById(R.id.textListCenterTime);
            textListCenterVexinationTime = itemView.findViewById(R.id.textListCenterVexinationTime);
        }
    }
}