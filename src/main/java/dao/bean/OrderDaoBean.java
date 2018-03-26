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

    public Order findOrderById(int orderId) {
        return (Order) super.load(Order.class, orderId);
    }

    public List<Order> findOrderByUserId(int userId) {
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

    public List<Order> findOrderByShowId(int showId) {
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

    public List<Order> findOrderByVenueId(int venueId) {
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

    public List<Order> findOrderByState(OrderState orderState) {
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

    public ResultMessage saveTicket(Ticket ticket) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery("insert into ticket(cost, seatId, orderId)  VALUE (?,?,?)");
            query.setParameter(0, ticket.getCost());
            query.setParameter(1, ticket.getSeatId());
            query.setParameter(2, ticket.getOrder().getId());
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return ResultMessage.SUCCESS;
    }

    public Ticket findTicketById(int ticketId) {
        return (Ticket) super.load(Ticket.class, ticketId);
    }

    public List<OrderRecord> findOrderRecordByUserId(int userId) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<OrderRecord> orderRecordList = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM OrderRecord as O where O.userId=:userId");
            query.setParameter("userId", userId);
            orderRecordList = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return orderRecordList;
    }
}
