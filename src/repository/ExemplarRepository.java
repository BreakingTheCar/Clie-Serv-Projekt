package repository;

import config.HibernateUtil;
import model.Exemplar;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class ExemplarRepository {
    private SessionFactory factory = HibernateUtil.getSessionFactory();

    public void save(Exemplar exemplar) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(exemplar);
            session.getTransaction().commit();
        }
    }

    public Exemplar findById(Integer id) {
        try (Session session = factory.openSession()) {
            return session.get(Exemplar.class, id);
        }
    }

    public List<Exemplar> findAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Exemplar", Exemplar.class).list();
        }
    }

    public List<Exemplar> findByRegalId(Integer regalId) {
        try (Session session = factory.openSession()) {
            return session.createQuery(
                            "FROM Exemplar WHERE regal.id = :regalId",
                            Exemplar.class
                    )
                    .setParameter("regalId", regalId)
                    .list();
        }
    }

    public List<Exemplar> findAvailable() {
        try (Session session = factory.openSession()) {
            return session.createQuery(
                    "FROM Exemplar e WHERE e.id NOT IN " +
                            "(SELECT a.exemplar.id FROM Ausleihe a WHERE a.rueckgabedatum IS NULL)",
                    Exemplar.class
            ).list();
        }
    }

    public void update(Exemplar exemplar) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(exemplar);
            session.getTransaction().commit();
        }
    }

    public void delete(Exemplar exemplar) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(session.contains(exemplar) ? exemplar : session.merge(exemplar));;
            session.getTransaction().commit();
        }
    }
}
