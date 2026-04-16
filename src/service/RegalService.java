package service;

import model.Regal;
import repository.RegalRepository;

import java.util.List;

public class RegalService {
    private RegalRepository repo = new RegalRepository();

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
        Regal r = repo.findById(id);
        if (r != null) {
            repo.delete(r);
        }
    }
}
