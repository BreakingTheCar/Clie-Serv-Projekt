package repository;

import config.HibernateUtil;
import model.Buch;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class BuchRepository {
    private SessionFactory factory = HibernateUtil.getSessionFactory();

    public void save(Buch buch) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(buch);
            session.getTransaction().commit();
        }
    }

    public Buch findById(Long isbn) {
        try (Session session = factory.openSession()) {
            return session.get(Buch.class, isbn);
        }
    }

    public List<Buch> findAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Buch", Buch.class).list();
        }
    }

    public List<Buch> findAvailableBooksByRegal(Integer regalId) {
        try (Session session = factory.openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT e.buch FROM Exemplar e " +
                                    "WHERE e.regal.id = :regalId " +
                                    "AND e.id NOT IN (" +
                                    "   SELECT a.exemplar.id FROM Ausleihe a WHERE a.rueckgabedatum IS NULL" +
                                    ")",
                            Buch.class
                    )
                    .setParameter("regalId", regalId)
                    .list();
        }
    }

    public void delete(Buch buch) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(buch);
            session.getTransaction().commit();
        }
    }
}
