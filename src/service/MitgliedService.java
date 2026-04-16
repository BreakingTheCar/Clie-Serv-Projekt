package service;

import model.Mitglied;
import repository.MitgliedRepository;

import java.util.List;

public class MitgliedService {
    private MitgliedRepository repo = new MitgliedRepository();

    public void addMitglied(Mitglied mitglied) {
        // simple validation
        if (repo.findByEmail(mitglied.getEmail()) != null) {
            throw new RuntimeException("Email already exists!");
        }
        repo.save(mitglied);
    }

    public Mitglied getMitgliedById(Integer id) {
        return repo.findById(id);
    }

    public List<Mitglied> getAllMitglieder() {
        return repo.findAll();
    }

    public Mitglied getMitgliedByName(String name) {
        return repo.findByName(name);
    }

    public Mitglied getMitgliedByEmail(String email) {
        return repo.findByEmail(email);
    }

    public List<Mitglied> getAllMitgliederWithLoans() {
        return repo.findAllWithLoans();
    }

    public void updateMitglied(Mitglied mitglied) {
        repo.update(mitglied);
    }
}
