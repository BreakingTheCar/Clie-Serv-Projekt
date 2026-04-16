package repository;

import config.HibernateUtil;
import model.Ausleihe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class AusleiheRepository {
    private SessionFactory factory = HibernateUtil.getSessionFactory();

    public void save(Ausleihe ausleihe) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(ausleihe);
            session.getTransaction().commit();
        }
    }

    public Ausleihe findById(Integer id) {
        try (Session session = factory.openSession()) {
            return session.get(Ausleihe.class, id);
        }
    }

    public List<Ausleihe> findAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Ausleihe", Ausleihe.class).list();
        }
    }

    public List<Ausleihe> findActive() {
        try (Session session = factory.openSession()) {
            return session.createQuery(
                    "FROM Ausleihe WHERE rueckgabedatum IS NULL",
                    Ausleihe.class
            ).list();
        }
    }

    public void update(Ausleihe ausleihe) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(ausleihe);
            session.getTransaction().commit();
        }
    }
}
