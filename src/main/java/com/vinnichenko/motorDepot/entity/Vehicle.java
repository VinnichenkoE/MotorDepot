package com.vinnichenko.motorDepot.entity;

import java.io.Serializable;

public class Vehicle implements Serializable {
    private int id;
    private String brand;
    private String model;
    private String registrationNumber;
    private int numberOfSeats;
    private int status_id;
    private int user_id;

    public Vehicle(){
    }

    public Vehicle(int id, String brand, String model, String registrationNumber, int numberOfSeats, int status_id, int user_id) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.numberOfSeats = numberOfSeats;
        this.status_id = status_id;
        this.user_id = user_id;
    }

    public Vehicle(String brand, String model, int numberOfSeats) {
        this.brand = brand;
        this.model = model;
        this.numberOfSeats = numberOfSeats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle vehicle = (Vehicle) o;

        if (id != vehicle.id) return false;
        if (numberOfSeats != vehicle.numberOfSeats) return false;
        if (status_id != vehicle.status_id) return false;
        if (user_id != vehicle.user_id) return false;
        if (brand != null ? !brand.equals(vehicle.brand) : vehicle.brand != null)
            return false;
        if (model != null ? !model.equals(vehicle.model) : vehicle.model != null)
            return false;
        return registrationNumber != null ? registrationNumber.equals(vehicle.registrationNumber) : vehicle.registrationNumber == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (registrationNumber != null ? registrationNumber.hashCode() : 0);
        result = 31 * result + numberOfSeats;
        result = 31 * result + status_id;
        result = 31 * result + user_id;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Vehicle{");
        sb.append("id=").append(id);
        sb.append(", brand='").append(brand).append('\'');
        sb.append(", model='").append(model).append('\'');
        sb.append(", registrationNumber='").append(registrationNumber).append('\'');
        sb.append(", numberOfSeats=").append(numberOfSeats);
        sb.append(", status_id=").append(status_id);
        sb.append(", user_id=").append(user_id);
        sb.append('}');
        return sb.toString();
    }
}
