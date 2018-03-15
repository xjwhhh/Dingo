package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "showTable")
public class Show {

    private int id;
    private String showType;
    private String name;
    private String description;
    private String startTime;
    private String endTime;
    private int venueId;
    private int totalSeats;
    private int currentSeats;
    private String ProgressType;
    private double earning;
    private List<ShowSeat> seatList;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //    @Enumerated(EnumType.STRING)
    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getCurrentSeats() {
        return currentSeats;
    }

    public void setCurrentSeats(int currentSeats) {
        this.currentSeats = currentSeats;
    }

    //    @Enumerated(EnumType.STRING)
    public String getProgressType() {
        return ProgressType;
    }

    public void setProgressType(String progressType) {
        this.ProgressType = progressType;
    }

    public double getEarning() {
        return earning;
    }

    public void setEarning(double earning) {
        this.earning = earning;
    }

    @OneToMany(mappedBy = "show", fetch = FetchType.EAGER)
    public List<ShowSeat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<ShowSeat> seatList) {
        this.seatList = seatList;
    }
}
