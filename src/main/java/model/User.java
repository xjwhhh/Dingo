package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    private int id;
    private String account;
    private String password;
    private String emailAddress;
    private boolean isConfirmed;
    private String name;
    private String level;
    private double balance;
    private int totalIntegral;
    private int currentIntegral;
    private boolean isCancelled;
    private List<Coupon> couponList;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //    @Column(name = "level")
//    @Enumerated(EnumType.STRING)
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getTotalIntegral() {
        return totalIntegral;
    }

    public void setTotalIntegral(int totalIntegral) {
        this.totalIntegral = totalIntegral;
    }

    public int getCurrentIntegral() {
        return currentIntegral;
    }

    public void setCurrentIntegral(int currentIntegral) {
        this.currentIntegral = currentIntegral;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    public List<Coupon> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Coupon> couponList) {
        this.couponList = couponList;
    }
}
