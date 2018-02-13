package dao.bean;

import dao.HibernateUtil;
import dao.ShowDao;
import model.ResultMessage;
import model.Show;
import model.ShowType;
import model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShowDaoBean extends BaseDaoBean implements ShowDao {

    private static ShowDaoBean showDao=new ShowDaoBean();

    public static ShowDaoBean getInstance(){
        return showDao;
    }

    public ResultMessage save(Show show) {
        return super.save(show);
    }

    public Show findById(int showId) {
        return (Show)super.load(Show.class,showId);
    }

    public List<Show> findByType(ShowType showType) {
        Session session= HibernateUtil.getSession();
        Transaction tx = null;
        List<Show> showList=null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM show as S where S.showType=:showType");
            query.setParameter("showType",showType);
            showList =query.list();
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
}
