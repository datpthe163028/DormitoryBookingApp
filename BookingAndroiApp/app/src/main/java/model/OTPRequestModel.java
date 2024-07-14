package model;

public class OTPRequestModel {
    public String email;
    public String OTP;

    public OTPRequestModel(String email, String otp) {
        this.email = email;
        OTP = otp;
    }
}
