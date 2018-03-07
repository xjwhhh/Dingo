package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "venueFinance")
public class VenueFinance {
    private int id;
    private int venueId;
    private int ticketManagerId;
    private double cost;

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

    public int getTicketManagerId() {
        return ticketManagerId;
    }

    public void setTicketManagerId(int ticketManagerId) {
        this.ticketManagerId = ticketManagerId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
