package dao.bean;

import dao.HibernateUtil;
import dao.TicketManagerDao;
import model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketManagerDaoBean extends BaseDaoBean implements TicketManagerDao {

    private static TicketManagerDaoBean ticketManagerDao = new TicketManagerDaoBean();

    public static TicketManagerDaoBean getInstance() {
        return ticketManagerDao;
    }

    public TicketManager find(String account, String password) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<TicketManager> ticketManagerList = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM TicketManager as T where T.account=:account and T.password=:password");
            query.setParameter("account", account);
            query.setParameter("password", password);
            ticketManagerList = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        System.out.println(ticketManagerList.toString());
        if (ticketManagerList.size() == 1) {
            return ticketManagerList.get(0);
        } else {
            TicketManager ticketManager = new TicketManager();
            ticketManager.setId(-1);
            return ticketManager;
        }
    }

    public List<TicketFinance> findTicketFinanceList() {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<TicketFinance> ticketFinanceList = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM TicketFinance");
            ticketFinanceList = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return ticketFinanceList;
    }

}
