package model;

import javax.persistence.*;

//场馆更改信息的请求bean,将更改后的venue信息用json存起来
@Entity
@Table(name = "venueApplication")
public class VenueApplication {
    private int id;
    private int venueId;
    private VenueApplicationType venueApplicationType;
    private String venueJson;
    private boolean isApproved;
    private int ticketManagerId;
    private String approveTime;

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

    @Enumerated(EnumType.STRING)
    public VenueApplicationType getVenueApplicationType() {
        return venueApplicationType;
    }

    public void setVenueApplicationType(VenueApplicationType venueApplicationType) {
        this.venueApplicationType = venueApplicationType;
    }

    public String getVenueJson() {
        return venueJson;
    }

    public void setVenueJson(String venueJson) {
        this.venueJson = venueJson;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public int getTicketManagerId() {
        return ticketManagerId;
    }

    public void setTicketManagerId(int ticketManagerId) {
        this.ticketManagerId = ticketManagerId;
    }

    public String getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime;
    }
}
