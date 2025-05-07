package com.example.gearup;

//this is customer model class
public class CustomerData {
    private int id;
    private String name;
    private String phone;
    private String address;
    private String vehicleName;
    private String vehicleType;
    private String serviceType;

    public CustomerData(int id, String name, String phone, String address,
                    String vehicleName, String vehicleType, String serviceType) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.vehicleName = vehicleName;
        this.vehicleType = vehicleType;
        this.serviceType = serviceType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
