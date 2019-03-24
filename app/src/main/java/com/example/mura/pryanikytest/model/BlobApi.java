package com.example.mura.pryanikytest.model;

import com.example.mura.pryanikytest.POJO.Example;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface BlobApi {
        @GET("JSONSample.json")
        Call<Example> message();
        @GET()
        Call<ResponseBody> image(@Url String url);

}
