package com.example.gearup.models;

public class ServiceModel {
    private String id;
    private String customerName;
    private String customerPhone;
    private String vehicleNumber;
    private String vehicleType;
    private String serviceTime;

    // Default constructor for Firebase and manual initialization
    public ServiceModel() {
        // No-op constructor for Firebase
    }

    // Constructor with all fields
    public ServiceModel(String customerName, String customerPhone, String vehicleNumber, String vehicleType, String serviceTime) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.serviceTime = serviceTime;
    }

    // Constructor with id (for internal usage)
    public ServiceModel(String id, String customerName, String customerPhone, String vehicleNumber, String vehicleType, String serviceTime) {
        this.id = id;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.serviceTime = serviceTime;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
