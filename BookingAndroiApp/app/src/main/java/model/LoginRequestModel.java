package model;

public class LoginRequestModel {
    public String email;
    public String Password;

    public LoginRequestModel(String email, String password) {
        this.email = email;
        Password = password;
    }
}
