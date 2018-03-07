package dao.bean;

import dao.HibernateUtil;
import dao.OrderDao;
import model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoBean extends BaseDaoBean implements OrderDao {

    private static OrderDaoBean orderDao = new OrderDaoBean();

    public static OrderDaoBean getInstance() {
        return orderDao;
    }

    public ResultMessage save(Order order) {
        return super.save(order);
    }

    public Order getOrderById(int orderId) {
        return (Order) super.load(Order.class, orderId);
    }

    public List<Order> getOrderByUserId(int userId) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<Order> orderList = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Order as O where O.userId=:userId");
            query.setParameter("userId", userId);
            orderList = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return orderList;
    }

    public List<Order> getOrderByShowId(int showId) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<Order> orderList = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Order as O where O.showId=:showId");
            query.setParameter("showId", showId);
            orderList = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return orderList;
    }

    public List<Order> getOrderByVenueId(int venueId) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<Order> orderList = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Order as O where O.venueId=:venueId");
            query.setParameter("venueId", venueId);
            orderList = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return orderList;
    }

    public List<Order> getOrderByState(OrderState orderState) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<Order> orderList = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Order as O where O.orderState=:orderState");
            query.setParameter("orderState", orderState);
            orderList = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return orderList;
    }

    public ResultMessage saveOrderRecord(OrderRecord orderRecord) {
        return super.save(orderRecord);
    }
}
