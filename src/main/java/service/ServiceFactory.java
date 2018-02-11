package service;

import service.bean.UserManageServiceBean;

public class ServiceFactory {
    public static UserManageService getUserManageService(){
        return new UserManageServiceBean();
    }
}
