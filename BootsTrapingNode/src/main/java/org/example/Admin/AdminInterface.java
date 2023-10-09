package org.example.Admin;

public interface AdminInterface {
    // Declare abstract methods (methods without a body) related to Admin functionality
    String getUserName();
    String getEmail();
    String getPassword();
    float getId();
    void setUserName(String userName);
    void setEmail(String email);
    void setPassword(String password);
    void setId(float id);
}
