package model;

import javax.persistence.*;

@Entity
@Table(name = "venueOrderRecord")
public class VenueOrderRecord {
    private int id;
    private int venueId;
    private int orderId;
    private OrderAction orderAction;
    private String time;

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Enumerated(EnumType.STRING)
    public OrderAction getOrderAction() {
        return orderAction;
    }

    public void setOrderAction(OrderAction orderAction) {
        this.orderAction = orderAction;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
