package com.example.shreya.shreyapractical.service;

import com.example.shreya.shreyapractical.model.CallbackGetSearchList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * This class represents the Giphy Demo API, all endpoints can stay here.
 * @author Shreya Prajapati
 * @date 14/08/18.
 */
public interface MyDemoAPI {
    @GET("search")
    Call<CallbackGetSearchList> getSearchResults(@Query("api_key") String api_key,
                                                 @Query("q") String query,
                                                 @Query("limit") String limit,
                                                 @Query("offset") String offset,
                                                 @Query("rating") String rating,
                                                 @Query("lang") String lang);
}