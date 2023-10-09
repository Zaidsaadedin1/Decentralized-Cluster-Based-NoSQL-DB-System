package org.example.Client;


public interface ClientInterface {
    // Declare abstract methods (methods without a body) related to Client functionality
    String getUserName();
    String getEmail();
    String getPassword();
    float getId();
    void setUserName(String userName);
    void setEmail(String email);
    void setPassword(String password);
    void setId(int id);
}
