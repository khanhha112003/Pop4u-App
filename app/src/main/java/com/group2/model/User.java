package com.group2.model;

public class User {
    private String username;
    private String email;
    private String fullname;
    private String birthdate;
    private String phone_number;

    public User(String username, String email, String fullname, String birthdate, String phone_number) {
        this.username = username;
        this.email = email;
        this.fullname = fullname;
        this.birthdate = birthdate;
        this.phone_number = phone_number;
    }

    public String getUsername() {
        return username;
    }

    public String getUserLastName() {
        if (fullname == null || fullname.isEmpty()) {
            return "";
        }
        String[] liststr = fullname.split(" ");
        String result = "";
        for (int i = 0; i < liststr.length - 1; i++) {
            result += liststr[i] + " ";
        }
        return result;
    }

    public String getUserFirstName() {
        if (fullname == null || fullname.isEmpty()) {
            return "";
        }
        String[] liststr = fullname.split(" ");
        return liststr[liststr.length - 1];
    }

    public String getEmail() {
        return email;
    }

    public String getFullname() {
        return fullname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getPhone_number() {
        return phone_number;
    }
}

