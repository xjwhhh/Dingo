package service.bean;

import dao.*;
import model.*;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.OrderManageService;

import java.text.ParseException;
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

    public int reserveChoose(String seatIdListJson, int userId, int venueId, int showId) {
//        ShowDaoBean showDao=new ShowDaoBean();
//        OrderDaoBean orderDao=new OrderDaoBean();
        JSONArray jsonArray = JSONArray.fromObject(seatIdListJson);
        List<Integer> showSeatIdList = new ArrayList<Integer>();
        for (int i = 0; i < jsonArray.size(); i++) {
            showSeatIdList.add(Integer.parseInt(jsonArray.get(i).toString()));
        }

        List<ShowSeat> showSeatList = new ArrayList<ShowSeat>();
        for (int i = 0; i < showSeatIdList.size(); i++) {
            //showSeat
            ShowSeat showSeat = showDao.findShowSeat(showSeatIdList.get(i));
            showSeatList.add(showSeat);
            showSeat.setBooked(true);
            showDao.update(showSeat);
        }
        System.out.println(showSeatList);

        //show
        Show show = showDao.findById(showId);
        show.setCurrentSeats(show.getCurrentSeats() - showSeatIdList.size());
        showDao.update(show);

        //order
        Order order = new Order();
        order.setUserId(userId);
        order.setVenueId(venueId);
        order.setShowId(showId);
        double cost = 0;
        for (int i = 0; i < showSeatList.size(); i++) {
            cost += showSeatList.get(i).getCost();
        }
        order.setCost(cost);
        Date date = new Date();
        order.setOrderTime(dateFormat(date));
        order.setState(OrderState.UNPAID.toString());
        order.setChoose(true);
        orderDao.save(order);

        //ticket
        List<Order> orderList = orderDao.findOrderByUserId(userId);
        List<Order> orderList1 = new ArrayList<Order>();
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getShowId() == showId) {
                orderList1.add(orderList.get(i));
            }
        }
        for (int i = 0; i < orderList1.size(); i++) {
            if (orderList1.get(i).getId() > order.getId()) {
                order = orderList1.get(i);
            }
        }
        for (int i = 0; i < showSeatList.size(); i++) {
            Ticket ticket = new Ticket();
            ticket.setCost(showSeatList.get(i).getCost());
            ticket.setSeatId(showSeatList.get(i).getId());
            ticket.setOrder(order);
            ticket.setCome(false);
            System.out.println(showSeatList.get(i).getLevel());
            ticket.setLevel(showSeatList.get(i).getLevel());
            orderDao.saveTicket(ticket);
        }

        //orderRecord
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setOrderId(order.getId());
        orderRecord.setUserId(order.getUserId());
        orderRecord.setVenueId(order.getVenueId());
        orderRecord.setShowId(order.getShowId());
        orderRecord.setCost(order.getCost());
        orderRecord.setOrderAction(OrderAction.ORDER.toString());
        orderRecord.setTime(dateFormat(date));
        orderDao.saveOrderRecord(orderRecord);
        return order.getId();
    }

    public int reserveNoChoose(int one, int two, int three, int userId, int venueId, int showId) {
        Order order = new Order();
        order.setUserId(userId);
        order.setVenueId(venueId);
        order.setShowId(showId);
        double cost = 0;
        List<ShowSeat> showSeatList = showDao.findShowSeatListByShowId(showId);
        List<ShowSeat> useShowSeatList = new ArrayList<ShowSeat>();
        List<ShowSeat> firstShowSeatList = new ArrayList<ShowSeat>();
        List<ShowSeat> secondShowSeatList = new ArrayList<ShowSeat>();
        List<ShowSeat> thirdShowSeatList = new ArrayList<ShowSeat>();
        double firstCost = 0;
        double secondCost = 0;
        double thirdCost = 0;
        for (int i = 0; i < showSeatList.size(); i++) {
            if (showSeatList.get(i).getLevel().equals("一等座")) {
                firstShowSeatList.add(showSeatList.get(i));
                firstCost = showSeatList.get(i).getCost();
            } else if (showSeatList.get(i).getLevel().equals("二等座")) {
                secondShowSeatList.add(showSeatList.get(i));
                secondCost = showSeatList.get(i).getCost();
            } else if (showSeatList.get(i).getLevel().equals("三等座")) {
                thirdShowSeatList.add(showSeatList.get(i));
                thirdCost = showSeatList.get(i).getCost();
            }
        }
        for (int i = 0; i < one; i++) {
            useShowSeatList.add(firstShowSeatList.get(i));
        }
        for (int i = 0; i < two; i++) {
            useShowSeatList.add(secondShowSeatList.get(i));
        }
        for (int i = 0; i < three; i++) {
            useShowSeatList.add(thirdShowSeatList.get(i));
        }
        cost = cost + one * firstCost + two * secondCost + three * thirdCost;
        order.setCost(cost);
        Date date = new Date();
        order.setOrderTime(dateFormat(date));
        order.setState(OrderState.UNPAID.toString());
        order.setChoose(false);
        orderDao.save(order);

        //ticket
        List<Order> orderList = orderDao.findOrderByUserId(userId);
        List<Order> orderList1 = new ArrayList<Order>();
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getShowId() == showId) {
                orderList1.add(orderList.get(i));
            }
        }
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList1.get(i).getId() > order.getId()) {
                order = orderList1.get(i);
            }
        }
        for (int i = 0; i < one; i++) {
            Ticket ticket = new Ticket();
            ticket.setCost(firstCost);
            ticket.setOrder(order);
            ticket.setLevel("一等座");
            ticket.setCome(false);
            orderDao.saveTicket(ticket);
        }
        for (int i = 0; i < two; i++) {
            Ticket ticket = new Ticket();
            ticket.setCost(firstCost);
            ticket.setOrder(order);
            ticket.setLevel("二等座");
            orderDao.saveTicket(ticket);
        }
        for (int i = 0; i < three; i++) {
            Ticket ticket = new Ticket();
            ticket.setCost(firstCost);
            ticket.setOrder(order);
            ticket.setLevel("三等座");
            orderDao.saveTicket(ticket);
        }

        //orderRecord
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setOrderId(order.getId());
        orderRecord.setUserId(order.getUserId());
        orderRecord.setVenueId(order.getVenueId());
        orderRecord.setShowId(order.getShowId());
        orderRecord.setCost(order.getCost());
        orderRecord.setOrderAction(OrderAction.ORDER.toString());
        orderRecord.setTime(dateFormat(date));
        orderDao.saveOrderRecord(orderRecord);
        return order.getId();
    }

    public int offLineBuyTicket(int one, int two, int three, String userAccount, String userPassword, int venueId, int showId) {
        User user = userDao.find(userAccount, userPassword);
        if (user.getId() == -1) {
            return -1;
        }
        Order order = new Order();
        order.setUserId(user.getId());
        order.setVenueId(venueId);
        order.setShowId(showId);
        double cost = 0;
        List<ShowSeat> showSeatList = showDao.findShowSeatListByShowId(showId);
        List<ShowSeat> useShowSeatList = new ArrayList<ShowSeat>();
        List<ShowSeat> firstShowSeatList = new ArrayList<ShowSeat>();
        List<ShowSeat> secondShowSeatList = new ArrayList<ShowSeat>();
        List<ShowSeat> thirdShowSeatList = new ArrayList<ShowSeat>();
        double firstCost = 0;
        double secondCost = 0;
        double thirdCost = 0;
        for (int i = 0; i < showSeatList.size(); i++) {
            if (showSeatList.get(i).getLevel().equals("一等座")) {
                firstShowSeatList.add(showSeatList.get(i));
                firstCost = showSeatList.get(i).getCost();
            } else if (showSeatList.get(i).getLevel().equals("二等座")) {
                secondShowSeatList.add(showSeatList.get(i));
                secondCost = showSeatList.get(i).getCost();
            } else if (showSeatList.get(i).getLevel().equals("三等座")) {
                thirdShowSeatList.add(showSeatList.get(i));
                thirdCost = showSeatList.get(i).getCost();
            }
        }
        for (int i = 0; i < one; i++) {
            useShowSeatList.add(firstShowSeatList.get(i));
        }
        for (int i = 0; i < two; i++) {
            useShowSeatList.add(secondShowSeatList.get(i));
        }
        for (int i = 0; i < three; i++) {
            useShowSeatList.add(thirdShowSeatList.get(i));
        }
        cost = cost + one * firstCost + two * secondCost + three * thirdCost;
        if (user.getBalance() < cost) {
            return -2;
        }
        order.setCost(cost);
        Date date = new Date();
        order.setOrderTime(dateFormat(date));
        order.setState(OrderState.UNPAID.toString());
        order.setChoose(false);
        orderDao.save(order);

        //ticket
        List<Order> orderList = orderDao.findOrderByUserId(user.getId());
        List<Order> orderList1 = new ArrayList<Order>();
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getShowId() == showId) {
                orderList1.add(orderList.get(i));
            }
        }
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList1.get(i).getId() > order.getId()) {
                order = orderList1.get(i);
            }
        }
        for (int i = 0; i < one; i++) {
            Ticket ticket = new Ticket();
            ticket.setCost(firstCost);
            ticket.setOrder(order);
            ticket.setLevel("一等座");
            ticket.setCome(false);
            orderDao.saveTicket(ticket);
        }
        for (int i = 0; i < two; i++) {
            Ticket ticket = new Ticket();
            ticket.setCost(firstCost);
            ticket.setOrder(order);
            ticket.setLevel("二等座");
            orderDao.saveTicket(ticket);
        }
        for (int i = 0; i < three; i++) {
            Ticket ticket = new Ticket();
            ticket.setCost(firstCost);
            ticket.setOrder(order);
            ticket.setLevel("三等座");
            orderDao.saveTicket(ticket);
        }

        //orderRecord
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setOrderId(order.getId());
        orderRecord.setUserId(order.getUserId());
        orderRecord.setVenueId(order.getVenueId());
        orderRecord.setShowId(order.getShowId());
        orderRecord.setCost(order.getCost());
        orderRecord.setOrderAction(OrderAction.ORDER.toString());
        orderRecord.setTime(dateFormat(date));
        orderDao.saveOrderRecord(orderRecord);

        //pay
        Date payDate = new Date();
        order.setPayTime(dateFormat(payDate));
        orderDao.update(order);

        Show show = showDao.findById(order.getShowId());
        ShowEarning showEarning = showDao.findShowEarningByShowId(show.getId());
        if (showEarning.getId() != -1) {
            double money = calculateMoney(order.getCost(), user);

            showEarning.setOnlineEarning(showEarning.getOfflineEarning() + money);
            showDao.update(showEarning);

            user.setBalance(user.getBalance() - money);
            userDao.update(user);

            OrderRecord payOrderRecord = new OrderRecord();
            payOrderRecord.setOrderId(order.getId());
            payOrderRecord.setUserId(order.getUserId());
            payOrderRecord.setVenueId(order.getVenueId());
            payOrderRecord.setShowId(order.getShowId());
            payOrderRecord.setCost(order.getCost());
            payOrderRecord.setOrderAction(OrderAction.PAY.toString());
            payOrderRecord.setTime(dateFormat(payDate));
            orderDao.saveOrderRecord(payOrderRecord);
        }
        return order.getId();
    }

    public ResultMessage pay(int orderId, int couponType) {


        Order order = orderDao.findOrderById(orderId);
        Date date = dateParse(order.getOrderTime());
        Date now = new Date();
        //过期，自动取消
        if (date.getTime() + 15 * 60 * 1000 >= now.getTime()) {
            order.setState(OrderState.CANCELLED.toString());
            cancelWithoutPay(order.getId());
            return ResultMessage.FAIL;
        }
        User user = userDao.findById(order.getUserId());

        if (order.getPayTime() == null) {
            Show show = showDao.findById(order.getShowId());
            ShowEarning showEarning = showDao.findShowEarningByShowId(show.getId());
            if (showEarning.getId() != -1) {


                double money=order.getCost();
                if(couponType!=-1) {
                    Coupon coupon=new Coupon();
                    for(int i=0;i<user.getCouponList().size();i++){
                        if(user.getCouponList().get(i).getType()==couponType){
                            coupon=user.getCouponList().get(i);
//                            user.getCouponList().remove(i);
                            coupon.setUsed(true);
                            userDao.update(coupon);
                            break;
                        }
                    }
                    money = calculateMoneyWithCoupon(order.getCost(), coupon);
                }
                money = calculateMoney(money, user);
                //客户余额不够
                if (user.getBalance() < money) {
                    return ResultMessage.FAIL;
                }

                showEarning.setOnlineEarning(showEarning.getOnlineEarning() + money);
                showDao.update(showEarning);
                user.setBalance(user.getBalance() - money);
                userDao.update(user);

                OrderRecord orderRecord = new OrderRecord();
                orderRecord.setOrderId(orderId);
                orderRecord.setUserId(order.getUserId());
                orderRecord.setVenueId(order.getVenueId());
                orderRecord.setShowId(order.getShowId());
                orderRecord.setCost(order.getCost());
                orderRecord.setOrderAction(OrderAction.PAY.toString());
                orderRecord.setTime(dateFormat(now));
                orderDao.saveOrderRecord(orderRecord);

                order.setPayTime(dateFormat(now));
                orderDao.update(order);

                return ResultMessage.SUCCESS;
            }
        }
        return ResultMessage.FAIL;
    }

    public ResultMessage cancel(int orderId) {
        Order order = orderDao.findOrderById(orderId);
        Date date = new Date();

        if (order.getCancelTime() == null) {
            //付过钱
            order.setCancelTime(dateFormat(date));
            orderDao.update(order);

                Show show = showDao.findById(order.getShowId());
                User user = userDao.findById(order.getUserId());
                ShowEarning showEarning = showDao.findShowEarningByShowId(show.getId());
                if (showEarning.getId() != -1) {
                    double money = calculateMoney(order.getCost(), user);
                    showEarning.setOnlineEarning(showEarning.getOnlineEarning() - money);
                    showDao.update(showEarning);

                    user.setBalance(user.getBalance() + money);
                    userDao.update(user);
                }

            OrderRecord orderRecord = new OrderRecord();
            orderRecord.setOrderId(orderId);
            orderRecord.setUserId(order.getUserId());
            orderRecord.setVenueId(order.getVenueId());
            orderRecord.setShowId(order.getShowId());
            orderRecord.setCost(order.getCost());
            orderRecord.setOrderAction(OrderAction.CANCEL.toString());
            orderRecord.setTime(dateFormat(date));
            orderDao.saveOrderRecord(orderRecord);
            return ResultMessage.SUCCESS;
        }
        return ResultMessage.FAIL;

    }

    public Order getOrderByOrderId(int orderId) {
        return orderDao.findOrderById(orderId);
    }

    public List<Order> getOrderListByUserId(int userId) {
        List<Order> orderList = orderDao.findOrderByUserId(userId);
        System.out.println(orderList.size());

        boolean unPaid=false;
        for (Order order : orderList) {
            System.out.println(order.getState());
            if (order.getState().equals("UNPAID")) {
                Date date = dateParse(order.getOrderTime());
                Date now = new Date();
                if (date.getTime() + 15 * 60 * 1000 < now.getTime()) {
                    cancelWithoutPay(order.getId());
                    unPaid=true;
                }
            }
        }
        if(unPaid){
            orderList = orderDao.findOrderByUserId(userId);
        }
        return orderList;
    }

    public List<Order> getOrderListByShowId(int showId) {
        return orderDao.findOrderByShowId(showId);
    }

    public List<Order> getOrderListByVenueId(int venueId) {
        return orderDao.findOrderByVenueId(venueId);
    }

    public List<Order> getOrderListByState(OrderState orderState) {
        return orderDao.findOrderByState(orderState);
    }

    public List<Order> getAllOrders() {
        return null;
    }

    public ResultMessage checkTicket(int ticketId) {
        Ticket ticket = orderDao.findTicketById(ticketId);
        ticket.setCome(true);
        return orderDao.update(ticket);

    }

    public List<OrderRecord> getOrderRecordListByUserId(int userId) {
        return orderDao.findOrderRecordByUserId(userId);
    }

    public List<OrderRecord> getOrderRecordListByVenueId(int venueId) {
        return orderDao.findOrderRecordByVenueId(venueId);
    }

    public ResultMessage cancelWithoutPay(int orderId) {
        Order order = orderDao.findOrderById(orderId);
        if (order.getCancelTime() == null) {
            Date date = new Date();
            order.setState(OrderState.CANCELLED.toString());
            order.setCancelTime(dateFormat(date));
            orderDao.update(order);

            OrderRecord orderRecord = new OrderRecord();
            orderRecord.setOrderId(orderId);
            orderRecord.setUserId(order.getUserId());
            orderRecord.setVenueId(order.getVenueId());
            orderRecord.setShowId(order.getShowId());
            orderRecord.setCost(order.getCost());
            orderRecord.setOrderAction(OrderAction.CANCEL.toString());
            orderRecord.setTime(dateFormat(date));
            orderDao.saveOrderRecord(orderRecord);
            return ResultMessage.SUCCESS;
        }
        return ResultMessage.FAIL;
    }

    public void CountDown(int orderId) {
        CountDown countDown = new CountDown();
        countDown.run();
        Order order = orderDao.findOrderById(orderId);
        if (order.getPayTime() == null) {
            this.cancelWithoutPay(orderId);
            System.out.println("未付款");
        } else {
            System.out.println("已付款");
        }
    }

    private String dateFormat(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        return format.format(date);
    }

    private Date dateParse(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        Date date = new Date();
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static void main(String[] args) {
        int[] a = {25};
        String aa = JSONArray.fromObject(a).toString();
        System.out.println(aa);
        OrderManageServiceBean orderManageServiceBean = new OrderManageServiceBean();
        orderManageServiceBean.reserveChoose(aa, 1, 1, 2);
    }

    private double calculateMoney(double money, User user) {
        if (user.getLevel().equals("BRONZE")) {
            money *= 0.95;
        } else if (user.getLevel().equals("SILVER")) {
            money *= 0.9;
        } else if (user.getLevel().equals("GOLDEN")) {
            money *= 0.85;
        } else if (user.getLevel().equals("PLATINUM")) {
            money *= 0.8;
        } else if (user.getLevel().equals("DIAMOND")) {
            money *= 0.7;
        }
        return money;
    }

    private double calculateMoneyWithCoupon(double money, Coupon coupon) {
        switch (coupon.getType()) {
            case 1:
                money -= 10;
                break;
            case 2:
                money -= 100;
                break;
            case 3:
                money -= 200;
                break;
        }
        return money;
    }

    private Order allocateTicket(int orderId) {
        Order order = orderDao.findOrderById(orderId);
        List<Ticket> ticketList = order.getTicketList();
        int one = 0;
        int two = 0;
        int three = 0;
        for (Ticket ticket : ticketList) {
            if (ticket.getLevel().equals("一等座")) {
                one++;
            } else if (ticket.getLevel().equals("二等座")) {
                two++;
            } else if (ticket.getLevel().equals("三等座")) {
                three++;
            }
        }
        List<ShowSeat> showSeatListOne = getShowSeatList(one, "一等座");
        List<ShowSeat> showSeatListTwo = getShowSeatList(two, "二等座");
        List<ShowSeat> showSeatListThree = getShowSeatList(three, "三等座");

        return order;
    }

    private List<ShowSeat> getShowSeatList(int count, String level) {
        return new ArrayList<ShowSeat>();
    }

}
