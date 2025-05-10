
package com.example.gearup.models;
import com.example.gearup.models.ServiceModel;
public class ServiceModel {
    private String customerName;
    private String customerPhone;
    private String vehicleNumber;
    private String serviceTime;

    public ServiceModel(String customerName, String customerPhone, String vehicleNumber, String serviceTime) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.vehicleNumber = vehicleNumber;
        this.serviceTime = serviceTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getServiceTime() {
        return serviceTime;
    }
}