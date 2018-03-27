package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "showEarning")
public class ShowEarning {
    private int id;
    private int showId;
    private int ticketManagerId;
    private double onlineEarning;
    private double offlineEarning;
    private boolean isSettled;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public int getTicketManagerId() {
        return ticketManagerId;
    }

    public void setTicketManagerId(int ticketManagerId) {
        this.ticketManagerId = ticketManagerId;
    }

    public double getOnlineEarning() {
        return onlineEarning;
    }

    public void setOnlineEarning(double onlineEarning) {
        this.onlineEarning = onlineEarning;
    }

    public double getOfflineEarning() {
        return offlineEarning;
    }

    public void setOfflineEarning(double offlineEarning) {
        this.offlineEarning = offlineEarning;
    }

    public boolean isSettled() {
        return isSettled;
    }

    public void setSettled(boolean settled) {
        isSettled = settled;
    }
}
