package repository;

import config.HibernateUtil;
import model.Regal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class RegalRepository {
    private SessionFactory factory = HibernateUtil.getSessionFactory();

    public void save(Regal regal) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(regal);
            session.getTransaction().commit();
        }
    }

    public Regal findById(Integer id) {
        try (Session session = factory.openSession()) {
            return session.get(Regal.class, id);
        }
    }

    public List<Regal> findAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Regal", Regal.class).list();
        }
    }

    public void update(Regal regal) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(regal);
            session.getTransaction().commit();
        }
    }

    public void delete(Regal regal) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(regal);
            session.getTransaction().commit();
        }
    }
}
