package com.example.asm_resfulapi.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserModel {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("Email")
    @Expose
    private String Email;
    @SerializedName("Username")
    @Expose
    private String Username;
    @SerializedName("Password")
    @Expose
    private String Password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public UserModel() {
    }

    public UserModel(String id, String email, String username, String password) {
        this.id = id;
        Email = email;
        Username = username;
        Password = password;
    }

    public UserModel(String email, String username, String password) {
        Email = email;
        Username = username;
        Password = password;
    }


    public UserModel(String email, String password) {
        Email = email;
        Password = password;
    }
}
