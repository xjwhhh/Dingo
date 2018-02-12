package controller;

import model.Order;
import model.OrderState;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.ServiceFactory;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("order")
public class OrderController {


    @RequestMapping(value = "/getOrderByState", method = RequestMethod.POST)
    @ResponseBody
    public List<Order> getOrderByType(
            @RequestParam("orderState")String orderStateString) {
        OrderState orderState=OrderState.valueOf(orderStateString);
        return ServiceFactory.getOrderManageService().getOrderListByState(orderState);
    }

    @RequestMapping(value = "/getOrderById", method = RequestMethod.POST)
    @ResponseBody public Order getOrderById(
            @RequestParam("orderId")String orderId) {
        return ServiceFactory.getOrderManageService().getOrderByOrderId(Integer.parseInt(orderId));
    }

    @RequestMapping(value = "/getOrderByShowId",method = RequestMethod.POST)
    @ResponseBody public List<Order> getOrderByShowId(@RequestParam("showId")String showId){
        return ServiceFactory.getOrderManageService().getOrderListByShowId(Integer.parseInt(showId));
    }

    @RequestMapping(value = "/getOrderByVenueId",method = RequestMethod.POST)
    @ResponseBody public List<Order> getOrderByVenueId(@RequestParam("venueId")String venueId){
        return ServiceFactory.getOrderManageService().getOrderListByVenueId(Integer.parseInt(venueId));
    }

    @RequestMapping(value = "/getOrderByUserId",method = RequestMethod.POST)
    @ResponseBody public List<Order> getOrderByUserId(@RequestParam("userId")String userId){
        return ServiceFactory.getOrderManageService().getOrderListByUserId(Integer.parseInt(userId));
    }

}
