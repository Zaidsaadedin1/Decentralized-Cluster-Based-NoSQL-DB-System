package org.example.Client;


public class Client implements ClientInterface{
    private int id;
    private String userName;
    private String email;
    private String password;
    public Client(int id,String userName, String email, String password) {
        this.id=id;
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

    public float getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
