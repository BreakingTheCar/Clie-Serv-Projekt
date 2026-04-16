package repository;

import config.HibernateUtil;
import model.Mitglied;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class MitgliedRepository {
    private SessionFactory factory = HibernateUtil.getSessionFactory();

    public void save(Mitglied mitglied) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(mitglied);
            session.getTransaction().commit();
        }
    }

    public Mitglied findById(Integer id) {
        try (Session session = factory.openSession()) {
            return session.get(Mitglied.class, id);
        }
    }

    public List<Mitglied> findAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Mitglied", Mitglied.class).list();
        }
    }

    public Mitglied findByName (String name) {
        try (Session session = factory.openSession()) {
            return session.createQuery(
                            "FROM Mitglied WHERE name = :name",
                            Mitglied.class
                    )
                    .setParameter("name", name)
                    .uniqueResult();
        }
    }

    public Mitglied findByEmail (String email) {
        try (Session session = factory.openSession()) {
            return session.createQuery(
                            "FROM Mitglied WHERE email = :email",
                            Mitglied.class
                    )
                    .setParameter("email", email)
                    .uniqueResult();
        }
    }

    public void update(Mitglied mitglied) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(mitglied);
            session.getTransaction().commit();
        }
    }

    public List<Mitglied> findAllWithLoans() {
        try (Session session = factory.openSession()) {
            return session.createQuery(
                    "SELECT DISTINCT m FROM Mitglied m JOIN m.ausleihen a WHERE a.rueckgabedatum IS NULL",
                    Mitglied.class
            ).list();
        }
    }
}
