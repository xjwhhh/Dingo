package dao;

import model.Order;
import model.OrderState;

import java.util.List;

public interface OrderDao {

    Order getOrderById(int orderId);

    List<Order> getOrderByUserId(int userId);

    List<Order> getOrderByShowId(int showId);

    List<Order> getOrderByVenueId(int venueId);

    List<Order> getOrderByState(OrderState orderState);

}
