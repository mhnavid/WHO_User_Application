package com.example.whouserapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.whouserapplication.LocaleManager;
import com.example.whouserapplication.R;
import com.example.whouserapplication.api.ApiClient;
import com.example.whouserapplication.api.ApiInterface;
import com.example.whouserapplication.model.Center;
import com.example.whouserapplication.model.ImageFind;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceDetailsActivity extends AppCompatActivity {

    ImageView centerImageView;
    TextView textCenterTitle, textCenterLocation, contactPersonNameText, contactPersonContactNoText,
            textDaysNineToThree, textTimeNineToThree, textDaysFiveToSeven, textTimeFiveToSeven,
            textOrganization, commentText, vaccinatorNameText, vaccinatorContactNoText;
    Button btnContactCall, btnDirection, btnVaccinatorCall, btnComment;

    private static ApiInterface apiInterface;
    private Center center;
    private ProgressDialog progressDialog;
    private ImagePopup imagePopup;
    private String currentLatLong, directionLatLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        getSupportActionBar().setTitle(R.string.app_name);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        center = (Center) bundle.getSerializable("center");
        currentLatLong = intent.getStringExtra("currentLat")
                + "," + intent.getStringExtra("currentLong");
        directionLatLong = center.getCenterLatitude()
                + "," + center.getCenterLongitude();


        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        imagePopup = new ImagePopup(this);

        imagePopup.setBackgroundColor(Color.BLACK);
        imagePopup.setHideCloseIcon(true);
        imagePopup.setImageOnClickClose(true);

        centerImageView = findViewById(R.id.centerImageView);
        commentText = findViewById(R.id.commentText);
        commentText.setFocusable(false);

        btnContactCall = findViewById(R.id.btnContactCall);
        btnDirection = findViewById(R.id.btnDirection);

        textCenterTitle = findViewById(R.id.textCenterTitle);
        textCenterLocation = findViewById(R.id.textCenterLocation);
        contactPersonNameText = findViewById(R.id.contactPersonNameText);
        contactPersonContactNoText = findViewById(R.id.contactPersonContactNoText);
        textDaysNineToThree = findViewById(R.id.textDaysNineToThree);
        textTimeNineToThree = findViewById(R.id.textTimeNineToThree);
        textDaysFiveToSeven = findViewById(R.id.textDaysFiveToSeven);
        textTimeFiveToSeven = findViewById(R.id.textTimeFiveToSeven);
        textOrganization = findViewById(R.id.textOrganization);
        vaccinatorNameText = findViewById(R.id.vaccinatorNameText);
        vaccinatorContactNoText = findViewById(R.id.vaccinatorContactNoText);
        btnVaccinatorCall = findViewById(R.id.btnVaccinatorCall);
        btnComment = findViewById(R.id.btnComment);

        centerImageView.setImageResource(R.drawable.ic_image_black_24dp);

        commentText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                commentText.requestFocus();
                commentText.setFocusableInTouchMode(true);
                return false;
            }
        });

        setImage();

        Log.d("centerID",String.valueOf(center.getCenterID()));

        String language = String.valueOf(LocaleManager.getLocale(getResources()));
        if (language.equals("bn")) {
            textCenterTitle.setText(center.getCenterNameBn());
            textCenterLocation.setText(center.getCenterLocationBn());
            if (center.getContactPersonContactNo().equals("")){
                LinearLayout linearLayout = findViewById(R.id.contactPersonLayout);
                linearLayout.setVisibility(View.INVISIBLE);
            }
            else {
                contactPersonNameText.setText("যোগাযোগ ব্যক্তি");
                contactPersonContactNoText.setText(center.getContactPersonContactNoBn());
            }
            if (center.getVaccinatorContactNo().equals("")){
                LinearLayout contactVaccinatorLayout = findViewById(R.id.contactVaccinatorLayout);
                contactVaccinatorLayout.setVisibility(View.INVISIBLE);
            } else {
                vaccinatorNameText.setText("টীকাদান কর্মী");
                vaccinatorContactNoText.setText(center.getVaccinatorContactNoBn());
            }
            if (!center.getDaysNineToThree().equals("")){
                if (!getWeekDay(center.getDaysNineToThree()).equals("")){
                    textDaysNineToThree.setText(getWeekDay(center.getDaysNineToThree()));
                    textTimeNineToThree.setText("সকাল ৯টা থেকে দুপুর ৩টা");
                }
            }
            if (!center.getDaysFiveToSeven().equals("")){
                if (!getWeekDay(center.getDaysFiveToSeven()).equals("")){
                    textDaysFiveToSeven.setText(getWeekDay(center.getDaysFiveToSeven()));
                    textTimeFiveToSeven.setText("বিকেল ৫টা থেকে সন্ধ্যা ৭টা");
                }
            }
            textOrganization.setText(center.getOrganizedBn());
        }
        else {
            textCenterTitle.setText(center.getCenterName());
            textCenterLocation.setText(center.getCenterLocation());
            if (center.getContactPersonContactNo().equals("")){
                LinearLayout linearLayout = findViewById(R.id.contactPersonLayout);
                linearLayout.setVisibility(View.INVISIBLE);
            }
            else {
                contactPersonNameText.setText("Contact Person");
                contactPersonContactNoText.setText(center.getContactPersonContactNo());
            }

            if (center.getVaccinatorContactNo().equals("")){
                LinearLayout contactVaccinatorLayout = findViewById(R.id.contactVaccinatorLayout);
                contactVaccinatorLayout.setVisibility(View.INVISIBLE);
            } else {
                vaccinatorNameText.setText("Vaccinator");
                vaccinatorContactNoText.setText(center.getVaccinatorContactNo());
            }

            if (!center.getDaysNineToThree().equals("")){
                if (!getWeekDay(center.getDaysNineToThree()).equals("")){
                    textDaysNineToThree.setText(getWeekDay(center.getDaysNineToThree()));
                    textTimeNineToThree.setText("09:00am - 03:00pm");
                }
            }
            if (!center.getDaysFiveToSeven().equals("")){
                if (!getWeekDay(center.getDaysFiveToSeven()).equals("")){
                    textDaysFiveToSeven.setText(getWeekDay(center.getDaysFiveToSeven()));
                    textTimeFiveToSeven.setText("05:00pm - 07:00pm");
                }
            }
            textOrganization.setText(center.getOrganized());
        }

        centerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePopup.initiatePopup(centerImageView.getDrawable());
                imagePopup.viewPopup();
            }
        });

        btnDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGoogleMap(currentLatLong, directionLatLong);
            }
        });

        btnContactCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialer(center.getContactPersonContactNo());
            }
        });

        btnVaccinatorCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialer(center.getVaccinatorContactNo());
            }
        });

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentText.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(), "Please write your feedback", Toast.LENGTH_LONG).show();
                } else {
                    sendCommentToServer();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_language, menu);
        MenuItem item = menu.findItem(R.id.btnLoad);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.local_english:
                setNewLocale(this, LocaleManager.ENGLISH);
                return true;
            case R.id.local_Bengali:
                setNewLocale(this, LocaleManager.BENGALI);
                Log.d("bengali",String.valueOf(LocaleManager.getLocale(getResources())));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleManager.setLocale(newBase));
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this);
    }

    private void setNewLocale(AppCompatActivity mContext, @LocaleManager.LocaleDef String language) {
        LocaleManager.setNewLocale(this, language);
        Intent intent = mContext.getIntent();
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    private void openDialer(String no){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        // Send phone number to intent as data
        intent.setData(Uri.parse("tel:+88" + no.trim()));
        // Start the dialer app activity with number
        startActivity(intent);
    }

    private void  openGoogleMap(String currentLatLong, String directionLatLong){
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr="+currentLatLong+"&daddr="+directionLatLong));
        getApplicationContext().startActivity(intent);
    }

    private void setImage(){

        progressDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("centerId", center.getCenterID());
        Call<ImageFind> call = apiInterface.findImage(jsonObject);
        call.enqueue(new Callback<ImageFind>() {
            @Override
            public void onResponse(Call<ImageFind> call, Response<ImageFind> response) {
                if (response.code() == 200){
                    assert response.body() != null;
                    if (!response.body().getImageLocation().equals("") || response.body().getImageLocation() != null){
                        String[] imagePath = response.body().getImageLocation().split("/");
                        final String webPath = "http://18.140.146.240:9001/public/images/" + imagePath[imagePath.length-1];

                        Picasso.get().load(webPath)
                                .placeholder(R.drawable.ic_image_black_24dp)
                                .error(R.drawable.ic_image_black_24dp)
                                .into(centerImageView);
                        progressDialog.dismiss();
                    }
                    else {
                        centerImageView.setImageResource(R.drawable.ic_image_black_24dp);
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<ImageFind> call, Throwable t) {
                centerImageView.setImageResource(R.drawable.ic_image_black_24dp);
                progressDialog.dismiss();
            }
        });
    }

    private String getWeekDay(String string){
        String language = String.valueOf(LocaleManager.getLocale(getResources()));
        StringBuilder weekDay = new StringBuilder();
        weekDay.append("");
        String[] splitStrings = string.split(",");
        for (String splitString : splitStrings) {
            if (splitString.length() == 2) {
                char[] chars = splitString.trim().toCharArray();
                if (language.equals("bn")) {
                    switch (chars[0]) {
                        case '0':
                            weekDay.append("প্রতি ");
                            break;
                        case '1':
                            weekDay.append("১ম সপ্তাহের ");
                            break;
                        case '2':
                            weekDay.append("২য় সপ্তাহের ");
                            break;
                        case '3':
                            weekDay.append("৩য় সপ্তাহের ");
                            break;
                        case '4':
                            weekDay.append("৪র্থ সপ্তাহের ");
                            break;
                        case '5':
                            weekDay.append("৫ম সপ্তাহের ");
                            break;
                        default:
                            break;
                    }

                    switch (chars[1]) {
                        case '1':
                            weekDay.append("শনিবার, ");
                            break;
                        case '2':
                            weekDay.append("রবিবার, ");
                            break;
                        case '3':
                            weekDay.append("সোমবার, ");
                            break;
                        case '4':
                            weekDay.append("মঙ্গলবার, ");
                            break;
                        case '5':
                            weekDay.append("বুধবার, ");
                            break;
                        case '6':
                            weekDay.append("বৃহস্পতিবার, ");
                            break;
                        case '7':
                            weekDay.append("শুক্রবার, ");
                            break;
                        default:
                            break;
                    }
                } else {
                    switch (chars[0]) {
                        case '0':
                            weekDay.append("Every ");
                            break;
                        case '1':
                            weekDay.append("1st week ");
                            break;
                        case '2':
                            weekDay.append("2nd week ");
                            break;
                        case '3':
                            weekDay.append("3rd week ");
                            break;
                        case '4':
                            weekDay.append("4th week ");
                            break;
                        case '5':
                            weekDay.append("5th week ");
                            break;
                        default:
                            break;
                    }

                    switch (chars[1]) {
                        case '1':
                            weekDay.append("Saturday, ");
                            break;
                        case '2':
                            weekDay.append("Sunday, ");
                            break;
                        case '3':
                            weekDay.append("Monday, ");
                            break;
                        case '4':
                            weekDay.append("Tuesday, ");
                            break;
                        case '5':
                            weekDay.append("Wednesday, ");
                            break;
                        case '6':
                            weekDay.append("Thursday, ");
                            break;
                        case '7':
                            weekDay.append("Friday, ");
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        Log.d("weekday", String.valueOf(weekDay));
        return String.valueOf(weekDay);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PlaceDetailsActivity.this, MapActivity.class);
        startActivity(intent);
    }

    private void sendCommentToServer(){
        progressDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("opentext", commentText.getText().toString().trim());
        jsonObject.addProperty("centerId", center.getCenterID());
        Log.d("comment", String.valueOf(jsonObject));
        Call<ResponseBody> call = apiInterface.sendFeedBack(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("comment", String.valueOf(response.body()));
                if (response.code() == 200){
                    progressDialog.dismiss();
                    commentText.setText("Thanks for your feedback.");
                    commentText.setInputType(InputType.TYPE_NULL);
                    btnComment.setVisibility(View.INVISIBLE);
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
