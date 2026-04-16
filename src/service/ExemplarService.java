package service;

import model.Exemplar;
import model.Regal;
import repository.ExemplarRepository;

import java.util.List;

public class ExemplarService {
    private ExemplarRepository repo = new ExemplarRepository();

    public void addExemplar(Exemplar exemplar) {
        repo.save(exemplar);
    }

    public Exemplar getById(Integer id) {
        return repo.findById(id);
    }

    public List<Exemplar> getAll() {
        return repo.findAll();
    }

    public void deleteExemplar(Integer id) {
        Exemplar e = repo.findById(id);
        if (e == null) {
            throw new RuntimeException("Exemplar nicht gefunden");
        }
        repo.delete(e);
    }

    public List<Exemplar> getExemplarsByRegalId(Integer regalId) {
        return repo.findByRegalId(regalId);
    }

    public List<Exemplar> getAvailableExemplars() {
        return repo.findAvailable();
    }

    public void moveToRegal(Exemplar exemplar, Regal newRegal) {
        exemplar.setRegal(newRegal);
        repo.update(exemplar);
    }
}
