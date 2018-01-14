package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

public class BaseDaoImpl implements BaseDao {
	
	public BaseDaoImpl() {
	}

	public void flush() {
		HibernateUtil.getSession().flush();
	}

	public void clear() {
		HibernateUtil.getSession().clear();
	}

	public void save(Object bean) {
		try {
			System.out.println("ready to getsession");	
			Session session =HibernateUtil.getSession() ;
			Transaction tx=session.beginTransaction();
			session.merge(bean);
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	@SuppressWarnings("rawtypes")
	public Object load(Class c, String id) {
		try {
			Session session = HibernateUtil.getSession();
			Transaction tx=session.beginTransaction();
			Object o=session.get(c, id);
			tx.commit();
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}


	public List getAllList(Class c) {
		return null;
		//

	}


	public Long getTotalCount(Class c) {
		//
		return null;
	}



	public void update(Object bean) {
		//

	}

	public void delete(Object bean) {

		try {
		//	System.out.println("ready to getsession");	
			Session session =HibernateUtil.getSession() ;
			Transaction tx=session.beginTransaction();
			session.delete(bean);;
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	@SuppressWarnings({ "rawtypes" })
	public void delete(Class c, String id) {
		//
	}

	@SuppressWarnings({ "rawtypes" })
	public void delete(Class c, String[] ids) {
		//
	}
}
