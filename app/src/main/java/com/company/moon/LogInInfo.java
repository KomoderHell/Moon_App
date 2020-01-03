package com.company.moon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogInInfo {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("gstin")
    @Expose
    private String gstin;
    @SerializedName("approval")
    @Expose
    private String approval;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("nature")
    @Expose
    private String nature;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("fill_status")
    @Expose
    private String fill_status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getFill_status() {
        return fill_status;
    }

    public void setFill_status(String fill_status) {
        this.fill_status = fill_status;
    }
}
