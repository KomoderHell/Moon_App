package com.company.moon;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("signup.php")
    Call<Integer> signUpUser(@Field("email") String email,
                             @Field("password") String password,
                             @Field("confirm") String confirm,
                             @Field("phone") String phone,
                             @Field("type") String type)
        ;
}
