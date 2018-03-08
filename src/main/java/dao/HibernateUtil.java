package dao;

import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        try {
//            StandardServiceRegistry serviceRegistry=new StandardServiceRegistryBuilder().configure().build();
//
//           sessionFactory=new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();

//
            Configuration config;
            ServiceRegistry serviceRegistry;
            config = new Configuration().configure();
            config.addAnnotatedClass(User.class);
            config.addAnnotatedClass(Venue.class);
            config.addAnnotatedClass(Order.class);
            config.addAnnotatedClass(TicketManager.class);
            config.addAnnotatedClass(Seat.class);
            config.addAnnotatedClass(Show.class);
            config.addAnnotatedClass(ShowSeat.class);
            config.addAnnotatedClass(VenueApplication.class);
            config.addAnnotatedClass(Ticket.class);
            config.addAnnotatedClass(OrderRecord.class);
            config.addAnnotatedClass(ShowEarning.class);
            config.addAnnotatedClass(VenueFinance.class);
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
            sessionFactory = config.buildSessionFactory(serviceRegistry);
            return sessionFactory;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Session getSession() {
        return getSessionFactory().getCurrentSession();
    }
}
