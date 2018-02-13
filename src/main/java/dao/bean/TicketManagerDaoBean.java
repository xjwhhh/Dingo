package dao.bean;

import dao.HibernateUtil;
import dao.TicketManagerDao;
import model.TicketManager;
import model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketManagerDaoBean implements TicketManagerDao {

    private static TicketManagerDaoBean ticketManagerDao=new TicketManagerDaoBean();

    public static TicketManagerDaoBean getInstance(){
        return ticketManagerDao;
    }

    public TicketManager find(String account, String password) {
        Session session= HibernateUtil.getSession();
        Transaction tx = null;
        List<TicketManager> ticketManagerList=null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM ticketManager as T where T.account=:account and T.password=:password");
            query.setParameter("account",account);
            query.setParameter("password",password);
            ticketManagerList =query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        if(ticketManagerList.size()==1){
            return ticketManagerList.get(0);
        }else{
            TicketManager ticketManager=new TicketManager();
            ticketManager.setId(-1);
            return ticketManager;
        }
    }
}
