package dao.bean;

import dao.HibernateUtil;
import dao.ShowDao;
import model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShowDaoBean extends BaseDaoBean implements ShowDao {

    private static ShowDaoBean showDao = new ShowDaoBean();

    public static ShowDaoBean getInstance() {
        return showDao;
    }

    public ResultMessage save(Show show) {
        return super.save(show);
    }

    public Show findById(int showId) {
        return (Show) super.load(Show.class, showId);
    }

    public List<Show> findByType(String showType) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<Show> showList = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Show as S where S.showType=:showType");
            query.setParameter("showType", showType);
            showList = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return showList;
    }

    public ResultMessage update(Show show) {
        return super.update(show);
    }

    public List<Show> findByVenueId(int venueId) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<Show> showList = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Show as S where S.venueId=:venueId");
            query.setParameter("venueId", venueId);
            showList = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return showList;
    }

    public List<Show> findByUserId(int userId) {
        return null;
    }

    public ResultMessage saveShowSeat(ShowSeat showSeat) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery("insert into showseat(cost, description, level,seatId, showId)  VALUE (?,?,?,?,?)");
            query.setParameter(0, showSeat.getCost());
            query.setParameter(1, showSeat.getDescription());
            query.setParameter(2, showSeat.getLevel());
            query.setParameter(3, showSeat.getSeatId());
            query.setParameter(4, showSeat.getShow().getId());
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

    public ShowSeat findShowSeat(int showSeatId) {
        return (ShowSeat) super.load(Show.class, showSeatId);
    }

    public List<ShowSeat> findShowSeatListByShowId(int showId) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<ShowSeat> showSeatList = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM ShowSeat as S where S.showId=:showId");
            query.setParameter("showId", showId);
            showSeatList = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return showSeatList;
    }
}
