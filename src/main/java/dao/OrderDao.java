package dao;

import model.Order;
import model.OrderRecord;
import model.OrderState;
import model.ResultMessage;

import java.util.List;

public interface OrderDao extends BaseDao {

    ResultMessage save(Order order);

    Order getOrderById(int orderId);

    List<Order> getOrderByUserId(int userId);

    List<Order> getOrderByShowId(int showId);

    List<Order> getOrderByVenueId(int venueId);

    List<Order> getOrderByState(OrderState orderState);

    ResultMessage saveOrderRecord(OrderRecord orderRecord);

}
