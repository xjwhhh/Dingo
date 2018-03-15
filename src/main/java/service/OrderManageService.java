package service;

import model.Coupon;
import model.Order;
import model.OrderState;
import model.ResultMessage;

import java.util.List;

public interface OrderManageService {

    int reserveChoose(String seatIdListJson, int userId, int venueId, int showId);

    int reserveNoChoose(int one, int two, int three, int userId, int venueId, int showId);

    ResultMessage pay(int orderId, Coupon coupon);

    ResultMessage cancel(int orderId);

    Order getOrderByOrderId(int orderId);

    List<Order> getOrderListByUserId(int userId);

    List<Order> getOrderListByShowId(int showId);

    List<Order> getOrderListByVenueId(int venueId);

    List<Order> getOrderListByState(OrderState orderState);

    List<Order> getAllOrders();

    ResultMessage checkTicket(int ticketId);

}
