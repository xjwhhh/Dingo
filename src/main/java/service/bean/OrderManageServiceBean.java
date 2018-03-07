package service.bean;

import dao.*;
import model.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.OrderManageService;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderManageServiceBean implements OrderManageService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    VenueDao venueDao;

    @Autowired
    UserDao userDao;

    @Autowired
    ShowDao showDao;

    public ResultMessage reserveChoose(String orderJson) {
        JSONObject jsonObject = JSONObject.fromObject(orderJson);
        Order order = (Order) JSONObject.toBean(jsonObject, Order.class);
        orderDao.save(order);
        //todo 获取保存的id 保存订单记录
//        OrderRecord orderRecord=new OrderRecord();
//        orderRecord.setOrderId(order.getId());
//        orderRecord.setUserId(order.getUserId());
//        orderRecord.setVenueId(order.getVenueId());
//        orderRecord.setOrderAction(OrderAction.PAY);
//        orderRecord.setTime(dateString);
//        orderDao.saveOrderRecord(orderRecord);
        return ResultMessage.SUCCESS;
    }

    public ResultMessage reserveNoChoose(String orderJson) {
        JSONObject jsonObject = JSONObject.fromObject(orderJson);
        Order order = (Order) JSONObject.toBean(jsonObject, Order.class);
        return orderDao.save(order);
    }

    public ResultMessage pay(int orderId) {
        Order order = orderDao.getOrderById(orderId);
        if (order.getPayTime() == null) {
            Date date = new Date();
            //todo dateFormat
            String dateString = null;
            order.setPayTime(dateString);
            orderDao.update(order);
            Show show = showDao.findById(order.getShowId());
            show.setEarning(show.getEarning() + order.getCost());
            showDao.update(show);
            User user = userDao.findById(order.getUserId());
            user.setBalance(user.getBalance() - order.getCost());
            userDao.update(user);
            OrderRecord orderRecord = new OrderRecord();
            orderRecord.setOrderId(orderId);
            orderRecord.setUserId(order.getUserId());
            orderRecord.setVenueId(order.getVenueId());
            orderRecord.setOrderAction(OrderAction.PAY);
            orderRecord.setTime(dateString);
            orderDao.saveOrderRecord(orderRecord);
            return ResultMessage.SUCCESS;
        }
        return ResultMessage.FAIL;
    }

    public ResultMessage cancel(int orderId) {
        Order order = orderDao.getOrderById(orderId);
        if (order.getCancelTime() == null) {
            Date date = new Date();
            //todo dateFormat
            String dateString = null;
            order.setCancelTime(dateString);
            orderDao.update(order);
            Show show = showDao.findById(order.getShowId());
            //退款，根据期限返还不同比例 todo
            double balance = 0;

            show.setEarning(show.getEarning() - balance);
            showDao.update(show);
            User user = userDao.findById(order.getUserId());
            user.setBalance(user.getBalance() + balance);
            userDao.update(user);
            OrderRecord orderRecord = new OrderRecord();
            orderRecord.setOrderId(orderId);
            orderRecord.setUserId(order.getUserId());
            orderRecord.setVenueId(order.getVenueId());
            orderRecord.setOrderAction(OrderAction.CANCEL);
            orderRecord.setTime(dateString);
            orderDao.saveOrderRecord(orderRecord);
            return ResultMessage.SUCCESS;
        }
        return ResultMessage.FAIL;

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
