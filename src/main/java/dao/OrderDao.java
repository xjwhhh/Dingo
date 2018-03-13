package dao;

import model.*;

import java.util.List;

public interface OrderDao extends BaseDao {

    ResultMessage save(Order order);

    Order getOrderById(int orderId);

    List<Order> getOrderByUserId(int userId);

    List<Order> getOrderByShowId(int showId);

    List<Order> getOrderByVenueId(int venueId);

    List<Order> getOrderByState(OrderState orderState);

    ResultMessage saveOrderRecord(OrderRecord orderRecord);

    ResultMessage saveTicket(Ticket ticket);

}
