package model;

public class UserOrderRecord {
    private int id;
    private int userId;
    private int orderId;
    private OrderAction orderAction;
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public OrderAction getOrderAction() {
        return orderAction;
    }

    public void setOrderAction(OrderAction orderAction) {
        this.orderAction = orderAction;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
