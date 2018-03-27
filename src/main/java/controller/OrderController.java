package controller;

import model.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.bean.OrderManageServiceBean;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    OrderManageServiceBean orderManageServiceBean;

    @RequestMapping(value = "/saveOrderChoose", method = RequestMethod.POST)
    @ResponseBody
    public int saveOrderChoose(
            @RequestParam("seatIdListJson") String seatIdListJson,
            @RequestParam("userId") String userId,
            @RequestParam("venueId") String venueId,
            @RequestParam("showId") String showId) {
        return orderManageServiceBean.reserveChoose(seatIdListJson, Integer.parseInt(userId), Integer.parseInt(venueId), Integer.parseInt(showId));
    }

    @RequestMapping(value = "/saveOrderNoChoose", method = RequestMethod.POST)
    @ResponseBody
    public int saveOrderNoChoose(
            @RequestParam("one") String one,
            @RequestParam("two") String two,
            @RequestParam("three") String three,
            @RequestParam("userId") String userId,
            @RequestParam("venueId") String venueId,
            @RequestParam("showId") String showId) {
        return orderManageServiceBean.reserveNoChoose(Integer.parseInt(one), Integer.parseInt(two), Integer.parseInt(three), Integer.parseInt(userId), Integer.parseInt(venueId), Integer.parseInt(showId));
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage payOrder(
            @RequestParam("orderId") String orderId,
            @RequestParam("coupon") String couponJson) {
        JSONObject jsonObject = JSONObject.fromObject(couponJson);
        Coupon coupon = (Coupon) JSONObject.toBean(jsonObject, Coupon.class);
        return orderManageServiceBean.pay(Integer.parseInt(orderId), coupon);
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage cancelOrder(
            @RequestParam("orderId") String orderId) {
        return orderManageServiceBean.cancel(Integer.parseInt(orderId));
    }

    @RequestMapping(value = "/getOrderByState", method = RequestMethod.POST)
    @ResponseBody
    public List<Order> getOrderByType(
            @RequestParam("orderState") String orderStateString) {
        OrderState orderState = OrderState.class.getEnumConstants()[Integer.parseInt(orderStateString)];
        return orderManageServiceBean.getOrderListByState(orderState);
    }

    @RequestMapping(value = "/getOrderById", method = RequestMethod.POST)
    @ResponseBody
    public Order getOrderById(
            @RequestParam("orderId") String orderId) {
        return orderManageServiceBean.getOrderByOrderId(Integer.parseInt(orderId));
    }

    @RequestMapping(value = "/getOrderByShowId", method = RequestMethod.POST)
    @ResponseBody
    public List<Order> getOrderByShowId(@RequestParam("showId") String showId) {
        return orderManageServiceBean.getOrderListByShowId(Integer.parseInt(showId));
    }

    @RequestMapping(value = "/getOrderByVenueId", method = RequestMethod.POST)
    @ResponseBody
    public List<Order> getOrderByVenueId(@RequestParam("venueId") String venueId) {
        return orderManageServiceBean.getOrderListByVenueId(Integer.parseInt(venueId));
    }

    @RequestMapping(value = "/getOrderByUserId", method = RequestMethod.POST)
    @ResponseBody
    public List<Order> getOrderByUserId(@RequestParam("userId") String userId) {
        return orderManageServiceBean.getOrderListByUserId(Integer.parseInt(userId));
    }

    @RequestMapping(value = "/checkTicket", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage checkTicket(
            @RequestParam("ticketId") String ticketId
    ) {
        return orderManageServiceBean.checkTicket(Integer.parseInt(ticketId));
    }

    @RequestMapping(value = "/payCountDown", method = RequestMethod.POST)
    @ResponseBody
    public void payCountDown(@RequestParam("orderId") String orderId) {
//        return orderManageServiceBean.getOrderListByUserId(Integer.parseInt(userId));
//        return ResultMessage.SUCCESS;
    }

    @RequestMapping(value = "/getOrderRecordByUserId", method = RequestMethod.POST)
    @ResponseBody
    public List<OrderRecord> getOrderRecordByUserId(@RequestParam("userId") String userId) {
        return orderManageServiceBean.getOrderRecordListByUserId(Integer.parseInt(userId));
    }

    @RequestMapping(value = "/getOrderRecordByVenueId", method = RequestMethod.POST)
    @ResponseBody
    public List<OrderRecord> getOrderRecordByVenueId(@RequestParam("userId") String userId) {
        return orderManageServiceBean.getOrderRecordListByVenueId(Integer.parseInt(userId));
    }

    @RequestMapping(value = "/offLineBuyTicket", method = RequestMethod.POST)
    @ResponseBody
    public int offLineBuyTicket(
            @RequestParam("one") String one,
            @RequestParam("two") String two,
            @RequestParam("three") String three,
            @RequestParam("userAccount") String userAccount,
            @RequestParam("userPassword") String userPassword,
            @RequestParam("venueId") String venueId,
            @RequestParam("showId") String showId) {
        return orderManageServiceBean.offLineBuyTicket(Integer.parseInt(one), Integer.parseInt(two), Integer.parseInt(three), userAccount, userPassword, Integer.parseInt(venueId), Integer.parseInt(showId));
    }

}
