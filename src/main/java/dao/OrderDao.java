package dao;

import model.*;

import java.util.List;

public interface OrderDao extends BaseDao {

    ResultMessage save(Order order);

    Order findOrderById(int orderId);

    List<Order> findOrderByUserId(int userId);

    List<Order> findOrderByShowId(int showId);

    List<Order> findOrderByVenueId(int venueId);

    List<Order> findOrderByState(OrderState orderState);

    ResultMessage saveOrderRecord(OrderRecord orderRecord);

    ResultMessage saveTicket(Ticket ticket);

    Ticket findTicketById(int ticketId);

}
