package com.example.gearup.models;

public class ServiceModel {
    private String customerName;
    private String customerPhone;
    private String vehicleNumber;
    private String vehicleType;
    private String serviceTime;
    private String id;

    // Default constructor (required for Firebase)
    public ServiceModel() {}

    public ServiceModel(String customerName, String customerPhone, String vehicleNumber, String vehicleType, String serviceTime, String id) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.serviceTime = serviceTime;
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
