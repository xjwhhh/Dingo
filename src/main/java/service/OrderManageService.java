package service;

import model.*;

import java.util.List;

public interface OrderManageService {

    int reserveChoose(String seatIdListJson, int userId, int venueId, int showId);

    int reserveNoChoose(int one, int two, int three, int userId, int venueId, int showId);

    int offLineBuyTicket(int one, int two, int three, String userAccount, String userPassword, int venueId, int showId);

    ResultMessage pay(int orderId, int couponType);

    ResultMessage cancel(int orderId);

    Order getOrderByOrderId(int orderId);

    List<Order> getOrderListByUserId(int userId);

    List<Order> getOrderListByShowId(int showId);

    List<Order> getOrderListByVenueId(int venueId);

    List<Order> getOrderListByState(OrderState orderState);

    List<Order> getAllOrders();

    ResultMessage checkTicket(int ticketId);

    List<OrderRecord> getOrderRecordListByUserId(int userId);

    List<OrderRecord> getOrderRecordListByVenueId(int venueId);

    ResultMessage allocateTicket();
}
