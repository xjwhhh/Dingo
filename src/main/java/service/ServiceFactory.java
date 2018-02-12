package service;

import service.bean.OrderManageServiceBean;
import service.bean.TicketManagerServiceBean;
import service.bean.UserManageServiceBean;
import service.bean.VenueManageServiceBean;

public class ServiceFactory {
    public static UserManageService getUserManageService(){
        return new UserManageServiceBean();
    }

    public static VenueManageService getVenueManageService(){
        return new VenueManageServiceBean();
    }

    public static TicketManageService getTicketManageService(){
        return new TicketManagerServiceBean();
    }

    public static OrderManageService getOrderManageService(){
        return new OrderManageServiceBean();
    }
}
