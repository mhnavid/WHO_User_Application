package com.example.whouserapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.whouserapplication.model.CenterListDetails;

import java.util.ArrayList;
import java.util.List;

public class NearbyCenterListActivity extends AppCompatActivity {

    private List<CenterListDetails> centerListFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_center_list);
        fillCenterList();
        setUpRecyclerView();
    }

    private void fillCenterList(){
        centerListFull = new ArrayList<>();
        centerListFull.add(new CenterListDetails("Center 1", "Location 1", "01792207912", "10:00am - 08:00pm"));
        centerListFull.add(new CenterListDetails("Center 2", "Location 2", "01792207912", "10:00am - 08:00pm"));
        centerListFull.add(new CenterListDetails("Center 3", "Location 3", "01792207912", "10:00am - 08:00pm"));
        centerListFull.add(new CenterListDetails("Center 4", "Location 4", "01792207912", "10:00am - 08:00pm"));
        centerListFull.add(new CenterListDetails("Center 5", "Location 5", "01792207912", "10:00am - 08:00pm"));
        centerListFull.add(new CenterListDetails("Center 6", "Location 6", "01792207912", "10:00am - 08:00pm"));
    }

    private void setUpRecyclerView(){
        RecyclerView centerListRecyclerView = findViewById(R.id.centerListRecyclerView);
        CenterListAdapter centerListAdapter = new CenterListAdapter(getApplicationContext(), centerListFull);
        centerListRecyclerView.setAdapter(centerListAdapter);
        centerListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
