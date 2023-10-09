package org.example.Admin;

public class Admin implements AdminInterface{
    private static float id;
    private String userName;
    private String email;
    private String password;

    public Admin(float id, String userName, String email, String password) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setId(float id) {
        this.id = id;
    }

    public float getId() {
        return id;
    }


}
