package Mapping;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;


public class HypernateDemo {

    // SessionFactory einmal erstellen – ist teuer, wird wiederverwendet
    private static SessionFactory sessionFactory =
            new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    public static void main(String[] args) {

        System.out.println("=== Hibernate Demo – Bibliothek ===\n");


        // 1) READ – alle Bücher laden

        System.out.println("--- 1) Alle Bücher (READ) ---");
        allesBuecher();


        // 2) READ – alle Mitglieder

        System.out.println("\n--- 2) Alle Mitglieder (READ) ---");
        alleMitglieder();


        // 3) READ – aktive Ausleihen

        System.out.println("\n--- 3) Aktive Ausleihen (READ mit JOIN) ---");
        aktiveAusleihen();


        // 4) CREATE – neues Mitglied anlegen

        System.out.println("\n--- 4) Neues Mitglied anlegen (CREATE) ---");
        Integer neueID = neuesMitglied("Hibernate User", "hibernate@hwr-berlin.de");


        // 5) UPDATE – Mitglied ändern

        System.out.println("\n--- 5) Mitglied ändern (UPDATE) ---");
        mitgliedAendern(neueID, "Hibernate User (geändert)");


        // 6) DELETE – Mitglied löschen

        System.out.println("\n--- 6) Mitglied löschen (DELETE) ---");
      //  mitgliedLoeschen(neueID);


        // 7) HQL – Hibernate Query Language
        //    Wie SQL, aber auf Java-Klassen statt Tabellen

        System.out.println("\n--- 7) HQL – Bücher nach Kategorie ---");
        buecherNachKategorie("Programmierung");

        sessionFactory.close();
        System.out.println("\nFertig.");
    }

    // ── 1) Alle Bücher laden
    static void allesBuecher() {
        try (Session session = sessionFactory.openSession()) {
            List<Buch> buecher = session.createQuery(
                    "FROM Buch", Buch.class).list();
            buecher.forEach(b -> System.out.println("  " + b));
        }
    }

    // ── 2) Alle Mitglieder laden
    static void alleMitglieder() {
        try (Session session = sessionFactory.openSession()) {
            List<Mitglied> mitglieder = session.createQuery(
                    "FROM Mitglied", Mitglied.class).list();
            mitglieder.forEach(m -> System.out.println("  " + m));
        }
    }

    // ── 3) Aktive Ausleihen
    static void aktiveAusleihen() {
        try (Session session = sessionFactory.openSession()) {
            // HQL: Java-Klassen und -Felder statt Tabellen und Spalten
            List<Ausleihe> ausleihen = session.createQuery(
                    "FROM Ausleihe a WHERE a.rueckgabedatum IS NULL",
                    Ausleihe.class).list();
            ausleihen.forEach(a -> System.out.println("  " + a));
        }
    }

    // ── 4) Neues Mitglied anlegen
    static Integer neuesMitglied(String name, String email) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Mitglied m = new Mitglied(name, email);
            Integer id = (Integer) session.save(m);  // Hibernate generiert ID

            session.getTransaction().commit();
            System.out.println("  Angelegt: " + m + " → ID: " + id);
            return id;
        }
    }

    // ── 5) Mitglied ändern
    static void mitgliedAendern(Integer id, String neuerName) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            // get() lädt das Objekt direkt per Primärschlüssel
            Mitglied m = session.get(Mitglied.class, id);
            if (m != null) {
                m.setName(neuerName);
                session.merge(m);  // Änderung speichern
                System.out.println("  Geändert: " + m);
            }

            session.getTransaction().commit();
        }
    }

    // ── 6) Mitglied löschen
    static void mitgliedLoeschen(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Mitglied m = session.get(Mitglied.class, id);
            if (m != null) {
                session.remove(m);
                System.out.println("  Gelöscht: " + m);
            }

            session.getTransaction().commit();
        }
    }

    // ── 7) HQL-Abfrage mit Parameter
    static void buecherNachKategorie(String kategorie) {
        try (Session session = sessionFactory.openSession()) {
            // HQL nutzt Java-Feldnamen (kategorie), nicht SQL-Spaltennamen
            List<Buch> buecher = session.createQuery(
                            "FROM Buch b WHERE b.kategorie = :kat", Buch.class)
                    .setParameter("kat", kategorie)
                    .list();
            buecher.forEach(b -> System.out.println("  " + b));
        }
    }
}