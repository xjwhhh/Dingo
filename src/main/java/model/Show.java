package model;

import java.util.List;

public class Show {
    private int id;
    private ShowType showType;
    private String name;
    private String description;
    private String time;
    private int venueId;
    private int totalSeats;
    private int currentSeats;
    private progressType progressType;
    private List<ShowSeat> seatList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public model.progressType getProgressType() {
        return progressType;
    }

    public void setProgressType(model.progressType progressType) {
        this.progressType = progressType;
    }

    public List<ShowSeat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<ShowSeat> seatList) {
        this.seatList = seatList;
    }
}
