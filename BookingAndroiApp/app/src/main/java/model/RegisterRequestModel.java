package model;

import java.util.Date;

public class RegisterRequestModel {
    public String email;
    public String Password;
    public String StudentID;
    public Date Dob;
    public String Phone;
    public boolean Gender;

    public RegisterRequestModel(String email, String password) {
        this.email = email;
        Password = password;
    }

<<<<<<< Updated upstream
    public RegisterRequestModel(String email, String password, String studentID, Date dob, String phone, boolean gender) {
        this.email = email;
        this.Password = password;
        this.StudentID = studentID;
        //this.Dob = dob;
=======
    public RegisterRequestModel(String email, String password, String studentID, String phone, boolean gender) {
        this.email = email;
        this.Password = password;
        this.StudentID = studentID;
>>>>>>> Stashed changes
        this.Phone = phone;
        this.Gender = gender;
    }
}
