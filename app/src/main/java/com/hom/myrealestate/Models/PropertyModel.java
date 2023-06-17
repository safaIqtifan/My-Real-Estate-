package com.hom.myrealestate.Models;

import android.net.Uri;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class PropertyModel implements Serializable {

    private String userId = "";
    private String propertyId = "";
    private String propertyCodeNum = "";
    private ArrayList<String> propertyImage = new ArrayList<>();
    private String propertyStatus = "";
    private String propertyPayment = "";
    private String leaseTerm = "";
    private String propertyCategory = "";
    private String propertyType = "";
    private String developmentStatus = "";
    private String propertyLocation = "";
    private String propertyPrice = "";
    private String propertyArea = "";
    private String serviceFees = "";
    private String roomsNum = "";
    private String bathroomsNum = "";
    @ServerTimestamp
    Date propertyCreatedAt;

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

//    public String getPropertyImage() {
//        return propertyImage;
//    }

//    public void setPropertyImage(String propertyImage) {
//        this.propertyImage = propertyImage;
//    }

    public ArrayList<String> getPropertyImage() {
        return propertyImage;
    }

    public void setPropertyImage(ArrayList<String> propertyImage) {
        this.propertyImage = propertyImage;
    }

    public String getPropertyCodeNum() {
        return propertyCodeNum;
    }

    public void setPropertyCodeNum(String propertyCodeNum) {
        this.propertyCodeNum = propertyCodeNum;
    }

    public String getPropertyStatus() {
        return propertyStatus;
    }

    public void setPropertyStatus(String propertyStatus) {
        this.propertyStatus = propertyStatus;
    }

    public String getPropertyPayment() {
        return propertyPayment;
    }

    public void setPropertyPayment(String propertyPayment) {
        this.propertyPayment = propertyPayment;
    }

    public String getLeaseTerm() {
        return leaseTerm;
    }

    public void setLeaseTerm(String leaseTerm) {
        this.leaseTerm = leaseTerm;
    }

    public String getPropertyCategory() {
        return propertyCategory;
    }

    public void setPropertyCategory(String propertyCategory) {
        this.propertyCategory = propertyCategory;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getDevelopmentStatus() {
        return developmentStatus;
    }

    public void setDevelopmentStatus(String developmentStatus) {
        this.developmentStatus = developmentStatus;
    }

    public String getPropertyLocation() {
        return propertyLocation;
    }

    public void setPropertyLocation(String propertyLocation) {
        this.propertyLocation = propertyLocation;
    }

    public String getPropertyPrice() {
        return propertyPrice;
    }

    public void setPropertyPrice(String propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public String getPropertyArea() {
        return propertyArea;
    }

    public void setPropertyArea(String propertyArea) {
        this.propertyArea = propertyArea;
    }

    public String getServiceFees() {
        return serviceFees;
    }

    public void setServiceFees(String serviceFees) {
        this.serviceFees = serviceFees;
    }

    public String getRoomsNum() {
        return roomsNum;
    }

    public void setRoomsNum(String roomsNum) {
        this.roomsNum = roomsNum;
    }

    public String getBathroomsNum() {
        return bathroomsNum;
    }

    public void setBathroomsNum(String bathroomsNum) {
        this.bathroomsNum = bathroomsNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getPropertyCreatedAt() {
        return propertyCreatedAt;
    }

    public void setPropertyCreatedAt(Date propertyCreatedAt) {
        this.propertyCreatedAt = propertyCreatedAt;
    }
}
