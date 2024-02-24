package com.example.asm_resfulapi.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductModel {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private int price;
    @SerializedName("CateID")
    @Expose
    private String CateID;

    public ProductModel(String id, String name, String description, int price, String cateID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        CateID = cateID;
    }

    public ProductModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCateID() {
        return CateID;
    }

    public void setCateID(String cateID) {
        CateID = cateID;
    }
}
