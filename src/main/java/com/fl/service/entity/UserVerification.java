package com.fl.service.entity;

public class UserVerification {
    private String verificationId;
    private String userName;

    public UserVerification(String verificationId, String userName) {
        this.verificationId = verificationId;
        this.userName = userName;
    }

    public String getVerificationId() {
        return verificationId;
    }

    public void setVerificationId(String verificationId) {
        this.verificationId = verificationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
