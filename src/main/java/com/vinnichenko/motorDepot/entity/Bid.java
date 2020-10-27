package com.vinnichenko.motorDepot.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Bid implements Serializable {

    public enum BidStatus {
        SUBMITTED(1),
        PENDING(2),
        IN_PROCESS(3),
        COMPLETED(4);

        private int index;

        BidStatus(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public static Bid.BidStatus getStatus(int id) {
            for (Bid.BidStatus status : Bid.BidStatus.values()) {
                if (status.index == id) {
                    return status;
                }
            }
            throw new IllegalArgumentException("invalid value of user role id"); //TODO ???
        }
    }
    private int id;
    private int number_of_seats;
    private long startDate;
    private long endDate;
    private String startPoint;
    private String endPoint;
    private int distance;
    private BidStatus status;

    public Bid() {
    }

    public Bid(int number_of_seats, long startDate, long endDate, String startPoint, String endPoint, int distance, BidStatus status) {
        this.number_of_seats = number_of_seats;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.distance = distance;
        this.status = status;
    }

    public Bid(int id, int number_of_seats, long startDate, long endDate, String startPoint, String endPoint, int distance, BidStatus status) {
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

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
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
        if (startDate != bid.startDate) return false;
        if (endDate != bid.endDate) return false;
        if (distance != bid.distance) return false;
        if (startPoint != null ? !startPoint.equals(bid.startPoint) : bid.startPoint != null) return false;
        if (endPoint != null ? !endPoint.equals(bid.endPoint) : bid.endPoint != null) return false;
        return status == bid.status;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + number_of_seats;
        result = 31 * result + (int) (startDate ^ (startDate >>> 32));
        result = 31 * result + (int) (endDate ^ (endDate >>> 32));
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
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
