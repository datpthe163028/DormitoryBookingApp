package model;

public class LoginRequestModel {
    public String email;
    public String Password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public LoginRequestModel(String email, String password) {
        this.email = email;
        Password = password;
    }

    public LoginRequestModel() {
    }
}
