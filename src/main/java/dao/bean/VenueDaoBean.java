package dao.bean;

import dao.HibernateUtil;
import dao.VenueDao;
import model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VenueDaoBean extends BaseDaoBean implements VenueDao {

    private static VenueDaoBean venueDao = new VenueDaoBean();

    public static VenueDaoBean getInstance() {
        return venueDao;
    }

    public ResultMessage save(Venue venue) {
        return super.save(venue);
    }

    public Venue find(String account, String password) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<Venue> venueList = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Venue as V where V.account=:account and V.password=:password");
            query.setParameter("account", account);
            query.setParameter("password", password);
            venueList = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        if (venueList.size() == 1) {
            return venueList.get(0);
        } else {
            Venue venue = new Venue();
            venue.setId(-1);
            return venue;
        }
    }

    public Venue findById(int venueId) {
        return (Venue) super.load(Venue.class, venueId);
    }

    public ResultMessage update(Venue venue) {
        return super.update(venue);
    }

    public ResultMessage saveVenueApplication(VenueApplication venueApplication) {
        return super.save(venueApplication);
    }

    public VenueApplication findVenueApplicationById(int venueApplicationId) {
        return (VenueApplication) super.load(VenueApplication.class, venueApplicationId);
    }

    public List<VenueApplication> findVenueApplicationByType(VenueApplicationType venueApplicationType) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<VenueApplication> venueApplicationList = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM VenueApplication as V where V.venueApplicationType=:type");
            query.setParameter("type", venueApplicationType);
            venueApplicationList = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return venueApplicationList;
    }
}
