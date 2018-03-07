package model;

import dao.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

public class test {

    @Autowired
    static SessionFactory sessionFactory;

    public static void main(String[] args) {
        Session session = HibernateUtil.getSession();
//        Session session=sessionFactory.getCurrentSession();
        User user = new User();
        user.setAccount("1");
        user.setPassword("1");
        user.setLevel(VIPLevel.COMMON);
        Transaction tx = session.beginTransaction();
        session.save(user);
        tx.commit();
    }
}
