package com.company.moon;

import retrofit2.Callback;

public class ApiRequest {
    private static ApiRequest instance;

    public static ApiRequest getInstance() {
        if (instance == null) {
            instance = new ApiRequest();
        }
        return instance;
    }
    
    public void signUpUser(String email, String password, String confirm, String phone, String type, Callback<Integer> callback) {
        ApiClient.getClient().create(ApiInterface.class).signUpUser(email, password, confirm, phone, type).enqueue(callback);
    }
}
