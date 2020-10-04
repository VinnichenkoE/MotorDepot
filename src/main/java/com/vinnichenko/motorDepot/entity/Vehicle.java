package com.vinnichenko.motorDepot.entity;

public class Vehicle {
    private int id;
    private String brand;
    private String model;
    private String registrationNumber;
    private int numberOfSeats;
    private String status;
    private User user;

    public Vehicle(int id, String brand, String model, String registrationNumber, int numberOfSeats, String status, User user) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.numberOfSeats = numberOfSeats;
        this.status = status;
        this.user = user;
    }

    public Vehicle(String brand, String model, int numberOfSeats) {
        this.brand = brand;
        this.model = model;
        this.numberOfSeats = numberOfSeats;
    }

    public Vehicle(){
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle vehicle = (Vehicle) o;

        if (numberOfSeats != vehicle.numberOfSeats) return false;
        if (brand != null ? !brand.equals(vehicle.brand) : vehicle.brand != null)
            return false;
        return model != null ? model.equals(vehicle.model) : vehicle.model == null;
    }

    @Override
    public int hashCode() {
        int result = brand != null ? brand.hashCode() : 0;
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + numberOfSeats;
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
        sb.append(", status='").append(status).append('\'');
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }
}
