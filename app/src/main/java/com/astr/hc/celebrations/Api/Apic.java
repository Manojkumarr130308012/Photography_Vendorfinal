package com.astr.hc.celebrations.Api;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Apic {
    /*
  Retrofit get annotation with our URL
  And our method that will return us the List of ContactList
  */
    @Headers("Content-Type: application/json")


    @POST("api/vendor_image")
    Call<ResponseBody> uploadImages(@Body RequestBody form);
}
