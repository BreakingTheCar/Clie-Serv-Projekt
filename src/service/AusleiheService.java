package service;

import model.Ausleihe;
import model.Exemplar;
import model.Mitglied;
import repository.AusleiheRepository;

import java.time.LocalDate;
import java.util.List;

public class AusleiheService {
    private AusleiheRepository repo = new AusleiheRepository();

    public void borrowBook(Exemplar exemplar, Mitglied mitglied) {
        boolean isBorrowed = repo.findActive().stream()
                .anyMatch(a -> a.getExemplar().getExemplarID()
                        .equals(exemplar.getExemplarID()));

        if (isBorrowed) {
            throw new RuntimeException("Exemplar ist bereits ausgeliehen!");
        }

        Ausleihe ausleihe = new Ausleihe(
                exemplar,
                mitglied,
                LocalDate.now()
        );

        repo.save(ausleihe);
    }

    public void returnBook(Integer ausleiheId) {
        Ausleihe ausleihe = repo.findById(ausleiheId);

        if (ausleihe == null) {
            throw new RuntimeException("Ausleihe nicht gefunden!");
        }

        ausleihe.setRueckgabedatum(LocalDate.now());
        repo.update(ausleihe);
    }

    public List<Ausleihe> getActiveLoans() {
        return repo.findActive();
    }

    public List<Ausleihe> getAllLoans() {
        return repo.findAll();
    }
}
