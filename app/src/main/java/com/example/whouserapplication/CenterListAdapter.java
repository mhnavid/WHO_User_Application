package com.example.whouserapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
    private final Context context;

    public CenterListAdapter(Context context, List<CenterListDetails> centerListDetails){
        this.context = context;
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
        final CenterListDetails center = this.centerListDetails.get(position);
        Button btnCall;
        ImageView centerImageView;
        TextView textListCenterTitle,
                textListCenterLocation,
                textListCenterMobileNo,
                textListCenterTime,
                textListCenterVexinationTime;
        centerImageView = holder.centerImageView;
        centerImageView.setImageResource(R.drawable.temp_vaccination_center);
        textListCenterTitle = holder.textListCenterTitle;
        textListCenterTitle.setText(center.getCenterName());
        textListCenterLocation = holder.textListCenterLocation;
        textListCenterLocation.setText(center.getCenterLocation());
        textListCenterMobileNo = holder.textListCenterMobileNo;
        textListCenterMobileNo.setText(center.getCenterPhoneNo());
        textListCenterTime = holder.textListCenterTime;

        textListCenterVexinationTime = holder.textListCenterVexinationTime;
        textListCenterVexinationTime.setText(center.getVaxinationHour());

        btnCall = holder.btnCall;
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialer(center.getCenterPhoneNo().trim());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.centerListDetails.size();
    }

    private void openDialer(String phoneNo){
//        Intent intent = new Intent(Intent.ACTION_DIAL);
//        // Send phone number to intent as data
//        intent.setData(Uri.parse("tel:" + phoneNo));
//        // Start the dialer app activity with number
//        context.startActivity(intent);

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=23.7509,90.3844&daddr=23.752212,90.390325"));
        context.startActivity(intent);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView centerImageView;
        private Button btnCall;

        private TextView textListCenterTitle,
                textListCenterLocation,
                textListCenterMobileNo,
                textListCenterTime,
                textListCenterVexinationTime;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            centerImageView = itemView.findViewById(R.id.centerImageView);
            btnCall = itemView.findViewById(R.id.btnDirection);
            textListCenterTitle = itemView.findViewById(R.id.textListCenterTitle);
            textListCenterLocation = itemView.findViewById(R.id.textListCenterLocation);
            textListCenterMobileNo = itemView.findViewById(R.id.textListCenterPhoneNo);
            textListCenterTime = itemView.findViewById(R.id.textListCenterTime);
            textListCenterVexinationTime = itemView.findViewById(R.id.textListCenterVexinationTime);
        }
    }
}