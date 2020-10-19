package com.vinnichenko.motorDepot.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Bid implements Serializable {

    public enum BidStatus {
        PENDING,
        IN_PROCESS,
        COMPLETE
    }
    private int id;
    private int number_of_seats;
    private Timestamp startDate;
    private Timestamp endDate;
    private String startPoint;
    private String endPoint;
    private int distance;
    private BidStatus status;

    public Bid() {
    }

    public Bid(int number_of_seats, Timestamp startDate, Timestamp endDate, String startPoint, String endPoint, int distance, BidStatus status) {
        this.number_of_seats = number_of_seats;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.distance = distance;
        this.status = status;
    }

    public Bid(int id, int number_of_seats, Timestamp startDate, Timestamp endDate, String startPoint, String endPoint, int distance, BidStatus status) {
        this.id = id;
        this.number_of_seats = number_of_seats;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.distance = distance;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber_of_seats() {
        return number_of_seats;
    }

    public void setNumber_of_seats(int number_of_seats) {
        this.number_of_seats = number_of_seats;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public BidStatus getStatus() {
        return status;
    }

    public void setStatus(BidStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bid bid = (Bid) o;

        if (id != bid.id) return false;
        if (number_of_seats != bid.number_of_seats) return false;
        if (distance != bid.distance) return false;
        if (startDate != null ? !startDate.equals(bid.startDate) : bid.startDate != null)
            return false;
        if (endDate != null ? !endDate.equals(bid.endDate) : bid.endDate != null)
            return false;
        if (startPoint != null ? !startPoint.equals(bid.startPoint) : bid.startPoint != null)
            return false;
        if (endPoint != null ? !endPoint.equals(bid.endPoint) : bid.endPoint != null)
            return false;
        return status == bid.status;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + number_of_seats;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (startPoint != null ? startPoint.hashCode() : 0);
        result = 31 * result + (endPoint != null ? endPoint.hashCode() : 0);
        result = 31 * result + distance;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Bid{");
        sb.append("id=").append(id);
        sb.append(", number_of_seats=").append(number_of_seats);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", startPoint='").append(startPoint).append('\'');
        sb.append(", endPoint='").append(endPoint).append('\'');
        sb.append(", distance=").append(distance);
        sb.append(", status='").append(status).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
