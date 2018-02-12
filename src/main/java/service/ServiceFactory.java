package service;

import service.bean.*;

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

    public static ShowManageService getShowManageService(){return new ShowManageServiceBean();}
}
