package com.company.moon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("signup.php")
    Call<List<LogInInfo>> signUpUser(@Field("email") String email,
                             @Field("password") String password,
                             @Field("confirm") String confirm,
                             @Field("phone") String phone,
                             @Field("type") String type)
        ;

    @FormUrlEncoded
    @POST("signup.php")
    Call<Integer> signUpUser2(@Field("email") String email,
                              @Field("password") String password,
                              @Field("confirm") String confirm,
                              @Field("phone") String phone,
                              @Field("type") String type)
            ;

    @FormUrlEncoded
    @POST("login.php")
    Call<List<LogInInfo>> logInUser(@Field("email") String email,
                         @Field("password") String password)
        ;
    
    @Multipart
    @POST("retailer_reg.php")
    Call<Integer> retailerDetailsUpload(@Part MultipartBody.Part image,
                                     @Part("user_id") RequestBody user_id,
                                     @Part("name") RequestBody name,
                                     @Part("nature") RequestBody nature,
                                     @Part("gstin") RequestBody gstin)
        ;
}
