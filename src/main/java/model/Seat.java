package model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "seat")
public class Seat {
    private int id;
    private Venue venue;
    private String level;
    private String description;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "venueId")
//    @JoinColumn(name = "venueId", updatable = false, insertable = false)
    public Venue getVenue() {
        return venue;
    }

    @JsonBackReference
    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
