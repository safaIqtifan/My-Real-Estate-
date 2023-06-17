package com.hom.myrealestate.Models;

public class UserModel {

    String userId;
    String name;
    String phoneNumber;
    String ccpPhone;
    String address;
    String email;
    String password;
    String confirmPassword;
    String fullMobileNum;
    boolean profileCompleted = false;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isProfileCompleted() {
        return profileCompleted;
    }

    public void setProfileCompleted(boolean profileCompleted) {
        this.profileCompleted = profileCompleted;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCcpPhone() {
        return ccpPhone;
    }

    public void setCcpPhone(String ccpPhone) {
        this.ccpPhone = ccpPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFullMobileNum() {
        return fullMobileNum;
    }

    public void setFullMobileNum(String fullMobileNum) {
        this.fullMobileNum = fullMobileNum;
    }
}
