package com.epi.vaccinationcenter.api;

import com.epi.vaccinationcenter.model.CenterDetails;
import com.epi.vaccinationcenter.model.ImageFind;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("all-center")
    Call<List<CenterDetails>> getAllCenter();

    @POST("center/image-find")
    Call<ImageFind> findImage(@Body JsonObject jsonObject);

    @POST("feedback-add")
    Call<ResponseBody> sendFeedBack(@Body JsonObject jsonObject);
}