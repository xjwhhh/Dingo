package controller;

import model.Order;
import model.OrderState;
import model.ResultMessage;
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
    public ResultMessage saveOrderChoose(
            @RequestParam("orderJson")String orderJson) {
        return orderManageServiceBean.reserveChoose(orderJson);
    }

    @RequestMapping(value = "/saveOrderNoChoose", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage saveOrderNoChoose(
            @RequestParam("orderJson")String orderJson) {
        return orderManageServiceBean.reserveNoChoose(orderJson);
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage payOrder(
            @RequestParam("orderId")String orderId) {
        return orderManageServiceBean.pay(Integer.parseInt(orderId));
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage cancelOrder(
            @RequestParam("orderId")String orderId) {
        return orderManageServiceBean.cancel(Integer.parseInt(orderId));
    }


    @RequestMapping(value = "/getOrderByState", method = RequestMethod.POST)
    @ResponseBody
    public List<Order> getOrderByType(
            @RequestParam("orderState")String orderStateString) {
        OrderState orderState=OrderState.valueOf(orderStateString);
        return orderManageServiceBean.getOrderListByState(orderState);
    }

    @RequestMapping(value = "/getOrderById", method = RequestMethod.POST)
    @ResponseBody public Order getOrderById(
            @RequestParam("orderId")String orderId) {
        return orderManageServiceBean.getOrderByOrderId(Integer.parseInt(orderId));
    }

    @RequestMapping(value = "/getOrderByShowId",method = RequestMethod.POST)
    @ResponseBody public List<Order> getOrderByShowId(@RequestParam("showId")String showId){
        return orderManageServiceBean.getOrderListByShowId(Integer.parseInt(showId));
    }

    @RequestMapping(value = "/getOrderByVenueId",method = RequestMethod.POST)
    @ResponseBody public List<Order> getOrderByVenueId(@RequestParam("venueId")String venueId){
        return orderManageServiceBean.getOrderListByVenueId(Integer.parseInt(venueId));
    }

    @RequestMapping(value = "/getOrderByUserId",method = RequestMethod.POST)
    @ResponseBody public List<Order> getOrderByUserId(@RequestParam("userId")String userId){
        return orderManageServiceBean.getOrderListByUserId(Integer.parseInt(userId));
    }

}
