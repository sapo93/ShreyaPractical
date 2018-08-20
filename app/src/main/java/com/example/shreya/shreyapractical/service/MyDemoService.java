package com.example.shreya.shreyapractical.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * This class represents the giphy api service.
 *
 * @author Shreya Prajapati
 */
public class MyDemoService {
    private static final String BASE_URL = "https://api.giphy.com/v1/gifs/";
    public static final String API_KEY = "Z67dSjQeI0xdhffYzKqB4UqAlxGT2UUP";
    private Retrofit retrofit = null;

    /**
     * This method creates a new instance of the API interface.
     *
     * @return The API interface
     */

    public MyDemoAPI getAPI() {
        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(MyDemoAPI.class);
    }
}