package dao.bean;

import dao.OrderDao;
import model.Order;
import model.OrderState;

import java.util.List;

public class OrderDaoBean implements OrderDao {

    private static OrderDaoBean orderDao=new OrderDaoBean();

    public static OrderDaoBean getInstance(){return orderDao;}

    public Order getOrderById(int orderId) {
        return null;
    }

    public List<Order> getOrderByUserId(int userId) {
        return null;
    }

    public List<Order> getOrderByShowId(int showId) {
        return null;
    }

    public List<Order> getOrderByVenueId(int venueId) {
        return null;
    }

    public List<Order> getOrderByState(OrderState orderState) {
        return null;
    }
}
