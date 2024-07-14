package model;

public class RegisterRequestModel {
    public String email;
    public String Password;
    public String StudentID;
    public String Phone;
    public boolean Gender;

    public RegisterRequestModel(String email, String password) {
        this.email = email;
        Password = password;
    }

    public RegisterRequestModel(String email, String password, String studentID, String phone, boolean gender) {
        this.email = email;
        this.Password = password;
        this.StudentID = studentID;
        this.Phone = phone;
        this.Gender = gender;
    }
}
