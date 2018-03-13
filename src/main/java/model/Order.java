package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orderTable")
public class Order {
    private int id;
    private int userId;
    private int venueId;
    private int showId;
    private double cost;
    private String orderTime;
    private String payTime;
    private String ticketConfirmedTime;
    private String cancelTime;
    private String state;
    private List<Ticket> ticketList;

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getTicketConfirmedTime() {
        return ticketConfirmedTime;
    }

    public void setTicketConfirmedTime(String ticketConfirmedTime) {
        this.ticketConfirmedTime = ticketConfirmedTime;
    }

//    @Enumerated(EnumType.STRING)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER)
    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }
}
