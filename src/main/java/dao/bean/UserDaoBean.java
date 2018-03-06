package dao.bean;

import dao.HibernateUtil;
import dao.UserDao;
import model.ResultMessage;
import model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public class UserDaoBean extends BaseDaoBean implements UserDao {

    private static UserDaoBean userDao=new UserDaoBean();


    public static UserDaoBean getInstance(){
        return userDao;
    }

    public ResultMessage save(User user) {
        return super.save(user);
    }

    public User find(String account, String password) {
        Session session= HibernateUtil.getSession();
        Transaction tx = null;
        List<User> userList=null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM User as U where U.account=:account and U.password=:password");
            query.setParameter("account",account);
            query.setParameter("password",password);
            userList =query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        if(userList.size()==1){
            return userList.get(0);
        }else{
            User user=new User();
            user.setId(-1);
            return user;
        }
    }

    public User findById(int userId) {
        return (User)super.load(User.class,userId);
    }

    public User findByEmail(String emailAddress) {
        Session session= HibernateUtil.getSession();
        Transaction tx = null;
        List<User> userList=null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM User as U where U.emailAddress=:emailAddress");
            query.setParameter("emailAddress",emailAddress);
            userList =query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        if(userList.size()==1){
            return userList.get(0);
        }else{
            User user=new User();
            user.setId(-1);
            return user;
        }
    }

    public ResultMessage update(User user) {
        return super.update(user);
    }
}
