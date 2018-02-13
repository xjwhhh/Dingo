package model;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User {

    private int id;
    private String account;
    private String password;
    private String emailAddress;
    private boolean isConfirmed;
    private String name;
    private VIPLevel level;
    private int totalIntegral;
    private int currentIntegral;
    private boolean isCancelled;

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

    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    public VIPLevel getLevel() {
        return level;
    }

    public void setLevel(VIPLevel level) {
        this.level = level;
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
}
