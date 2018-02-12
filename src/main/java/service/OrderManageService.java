package service;

import model.Order;
import model.OrderState;

import java.util.List;

public interface OrderManageService {

    void reserveChoose();

    void reserveNoChoose();

    void pay();

    void cancel();

    Order getOrderByOrderId(int orderId);

    List<Order> getOrderListByUserId(int userId);

    List<Order> getOrderListByShowId(int showId);

    List<Order> getOrderListByVenueId(int venueId);

    List<Order> getOrderListByState(OrderState orderState);

    List<Order> getAllOrders();


}
