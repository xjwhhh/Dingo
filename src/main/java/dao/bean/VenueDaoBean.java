package dao.bean;

import dao.HibernateUtil;
import dao.VenueDao;
import model.*;
import net.sf.json.JSONArray;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VenueDaoBean extends BaseDaoBean implements VenueDao {

    private static VenueDaoBean venueDao = new VenueDaoBean();

    public static VenueDaoBean getInstance() {
        return venueDao;
    }

    public ResultMessage save(Venue venue) {
        //自动分配七位编码
        boolean isRedundant=true;
        int code=0;
        String codeString="";
        while(isRedundant) {
            code = (int) (Math.random() * 9999999);
            codeString=String.valueOf(code);
            while(codeString.length()<7){
                codeString="0"+codeString;
            }
            System.out.println(codeString);

            Session session = HibernateUtil.getSession();
            Transaction tx = null;
            List<Venue> venueList = null;
            try {
                tx = session.beginTransaction();
                Query query = session.createQuery("FROM Venue as V where V.code=:code");
                query.setParameter("code",codeString );
                venueList = query.list();
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
            System.out.println(venueList.size());
            if(venueList.size()==0){
                isRedundant=false;
            }else{
                isRedundant=true;
            }
        }
        venue.setCode(codeString);
        return super.save(venue);
    }

    public Venue find(String code, String password) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<Venue> venueList = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Venue as V where V.code=:code and V.password=:password");
            query.setParameter("code", code);
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
            Query query = session.createQuery("FROM VenueApplication as V where V.venueApplicationType=:type and V.approved=:approved");
            query.setParameter("type", venueApplicationType);
            query.setParameter("approved", false);
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

    public List<Venue> findVenueList() {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<Venue> venueList = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Venue V");
            venueList = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return venueList;
    }

    public List<Seat> findSeatListByVenueId(int venueId) {
        System.out.println("111");
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<Seat> seatList = new ArrayList<Seat>();
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery("select S.id,S.description,S.level FROM seat as S where venueId=?");
            query.setParameter(0, venueId);
            List object = query.list();
            tx.commit();
            for(Object obj : object){
                Object[] arrObj = (Object[])obj;
                Seat seat=new Seat();
                seat.setId(Integer.parseInt(arrObj[0].toString()));
                seat.setDescription(arrObj[1].toString());
                seat.setLevel(arrObj[2].toString());
                seatList.add(seat);
                System.out.println("22");
            }
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
//        System.out.println(objectList.toString());
        System.out.println(JSONArray.fromObject(seatList));
       return seatList;
    }

    public ResultMessage saveSeat(Seat seat) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery("insert into seat(description, level, venueId) VALUE (?,?,?)");
            query.setParameter(0, seat.getDescription());
            query.setParameter(1, seat.getLevel());
            query.setParameter(2, seat.getVenue().getId());
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

    public static void main(String[] args){
        Seat seat=new Seat();
        seat.setLevel("2");
        seat.setDescription("e");
        Venue venue=venueDao.findById(1);
        seat.setVenue(venue);
       venueDao.saveSeat(seat);
    }
}
