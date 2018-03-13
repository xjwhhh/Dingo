package service.bean;

import dao.*;
import model.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.OrderManageService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public ResultMessage reserveChoose(String seatIdListJson,int userId,int venueId,int showId) {
        JSONObject jsonObject = JSONObject.fromObject(seatIdListJson);
        List<Integer> showSeatIdList = (List<Integer>) JSONObject.toBean(jsonObject, List.class);
        List<ShowSeat> showSeatList = new ArrayList<ShowSeat>();
        for(int i=0;i<showSeatIdList.size();i++){
            showSeatList.add(showDao.findShowSeat(showSeatIdList.get(i)));
        }
        System.out.println(showSeatList);

        //order
        Order order=new Order();
        order.setUserId(userId);
        order.setVenueId(venueId);
        order.setShowId(showId);
        double cost=0;
        for(int i=0;i<showSeatList.size();i++){
            cost+=showSeatList.get(i).getCost();
        }
        order.setCost(cost);
        Date date=new Date();
        order.setOrderTime(dateFormat(date));
        order.setState(OrderState.UNPAID.toString());
        orderDao.save(order);

        //ticket
        List<Order> orderList=orderDao.getOrderByUserId(userId);
        List<Order> orderList1=new ArrayList<Order>();
        for(int i=0;i<orderList.size();i++){
            if(orderList.get(i).getShowId()==showId){
                orderList1.add(orderList.get(i));
            }
        }
        for(int i=0;i<orderList.size();i++){
            if(orderList1.get(i).getId()>order.getId()){
                order=orderList1.get(i);
            }
        }
        for(int i=0;i<showSeatList.size();i++){
            Ticket ticket=new Ticket();
            ticket.setCost(showSeatList.get(i).getCost());
            ticket.setSeatId(showSeatList.get(i).getId());
            ticket.setOrder(order);
            ticket.setLevel(showSeatList.get(i).getLevel());
            orderDao.saveTicket(ticket);
        }

        //orderRecord
        OrderRecord orderRecord=new OrderRecord();
        orderRecord.setOrderId(order.getId());
        orderRecord.setUserId(order.getUserId());
        orderRecord.setVenueId(order.getVenueId());
        orderRecord.setOrderAction(OrderAction.ORDER.toString());
        orderRecord.setTime(dateFormat(date));
        orderDao.saveOrderRecord(orderRecord);
        return ResultMessage.SUCCESS;
    }

    public ResultMessage reserveNoChoose(int one,int two,int three,int userId,int venueId,int showId) {
        Order order=new Order();
        order.setUserId(userId);
        order.setVenueId(venueId);
        order.setShowId(showId);
        double cost=0;
        List<ShowSeat> showSeatList=showDao.findShowSeatListByShowId(showId);
        List<ShowSeat> useShowSeatList=new ArrayList<ShowSeat>();
        List<ShowSeat> firstShowSeatList=new ArrayList<ShowSeat>();
        List<ShowSeat> secondShowSeatList=new ArrayList<ShowSeat>();
        List<ShowSeat> thirdShowSeatList=new ArrayList<ShowSeat>();
        double firstCost=0;
        double secondCost=0;
        double thirdCost=0;
        for(int i=0;i<showSeatList.size();i++){
            if(showSeatList.get(i).getLevel().equals("一等座")){
                firstShowSeatList.add(showSeatList.get(i));
                firstCost=showSeatList.get(i).getCost();
            }else if(showSeatList.get(i).getLevel().equals("二等座")){
                secondShowSeatList.add(showSeatList.get(i));
                secondCost=showSeatList.get(i).getCost();
            }else if(showSeatList.get(i).getLevel().equals("三等座")){
                thirdShowSeatList.add(showSeatList.get(i));
                thirdCost=showSeatList.get(i).getCost();
            }
        }
        for(int i=0;i<one;i++){
            useShowSeatList.add(firstShowSeatList.get(i));
        }
        for(int i=0;i<two;i++){
            useShowSeatList.add(secondShowSeatList.get(i));
        }
        for(int i=0;i<three;i++){
            useShowSeatList.add(thirdShowSeatList.get(i));
        }
        cost=cost+one*firstCost+two*secondCost+three*thirdCost;
        order.setCost(cost);
        Date date=new Date();
        order.setOrderTime(dateFormat(date));
        order.setState(OrderState.UNPAID.toString());
        orderDao.save(order);

        //ticket
        List<Order> orderList=orderDao.getOrderByUserId(userId);
        List<Order> orderList1=new ArrayList<Order>();
        for(int i=0;i<orderList.size();i++){
            if(orderList.get(i).getShowId()==showId){
                orderList1.add(orderList.get(i));
            }
        }
        for(int i=0;i<orderList.size();i++){
            if(orderList1.get(i).getId()>order.getId()){
                order=orderList1.get(i);
            }
        }
        for(int i=0;i<one;i++){
            Ticket ticket=new Ticket();
            ticket.setCost(firstCost);
            ticket.setOrder(order);
            ticket.setLevel("一等座");
            orderDao.saveTicket(ticket);
        }
        for(int i=0;i<two;i++){
            Ticket ticket=new Ticket();
            ticket.setCost(firstCost);
            ticket.setOrder(order);
            ticket.setLevel("二等座");
            orderDao.saveTicket(ticket);
        }
        for(int i=0;i<three;i++){
            Ticket ticket=new Ticket();
            ticket.setCost(firstCost);
            ticket.setOrder(order);
            ticket.setLevel("三等座");
            orderDao.saveTicket(ticket);
        }

        //orderRecord
        OrderRecord orderRecord=new OrderRecord();
        orderRecord.setOrderId(order.getId());
        orderRecord.setUserId(order.getUserId());
        orderRecord.setVenueId(order.getVenueId());
        orderRecord.setOrderAction(OrderAction.ORDER.toString());
        orderRecord.setTime(dateFormat(date));
        orderDao.saveOrderRecord(orderRecord);
        return ResultMessage.SUCCESS;
    }

    public ResultMessage pay(int orderId) {
        Order order = orderDao.getOrderById(orderId);
        if (order.getPayTime() == null) {
            Date date = new Date();
            order.setPayTime(dateFormat(date));
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
            orderRecord.setOrderAction(OrderAction.PAY.toString());
            orderRecord.setTime(dateFormat(date));
            orderDao.saveOrderRecord(orderRecord);
            return ResultMessage.SUCCESS;
        }
        return ResultMessage.FAIL;
    }

    public ResultMessage cancel(int orderId) {
        Order order = orderDao.getOrderById(orderId);
        if (order.getCancelTime() == null) {
            Date date = new Date();
            String dateString = null;
            order.setCancelTime(dateFormat(date));
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
            orderRecord.setOrderAction(OrderAction.CANCEL.toString());
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

    private String dateFormat(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        return format.format(date);
    }
}
