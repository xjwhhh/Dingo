package dao.bean;

import dao.HibernateUtil;
import dao.TicketManagerDao;
import model.ResultMessage;
import model.ShowEarning;
import model.TicketManager;
import model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketManagerDaoBean extends BaseDaoBean  implements TicketManagerDao {

    private static TicketManagerDaoBean ticketManagerDao = new TicketManagerDaoBean();

    public static TicketManagerDaoBean getInstance() {
        return ticketManagerDao;
    }

    public TicketManager find(String account, String password) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<TicketManager> ticketManagerList = null;
        try {
            System.out.println(account);
            System.out.println(account.length());
            System.out.println(password);
            System.out.println(password.length());
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

    public List<ShowEarning> findUnSettledShowEarning() {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<ShowEarning> showEarningList = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM ShowEarning as S where S.isSettled=:isSettled");
            query.setParameter("isSettled", false);
            showEarningList = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
       return showEarningList;
    }

    public ShowEarning findShowEarningById(int showEarningId) {
        return (ShowEarning) super.load(ShowEarning.class, showEarningId);
    }

    public ResultMessage updateShowEarning(ShowEarning showEarning) {
        return super.update(showEarning);
    }
}
