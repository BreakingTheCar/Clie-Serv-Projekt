package service;

import model.Regal;
import repository.ExemplarRepository;
import repository.RegalRepository;

import java.util.List;

public class RegalService {
    private RegalRepository repo = new RegalRepository();
    private ExemplarRepository exemplarRepo = new ExemplarRepository();

    public void addRegal(Regal regal) {
        repo.save(regal);
    }

    public Regal getById(Integer id) {
        return repo.findById(id);
    }

    public List<Regal> getAll() {
        return repo.findAll();
    }

    public void updateRegal(Regal regal) {
        repo.update(regal);
    }

    public void deleteRegal(Integer id) {
        Regal regal = repo.findById(id);
        if (regal == null) {
            throw new RuntimeException("Regal nicht gefunden.");
        }
        if (!exemplarRepo.findByRegalId(id).isEmpty()) {
            throw new RuntimeException(
                    "Regal kann nicht gelöscht werden, da sich noch Exemplare darin befinden."
            );
        }
        repo.delete(regal);
    }
}
