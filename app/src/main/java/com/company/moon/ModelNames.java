package com.company.moon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelNames {
    // Company Name
    @SerializedName("name")
    @Expose
    private String name;
    // Company user ID
    @SerializedName("us_id")
    @Expose
    private String us_id;
    // Product ID
    @SerializedName("id")
    @Expose
    private String id;
    // Product Name
    @SerializedName("product_name")
    @Expose
    private String product_name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUs_id() {
        return us_id;
    }

    public void setUs_id(String us_id) {
        this.us_id = us_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
