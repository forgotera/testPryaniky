package com.example.mura.pryanikytest.model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetworkService {
    static final String BASE_URL = "https://prnk.blob.core.windows.net/tmp/";
    private Retrofit mRetrofit;

    public NetworkService(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    //получить реализацию из интерфейса
    public BlobApi getBlobApi(){
        return mRetrofit.create(BlobApi.class);
    }
   }







