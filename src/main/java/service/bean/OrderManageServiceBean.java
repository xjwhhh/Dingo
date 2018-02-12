package service.bean;

import dao.DaoFactory;
import dao.OrderDao;
import model.Order;
import model.OrderState;
import service.OrderManageService;

import java.util.List;

public class OrderManageServiceBean implements OrderManageService {

    OrderDao orderDao= DaoFactory.getOrderDao();

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
