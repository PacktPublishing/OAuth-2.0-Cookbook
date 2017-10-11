package com.example.dynamicserver.api;

public class UserProfile {

    private String name;

    private String email;

    public UserProfile(String name, String email) {
        super();
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}
