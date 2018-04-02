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

        //showSeat
        for (int i = 0; i < showSeatList.size(); i++) {
            ShowSeat showSeat = showSeatList.get(i);
            showSeat.setBooked(true);
            showDao.update(showSeat);
        }

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
        orderRecord.setOnline(true);
        orderDao.saveOrderRecord(orderRecord);
        return order.getId();
    }

    public int reserveNoChoose(int one, int two, int three, int userId, int venueId, int showId) {
        Order order = new Order();
        order.setUserId(userId);
        order.setVenueId(venueId);
        order.setShowId(showId);
        double cost = 0;

        double firstCost = 0;
        double secondCost = 0;
        double thirdCost = 0;

        List<ShowSeat> firstShowSeatList = getShowSeatList(order.getShowId(), one, "一等座");
        List<ShowSeat> secondShowSeatList = getShowSeatList(order.getShowId(), two, "二等座");
        List<ShowSeat> thirdShowSeatList = getShowSeatList(order.getShowId(), three, "三等座");
//        System.out.println(firstShowSeatList.size());
//        System.out.println(secondShowSeatList.size());
//        System.out.println(thirdShowSeatList.size());
        //座位不够，失败
        if (firstShowSeatList == null && secondShowSeatList == null && thirdShowSeatList == null) {
//            order.setState(OrderState.CANCELLED.toString());
//            order.setCancelTime(dateFormat(new Date()));
//            orderDao.update(order);
//            cancel(order.getId());
            System.out.println("座位不够");
            return -1;
        } else {
            if (firstShowSeatList.size() > 0) {
                firstCost = firstShowSeatList.get(0).getCost();
            }
            if (secondShowSeatList.size() > 0) {
                secondCost = secondShowSeatList.get(0).getCost();
            }
            if (thirdShowSeatList.size() > 0) {
                thirdCost = thirdShowSeatList.get(0).getCost();
            }
            //showSeat
//            for (int i = 0; i < one; i++) {
//                ShowSeat showSeat = firstShowSeatList.get(i);
//                showSeat.setBooked(true);
//                showDao.update(showSeat);
//            }
//            for (int i = 0; i < two; i++) {
//                ShowSeat showSeat = secondShowSeatList.get(i);
//                showSeat.setBooked(true);
//                showDao.update(showSeat);
//            }
//            for (int i = 0; i < three; i++) {
//                ShowSeat showSeat = thirdShowSeatList.get(i);
//                showSeat.setBooked(true);
//                showDao.update(showSeat);
//            }
//
//            Show show=showDao.findById(showId);
//            show.setCurrentSeats(show.getCurrentSeats()-one-two-three);
//            showDao.update(show);

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
            for (int i = 0; i < orderList1.size(); i++) {
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
                ticket.setSeatId(-1);
                orderDao.saveTicket(ticket);
            }
            for (int i = 0; i < two; i++) {
                Ticket ticket = new Ticket();
                ticket.setCost(firstCost);
                ticket.setOrder(order);
                ticket.setLevel("二等座");
                ticket.setSeatId(-1);
                orderDao.saveTicket(ticket);
            }
            for (int i = 0; i < three; i++) {
                Ticket ticket = new Ticket();
                ticket.setCost(firstCost);
                ticket.setOrder(order);
                ticket.setLevel("三等座");
                ticket.setSeatId(-1);
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
            orderRecord.setOnline(true);
            orderDao.saveOrderRecord(orderRecord);
            return order.getId();
        }
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

        double firstCost = 0;
        double secondCost = 0;
        double thirdCost = 0;
        List<ShowSeat> firstShowSeatList = getShowSeatList(order.getShowId(), one, "一等座");
        List<ShowSeat> secondShowSeatList = getShowSeatList(order.getShowId(), two, "二等座");
        List<ShowSeat> thirdShowSeatList = getShowSeatList(order.getShowId(), three, "三等座");
        //座位不够，失败
        if (firstShowSeatList == null && secondShowSeatList == null && thirdShowSeatList == null) {
            order.setState(OrderState.CANCELLED.toString());
            order.setCancelTime(dateFormat(new Date()));
            orderDao.update(order);
            cancel(order.getId());
            return -4;
        } else {
            if (firstShowSeatList.size() > 0) {
                firstCost = firstShowSeatList.get(0).getCost();
            }
            if (secondShowSeatList.size() > 0) {
                secondCost = secondShowSeatList.get(0).getCost();
            }
            if (thirdShowSeatList.size() > 0) {
                thirdCost = thirdShowSeatList.get(0).getCost();
            }


            cost = cost + one * firstCost + two * secondCost + three * thirdCost;
            if (user.getBalance() < cost) {
                return -2;
            }

            for (int i = 0; i < one; i++) {
                ShowSeat showSeat = firstShowSeatList.get(i);
                showSeat.setBooked(true);
                showDao.update(showSeat);
            }
            for (int i = 0; i < two; i++) {
                ShowSeat showSeat = secondShowSeatList.get(i);
                showSeat.setBooked(true);
                showDao.update(showSeat);
            }
            for (int i = 0; i < three; i++) {
                ShowSeat showSeat = thirdShowSeatList.get(i);
                showSeat.setBooked(true);
                showDao.update(showSeat);
            }

            Show show=showDao.findById(showId);
            show.setCurrentSeats(show.getCurrentSeats()-one-two-three);
            showDao.update(show);


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
            for (int i = 0; i < orderList1.size(); i++) {
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
                ticket.setSeatId(firstShowSeatList.get(i).getId());
                orderDao.saveTicket(ticket);
            }
            for (int i = 0; i < two; i++) {
                Ticket ticket = new Ticket();
                ticket.setCost(firstCost);
                ticket.setOrder(order);
                ticket.setLevel("二等座");
                ticket.setSeatId(secondShowSeatList.get(i).getId());
                orderDao.saveTicket(ticket);
            }
            for (int i = 0; i < three; i++) {
                Ticket ticket = new Ticket();
                ticket.setCost(firstCost);
                ticket.setOrder(order);
                ticket.setLevel("三等座");
                ticket.setSeatId(thirdShowSeatList.get(i).getId());
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
            orderRecord.setOnline(false);
            orderDao.saveOrderRecord(orderRecord);

            //pay
            Date payDate = new Date();
            order.setPayTime(dateFormat(payDate));
            orderDao.update(order);

            ShowEarning showEarning = showDao.findShowEarningByShowId(show.getId());
            if (showEarning.getId() != -1) {
                double money = calculateMoney(order.getCost(), user);

                showEarning.setOnlineEarning(showEarning.getOfflineEarning() + money);
                showDao.update(showEarning);

                user.setBalance(user.getBalance() - money);
                user.setTotalIntegral((int) (user.getTotalIntegral() + money));
                user.setCurrentIntegral((int) (user.getCurrentIntegral() + money));
                userDao.update(user);

                OrderRecord payOrderRecord = new OrderRecord();
                payOrderRecord.setOrderId(order.getId());
                payOrderRecord.setUserId(order.getUserId());
                payOrderRecord.setVenueId(order.getVenueId());
                payOrderRecord.setShowId(order.getShowId());
                payOrderRecord.setCost(money);
                payOrderRecord.setOrderAction(OrderAction.PAY.toString());
                payOrderRecord.setTime(dateFormat(payDate));
                payOrderRecord.setOnline(false);
                orderDao.saveOrderRecord(payOrderRecord);

                checkTicket(user.getId());
            }
            return order.getId();
        }
    }

    public ResultMessage pay(int orderId, int couponType) {

        Order order = orderDao.findOrderById(orderId);
        Date date = dateParse(order.getOrderTime());
        Date now = new Date();
        //过期，自动取消
        if (date.getTime() + 15 * 60 * 1000 < now.getTime()) {
            cancelWithoutPay(order.getId());
            System.out.println(1111111111);
            return ResultMessage.FAIL;
        }
        User user = userDao.findById(order.getUserId());

        if (order.getPayTime() == null) {
            Show show = showDao.findById(order.getShowId());
            ShowEarning showEarning = showDao.findShowEarningByShowId(show.getId());
            System.out.println(2222222);
            if (showEarning.getId() != -1) {
                double money = order.getCost();
                if (couponType != -1) {
                    Coupon coupon = new Coupon();
                    for (int i = 0; i < user.getCouponList().size(); i++) {
                        if (user.getCouponList().get(i).getType() == couponType) {
                            coupon = user.getCouponList().get(i);
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
                    System.out.println(333333333);
                    cancelWithoutPay(orderId);
                    return ResultMessage.FAIL;
                }

                showEarning.setOnlineEarning(showEarning.getOnlineEarning() + money);
                showDao.update(showEarning);
                user.setBalance(user.getBalance() - money);
                user.setTotalIntegral((int) (user.getTotalIntegral() + money));
                user.setCurrentIntegral((int) (user.getCurrentIntegral() + money));
                userDao.update(user);

                OrderRecord orderRecord = new OrderRecord();
                orderRecord.setOrderId(orderId);
                orderRecord.setUserId(order.getUserId());
                orderRecord.setVenueId(order.getVenueId());
                orderRecord.setShowId(order.getShowId());
                orderRecord.setCost(money);
                orderRecord.setOrderAction(OrderAction.PAY.toString());
                orderRecord.setTime(dateFormat(now));
                orderRecord.setOnline(true);
                orderDao.saveOrderRecord(orderRecord);

                order.setPayTime(dateFormat(now));
                if (order.isChoose()) {
                    order.setState(OrderState.UNSTART.toString());
                } else {
                    order.setState(OrderState.UNTICKETCONFIRMED.toString());
                }
                orderDao.update(order);

                checkUserLevel(user.getId());

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
            order.setState(OrderState.CANCELLED.toString());
            orderDao.update(order);

            Show show = showDao.findById(order.getShowId());
            User user = userDao.findById(order.getUserId());
            ShowEarning showEarning = showDao.findShowEarningByShowId(show.getId());
            if (showEarning.getId() != -1) {
                double money = calculateCancelMoney(order, user);
                showEarning.setOnlineEarning(showEarning.getOnlineEarning() - money);
                showDao.update(showEarning);

                user.setBalance(user.getBalance() + money);
                user.setTotalIntegral((int) (user.getTotalIntegral() - order.getCost()));
                user.setCurrentIntegral((int) (user.getCurrentIntegral() - order.getCost()));
                userDao.update(user);

                OrderRecord orderRecord = new OrderRecord();
                orderRecord.setOrderId(orderId);
                orderRecord.setUserId(order.getUserId());
                orderRecord.setVenueId(order.getVenueId());
                orderRecord.setShowId(order.getShowId());
                orderRecord.setCost(money);
                orderRecord.setOrderAction(OrderAction.CANCEL.toString());
                orderRecord.setTime(dateFormat(date));
                orderRecord.setOnline(true);
                orderDao.saveOrderRecord(orderRecord);
            }



            freeShowSeat(order);

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

        boolean unPaid = false;
        for (Order order : orderList) {
            System.out.println(order.getState());
            if (order.getState().equals("UNPAID")) {
                Date date = dateParse(order.getOrderTime());
                Date now = new Date();
                if (date.getTime() + 15 * 60 * 1000 < now.getTime()) {
                    cancelWithoutPay(order.getId());
                    unPaid = true;
                }
            }
        }
        if (unPaid) {
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

            freeShowSeat(order);

            OrderRecord orderRecord = new OrderRecord();
            orderRecord.setOrderId(orderId);
            orderRecord.setUserId(order.getUserId());
            orderRecord.setVenueId(order.getVenueId());
            orderRecord.setShowId(order.getShowId());
            orderRecord.setCost(order.getCost());
            orderRecord.setOrderAction(OrderAction.CANCEL.toString());
            orderRecord.setTime(dateFormat(date));
            orderRecord.setOnline(true);
            orderDao.saveOrderRecord(orderRecord);
            return ResultMessage.SUCCESS;
        }
        return ResultMessage.FAIL;
    }

    public ResultMessage allocateTicket() {
        List<Order> orderList = orderDao.findOrderByState(OrderState.UNTICKETCONFIRMED);
        List<Order> allocateOrderList = new ArrayList<Order>();
        for (int i = 0; i < orderList.size(); i++) {
            Show show = showDao.findById(orderList.get(i).getShowId());
            if (new Date().getTime() - dateParse(show.getStartTime()).getTime() <= 14 * 24 * 60 * 60 * 1000) {
                allocateOrderList.add(orderList.get(i));
            }
        }
        for (int i = 0; i < allocateOrderList.size(); i++) {
            Order order = allocateOrderList.get(i);
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
            Show show = showDao.findById(order.getShowId());
            List<ShowSeat> showSeatListOne = getShowSeatList(show.getId(), one, "一等座");
            List<ShowSeat> showSeatListTwo = getShowSeatList(show.getId(), two, "二等座");
            List<ShowSeat> showSeatListThree = getShowSeatList(show.getId(), three, "三等座");
            //座位不够，失败
            if (showSeatListOne == null || showSeatListTwo == null || showSeatListThree == null) {
                order.setState(OrderState.CANCELLED.toString());
                order.setCancelTime(dateFormat(new Date()));
                orderDao.update(order);
                cancel(order.getId());
            } else {
                int a = 0;
                int b = 0;
                int c = 0;
                for (Ticket ticket : ticketList) {
                    if (ticket.getLevel().equals("一等座")) {
                        ticket.setSeatId(showSeatListOne.get(a).getId());
                        ShowSeat showSeat=showSeatListOne.get(a);
                        showSeat.setBooked(true);
                        showDao.update(showSeat);
                        a++;
                    } else if (ticket.getLevel().equals("二等座")) {
                        ticket.setSeatId(showSeatListTwo.get(b).getId());
                        ShowSeat showSeat=showSeatListTwo.get(b);
                        showSeat.setBooked(true);
                        showDao.update(showSeat);
                        b++;
                    } else if (ticket.getLevel().equals("三等座")) {
                        ticket.setSeatId(showSeatListThree.get(c).getId());
                        ShowSeat showSeat=showSeatListThree.get(c);
                        showSeat.setBooked(true);
                        showDao.update(showSeat);
                        c++;
                    }
                    orderDao.update(ticket);
                }
                order.setState(OrderState.UNSTART.toString());
                orderDao.update(order);
            }
        }
        //演出状态检查
        checkShow();
        return ResultMessage.SUCCESS;
    }

    private void checkShow(){
        try {
            List<Show> unStartShowList = showDao.findByState(ProgressType.PRESALE.toString());
            for (int i = 0; i < unStartShowList.size(); i++) {
                Show show = unStartShowList.get(i);
                Date startDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(show.getStartTime());
                Date now = new Date();
                if (now.getTime() >= startDate.getTime()) {
                    show.setProgressType(ProgressType.START.toString());
                    showDao.update(show);
                }
            }
            List<Show> StartShowList = showDao.findByState(ProgressType.START.toString());
            for (int i = 0; i < StartShowList.size(); i++) {
                Show show = StartShowList.get(i);
                Date endDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(show.getEndTime());
                Date now = new Date();
                if (now.getTime() >= endDate.getTime()) {
                    show.setProgressType(ProgressType.END.toString());
                    showDao.update(show);
                }
            }
        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    private void freeShowSeat(Order order) {
        List<Ticket> ticketList = order.getTicketList();
        for (int i = 0; i < ticketList.size(); i++) {
            if (ticketList.get(i).getSeatId() != -1) {
                ShowSeat showSeat = showDao.findShowSeat(ticketList.get(i).getSeatId());
                showSeat.setBooked(false);
                showDao.update(showSeat);
            }
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

    private double calculateCancelMoney(Order order, User user) {
        double cost = order.getCost();
        cost = calculateMoney(cost, user);
        Date now = new Date();
        Date orderDate = dateParse(order.getOrderTime());
        long count = now.getTime() - orderDate.getTime();
        if (count >= 7 * 24 * 60 * 60 * 1000) {
            return cost;
        } else if (count >= 24 * 60 * 60 * 1000) {
            return cost * 0.8;
        } else {
            return cost * 0.5;
        }
    }

    private List<ShowSeat> getShowSeatList(int showId, int count, String level) {
        List<ShowSeat> showSeats = new ArrayList<ShowSeat>();
        List<ShowSeat> showSeatList =showDao.findById(showId).getSeatList();
        for (int i = 0; i < showSeatList.size(); i++) {
//            System.out.println();
            if (!showSeatList.get(i).isBooked()&&showSeatList.get(i).getLevel().equals(level)) {
                showSeats.add(showSeatList.get(i));
                count--;
                System.out.println(count);
                if (count == 0) {
                    return showSeats;
                }
            }
        }
        return null;
    }

    private void checkUserLevel(int userId) {
        User user = userDao.findById(userId);
        int integral = user.getTotalIntegral();
        if (integral >= 50000) {
            user.setLevel(VIPLevel.DIAMOND.toString());
        } else if (integral >= 10000) {
            user.setLevel(VIPLevel.PLATINUM.toString());
        } else if (integral >= 5000) {
            user.setLevel(VIPLevel.GOLDEN.toString());
        } else if (integral >= 1000) {
            user.setLevel(VIPLevel.SILVER.toString());
        } else if (integral >= 100) {
            user.setLevel(VIPLevel.BRONZE.toString());
        } else {
            user.setLevel(VIPLevel.COMMON.toString());
        }
        userDao.update(user);
    }

}
