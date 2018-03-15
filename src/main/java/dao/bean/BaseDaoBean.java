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
        Session session = HibernateUtil.getSession();
        try {
            Transaction tx = session.beginTransaction();
            session.merge(bean);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return ResultMessage.SUCCESS;
    }

    public Object load(Class c, int id) {
        Session session = HibernateUtil.getSession();
        try {
            Transaction tx = session.beginTransaction();
            Object o = session.get(c, id);
            tx.commit();
            return o;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public ResultMessage update(Object bean) {
        Session session = HibernateUtil.getSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.update(bean);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return ResultMessage.SUCCESS;
    }

    public ResultMessage delete(Object bean) {
        Session session = HibernateUtil.getSession();
        try {
            Transaction tx = session.beginTransaction();
            session.delete(bean);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return ResultMessage.SUCCESS;
    }
}
