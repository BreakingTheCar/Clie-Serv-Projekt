import model.*;
import repository.*;
import service.*;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        testMitgliedRepository();
        testRegalRepository();
        testBuchRepository();
        testExemplarRepository();
        testAusleiheRepository();
        testAusleiheService();
        testBuchService();
        testExemplarService();
        testMitgliedService();
        testRegalService();
    }

    public static void testMitgliedRepository() {
        MitgliedRepository repo = new MitgliedRepository();
        System.out.println("\n===== MitgliedRepository =====");

        // Speichern
        Mitglied m = new Mitglied("Test User", "1234@test.de");
        repo.save(m);
        Mitglied saved = repo.findByEmail("1234@test.de");
        if(saved != null)
            System.out.println("save() erfolgreich");
        else
            System.out.println("save() nicht erfolgreich");

        // findByName
        Mitglied byName = repo.findByName("Test User");
        if(byName != null)
            System.out.println("findByName() erfolgreich");

        // findByEmail
        Mitglied byEmail = repo.findByEmail("1234@test.de");
        if(byEmail != null)
            System.out.println("findByEmail() erfolgreich");

        // Update by email & find by email
        byEmail.setName("Updated User");
        repo.update(byEmail);
        Mitglied updated = repo.findByEmail("1234@test.de");
        if(updated.getName().equals("Updated User"))
            System.out.println("update() erfolgreich");

        // findAll
        System.out.println("Mitglieder:");
        repo.findAll().forEach(System.out::println);

        // findAllWithLoans
        System.out.println("Mitglieder mit offenen Ausleihen:");
        repo.findAllWithLoans().forEach(System.out::println);
    }

    public static void testRegalRepository() {
        RegalRepository repo = new RegalRepository();
        System.out.println("\n===== RegalRepository =====");
        Regal regal = new Regal("Test-Regal");
        repo.save(regal);

        Regal saved = repo.findAll()
                .stream()
                .filter(r -> r.getStandort().equals("Test-Regal"))
                .findFirst()
                .orElse(null);
        if(saved != null)
            System.out.println("save() erfolgreich");

        saved.setStandort("Updated-Regal");
        repo.update(saved);

        Regal updated = repo.findById(saved.getRegalID());
        if(updated.getStandort().equals("Updated-Regal"))
            System.out.println("update() erfolgreich");

        System.out.println("Alle Regale:");
        repo.findAll().forEach(System.out::println);

        repo.delete(updated);
        if(repo.findById(updated.getRegalID()) == null)
            System.out.println("delete() erfolgreich");
    }

    public static void testBuchRepository() {
        BuchRepository repo = new BuchRepository();
        System.out.println("\n===== BuchRepository =====");

        // findAll
        List<Buch> books = repo.findAll();
        if (!books.isEmpty()) {
            System.out.println("✓ " + books.size() + " Bücher gefunden.");
            books.forEach(System.out::println);
        } else {
            System.out.println("Keine Bücher gefunden.");
        }

        //findById
        if (!books.isEmpty()) {

            Long isbn = books.get(0).getIsbn();

            Buch book = repo.findById(isbn);

            if (book != null)
                System.out.println("Buch gefunden: " + book);
            else
                System.out.println("Buch nicht gefunden.");
        }

        //speichern
        Buch testBook = new Buch(
                9999999999999L,
                "Repository Test",
                "JUnit",
                "Testing"
        );

        repo.save(testBook);
        Buch saved = repo.findById(9999999999999L);
        if (saved != null)
            System.out.println("Buch gespeichert");
        else
            System.out.println("Buch nicht gespeichert");

        //löschen
        repo.delete(saved);
        if (repo.findById(9999999999999L) == null)
            System.out.println("Erfolgreich gelöscht.");
        else
            System.out.println("Nicht erfolgreich gelöscht.");

        //findAvailableBooksByRegal
        List<Buch> available = repo.findAvailableBooksByRegal(1);
        System.out.println("Verfügbare Bücher in Regal 1:");
        available.forEach(System.out::println);
    }
    public static void testExemplarRepository() {
        BuchRepository buchRepo = new BuchRepository();
        RegalRepository regalRepo = new RegalRepository();
        ExemplarRepository repo = new ExemplarRepository();
        System.out.println("\n===== ExemplarRepository Tests =====");
        //findAll
        List<Exemplar> exemplare = repo.findAll();
        exemplare.forEach(System.out::println);

        //findById
        if (!exemplare.isEmpty()) {

            Exemplar e = repo.findById(
                    exemplare.get(0).getExemplarID()
            );

            if (e != null)
                System.out.println("gefunden: " + e);
        }

       //findAvailable
        List<Exemplar> available = repo.findAvailable();
        System.out.println("Verfügbare Exemplare: "
                + available.size());

        available.forEach(System.out::println);

        //findByRegalId
        List<Exemplar> regal = repo.findByRegalId(1);
        regal.forEach(System.out::println);

        //speichern
        Buch buch = buchRepo.findAll().get(0);
        Regal regalBsp = regalRepo.findAll().get(0);
        Exemplar exemplar = new Exemplar(buch, regalBsp);
        repo.save(exemplar);
        System.out.println("Exemplar gespeichert");
    }

    public static void testAusleiheRepository() {
        AusleiheRepository repo = new AusleiheRepository();
        MitgliedRepository mitgliedRepo = new MitgliedRepository();
        ExemplarRepository exemplarRepo = new ExemplarRepository();
        System.out.println("\n===== AusleiheRepository Tests =====");

        //findAll
        List<Ausleihe> ausleihen = repo.findAll();
        ausleihen.forEach(System.out::println);

        //findActive
        List<Ausleihe> active = repo.findActive();
        System.out.println("Zurzeit ausgeliehen:");
        active.forEach(System.out::println);

        //findById
        if (!ausleihen.isEmpty()) {
            Ausleihe a = repo.findById(
                    ausleihen.get(0).getAusleiheID()
            );

            if (a != null)
                System.out.println("Gefunden: " + a);
        }

        //speichern
        Mitglied mitglied = mitgliedRepo.findAll().get(0);
        Exemplar exemplar = exemplarRepo.findAvailable().get(0);

        Ausleihe ausleihe =
                new Ausleihe(exemplar, mitglied, LocalDate.now());

        repo.save(ausleihe);
        System.out.println("Ausleihe gespeichert");
    }

    public static void testAusleiheService() {
        AusleiheService ausleiheService = new AusleiheService();
        MitgliedRepository mitgliedRepo = new MitgliedRepository();
        ExemplarRepository exemplarRepo = new ExemplarRepository();

        System.out.println("\n===== AusleiheService Tests =====");
        //Buch ausleihen
        Exemplar exemplar = exemplarRepo.findAvailable().get(0);
        Mitglied mitglied = mitgliedRepo.findAll().get(0);
        ausleiheService.borrowBook(exemplar, mitglied);
        System.out.println("Ausleihe erfolgreich");

        //zwei mal dasselbe Exemplar ausleihen
        try{
            ausleiheService.borrowBook(exemplar, mitglied);
            System.out.println("Selbes Exemplar nochmal ausgeliehen");
        } catch (RuntimeException e) {
            System.out.println("Doppelte Ausleihe nicht erlaubt");
        }

        //Buch zurück geben
        Ausleihe loan =
                ausleiheService.getActiveLoans().stream()
                        .filter(a ->
                                a.getExemplar()
                                        .getExemplarID()
                                        .equals(exemplar.getExemplarID()))
                        .findFirst()
                        .orElse(null);

        ausleiheService.returnBook(
                loan.getAusleiheID()
        );
        System.out.println("Buch erfolgreich zurückgegeben");

    }

    public static void testBuchService() {
        BuchService service = new BuchService();
        System.out.println("\n===== BuchService Tests =====");

        //Buch hinzufügen
        Buch buch = new Buch(
                9999999999998L,
                "Service Test",
                "Tester",
                "Testing"
        );
        service.addBook(buch);

        if(service.getAllBooks().stream()
                .anyMatch(b -> b.getIsbn().equals(9999999999998L))) {

            System.out.println("Buch hinzugefügt");
        }

        //ISBN duplikate
        try {
            service.addBook(buch);
            System.out.println("ISBN Duplikate erlaubt");
        }
        catch(RuntimeException e){
            System.out.println("ISBN Duplikate nicht erlaubt");
        }

        //Verfügbare Bücher in einem Regal
        System.out.println("\nVerfügbare Bücher in Regal 1:");
        service.getAvailableBooksByRegal(1)
                .forEach(System.out::println);

    }

    public static void testExemplarService() {
        RegalRepository regalRepo = new RegalRepository();
        ExemplarService service = new ExemplarService();
        System.out.println("\n===== ExemplarService Tests =====");

        //einem Regal zuordnen
        Exemplar exemplar =
                service.getAll().get(0);
        Regal regal =
                regalRepo.findAll().get(1);
        service.moveToRegal(exemplar,regal);
        Exemplar updated =
                service.getById(
                        exemplar.getExemplarID()
                );
        if(updated.getRegal().getRegalID()
                .equals(regal.getRegalID())){
            System.out.println("Exemplar einem Regal zugeordnet");
        }

        //nicht existentes Exemplar löschen
        try{
            service.deleteExemplar(-1);
            System.out.println("Invalid delete akzeptiert");
        }
        catch(RuntimeException e){
            System.out.println("Invalid delete abgelehnt");
        }

    }

    public static void testMitgliedService() {
        MitgliedService service = new MitgliedService();
        System.out.println("\n===== MitgliedService Tests =====");

        //zwei mal dieselbe Email
        Mitglied m =
                new Mitglied(
                        "Duplicate",
                        "test@test.de"
                );
        service.addMitglied(m);
        try{
            service.addMitglied(
                    new Mitglied(
                            "Another",
                            "test@test.de")
            );
            System.out.println("Duplicate email akzeptiert");
        }
        catch(RuntimeException e){
            System.out.println("Duplicate email abgelehnt");
        }

        //update
        Mitglied user =
                service.getMitgliedByEmail(
                        "test@test.de"
                );
        user.setName("Updated");
        service.updateMitglied(user);
        System.out.println("updateMitglied() erfolgreich");

        //findByEmail
        Mitglied result =
                service.getMitgliedByEmail(
                        "test@test.de"
                );
        if(result != null)
            System.out.println("findByEmail() erfolgreich");
    }

    public static void testRegalService() {
        RegalService service = new RegalService();
        ExemplarRepository exemplarRepo = new ExemplarRepository();
        System.out.println("\n===== RegalService Tests =====");

        //hinzufügen
        Regal regal =
                new Regal("Service Shelf");
        service.addRegal(regal);
        System.out.println("addRegal() erfolgreich");

        //update
        regal.setStandort("Updated Shelf");
        service.updateRegal(regal);
        System.out.println("updateRegal() erfolgreich");

        //löschen
        service.deleteRegal(
                regal.getRegalID()
        );
        System.out.println("deleteRegal() erfolgreich");

        //volles Regal löschen
        service.deleteRegal(2);
    }

}