package com.company.moon;

import java.util.List;

import retrofit2.Callback;

public class ApiRequest {
    private static ApiRequest instance;

    public static ApiRequest getInstance() {
        if (instance == null) {
            instance = new ApiRequest();
        }
        return instance;
    }

    // Other Functions
    public void signUpUser(String email, String password, String confirm, String phone, String type, Callback<List<LogInInfo>> callback) {
        ApiClient.getClient().create(ApiInterface.class).signUpUser(email, password, confirm, phone, type).enqueue(callback);
    }

    public void signUpUser2(String email, String password, String confirm, String phone, String type, Callback<Integer> callback) {
        ApiClient.getClient().create(ApiInterface.class).signUpUser2(email, password, confirm, phone, type).enqueue(callback);
    }

    public void logInUser(String email, String password, Callback<List<LogInInfo>> callback) {
        ApiClient.getClient().create(ApiInterface.class).logInUser(email, password).enqueue(callback);
    }
    
    public void retailerDetailsUpload(MultipartBody.Part image, RequestBody user_id, RequestBody name, RequestBody nature,
                                      RequestBody gstin, Callback<Integer> callback) {
        ApiClient.getClient().create(ApiInterface.class).retailerDetailsUpload(image, user_id, name, nature, gstin).enqueue(callback);
    }
}
