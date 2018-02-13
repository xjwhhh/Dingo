package dao.bean;

import dao.BaseDao;
import dao.HibernateUtil;
import model.ResultMessage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDaoBean implements BaseDao {
    public ResultMessage save(Object bean) {
        try {
            Session session = HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            session.merge(bean);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultMessage.SUCCESS;
    }

    public Object load(Class c, int id) {
        try {
            Session session = HibernateUtil.getSession();
            Transaction tx=session.beginTransaction();
            Object o=session.get(c, id);
            tx.commit();
            return o;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultMessage update(Object bean) {
        try {
            Session session = HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            session.update(bean);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultMessage.SUCCESS;
    }

    public ResultMessage delete(Object bean) {
        try {
            Session session =HibernateUtil.getSession() ;
            Transaction tx=session.beginTransaction();
            session.delete(bean);;
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultMessage.SUCCESS;
    }
}
