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

    public List<Show> findByState(String progressType) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<Show> showList = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Show as S where S.progressType=:progressType");
            query.setParameter("progressType", progressType);
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
            Query query = session.createSQLQuery("insert into showseat(cost, description, level,seatId, showId, booked)  VALUE (?,?,?,?,?,?)");
            query.setParameter(0, showSeat.getCost());
            query.setParameter(1, showSeat.getDescription());
            query.setParameter(2, showSeat.getLevel());
            query.setParameter(3, showSeat.getSeatId());
            query.setParameter(4, showSeat.getShow().getId());
            query.setParameter(5, showSeat.isBooked());
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
        System.out.println(showSeatId);
        return (ShowSeat) super.load(ShowSeat.class, showSeatId);
    }

//    public List<ShowSeat> findShowSeatListByShowId(int showId) {
//        Session session = HibernateUtil.getSession();
//        Transaction tx = null;
//        List<ShowSeat> showSeatList = null;
//        try {
//            tx = session.beginTransaction();
//            Query query = session.createQuery("FROM ShowSeat as S where S.showId=:showId");
//            query.setParameter("showId", showId);
//            showSeatList = query.list();
//            tx.commit();
//        } catch (HibernateException e) {
//            if (tx != null) tx.rollback();
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//        return showSeatList;
//    }

//    public List<ShowSeat> findUnbookedShowSeatListByShowId(int showId) {
//        Session session = HibernateUtil.getSession();
//        Transaction tx = null;
//        List<ShowSeat> showSeatList = null;
//        try {
//            tx = session.beginTransaction();
//            Query query = session.createQuery("FROM ShowSeat as S where S.show=:showId and S.booked=:booked");
//            query.setParameter("showId", showId);
//            query.setParameter("booked", false);
//            showSeatList = query.list();
//            tx.commit();
//        } catch (HibernateException e) {
//            if (tx != null) tx.rollback();
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//        return showSeatList;
//    }

    public List<ShowEarning> findUnSettledShowEarning() {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<ShowEarning> showEarningList = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM ShowEarning as S where S.settled=:settled");
            query.setParameter("settled", false);
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

    public ShowEarning findShowEarningByShowId(int showId) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<ShowEarning> showEarningList = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM ShowEarning as S where S.showId=:showId");
            query.setParameter("showId", showId);
            showEarningList = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        if (showEarningList.size() > 0) {
            return showEarningList.get(0);
        } else {
            ShowEarning showEarning = new ShowEarning();
            showEarning.setId(-1);
            return showEarning;
        }
    }
}
