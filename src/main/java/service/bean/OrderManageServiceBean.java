package service.bean;

import dao.DaoFactory;
import dao.OrderDao;
import model.Order;
import model.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.OrderManageService;

import java.util.List;

@Service
public class OrderManageServiceBean implements OrderManageService {

    @Autowired
    OrderDao orderDao;


    public void reserveChoose() {

    }

    public void reserveNoChoose() {

    }

    public void pay() {

    }

    public void cancel() {

    }

    public Order getOrderByOrderId(int orderId) {
        return orderDao.getOrderById(orderId);
    }

    public List<Order> getOrderListByUserId(int userId) {
        return orderDao.getOrderByUserId(userId);
    }

    public List<Order> getOrderListByShowId(int showId) {
        return orderDao.getOrderByShowId(showId);
    }

    public List<Order> getOrderListByVenueId(int venueId) {
        return orderDao.getOrderByVenueId(venueId);
    }

    public List<Order> getOrderListByState(OrderState orderState) {
        return orderDao.getOrderByState(orderState);
    }

    public List<Order> getAllOrders() {
        return null;
    }
}
