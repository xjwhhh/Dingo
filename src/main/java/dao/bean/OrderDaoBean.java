package dao.bean;

import dao.OrderDao;
import model.Order;
import model.OrderState;
import model.ResultMessage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoBean extends BaseDaoBean implements OrderDao {

    private static OrderDaoBean orderDao=new OrderDaoBean();

    public static OrderDaoBean getInstance(){return orderDao;}

    public ResultMessage save(Order order) {
        return super.save(order);
    }

    public Order getOrderById(int orderId) {
        return (Order)super.load(Order.class,orderId) ;
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
