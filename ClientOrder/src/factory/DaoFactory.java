package factory;

import dao.OrderDao;
import dao.impl.OrderDaoImpl;

public class DaoFactory {

	public static OrderDao getOrderDao()
	{
		return OrderDaoImpl.getInstance();
	}
	

}
