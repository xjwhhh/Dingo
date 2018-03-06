package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "show")
public class Show {
    private int id;
    private ShowType showType;
    private String name;
    private String description;
    private String time;
    private int venueId;
    private int totalSeats;
    private int currentSeats;
    private ProgressType ProgressType;
    private double earning;
    private List<ShowSeat> seatList;

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    public ShowType getShowType() {
        return showType;
    }

    public void setShowType(ShowType showType) {
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    @Enumerated(EnumType.STRING)
    public ProgressType getProgressType() {
        return ProgressType;
    }

    public void setProgressType(ProgressType progressType) {
        this.ProgressType = progressType;
    }

    public double getEarning() {
        return earning;
    }

    public void setEarning(double earning) {
        this.earning = earning;
    }

    @OneToMany(mappedBy = "show")
    public List<ShowSeat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<ShowSeat> seatList) {
        this.seatList = seatList;
    }
}
