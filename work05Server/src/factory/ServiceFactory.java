package factory;

import service.OrderManageService;
import service.UserManageService;
import service.impl.OrderManageServiceImpl;
import service.impl.UserManageServiceImpl;

public class ServiceFactory {

    public static UserManageService getUserManageService() {
        return UserManageServiceImpl.getInstance();
    }

    public static OrderManageService getOrderManageService() {
        return OrderManageServiceImpl.getInstance();
    }

}
