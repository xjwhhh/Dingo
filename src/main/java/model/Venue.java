package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "venue")
public class Venue {
    private int id;
    private String code;
    private String account;
    private String password;
    private String name;
    private String address;
    private double onlineBalance;
    private double offlineBalance;
    private List<Seat> seatList;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getOnlineBalance() {
        return onlineBalance;
    }

    public void setOnlineBalance(double onlineBalance) {
        this.onlineBalance = onlineBalance;
    }

    public double getOfflineBalance() {
        return offlineBalance;
    }

    public void setOfflineBalance(double offlineBalance) {
        this.offlineBalance = offlineBalance;
    }

    @OneToMany(mappedBy = "venue", fetch = FetchType.EAGER)
    public List<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
    }
}
