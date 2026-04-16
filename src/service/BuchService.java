package service;

import model.Buch;
import repository.BuchRepository;

import java.util.List;

public class BuchService {
    private BuchRepository repo = new BuchRepository();

    public void addBook(Buch buch) {
        if (repo.findById(buch.getIsbn()) != null) {
            throw new RuntimeException("Buch existiert bereits!");
        }
        repo.save(buch);
    }

    public List<Buch> getAllBooks() {
        return repo.findAll();
    }

    public List<Buch> getAvailableBooksByRegal(Integer regalId) {
        return repo.findAvailableBooksByRegal(regalId);
    }

    public void deleteBook(Long isbn) {
        Buch buch = repo.findById(isbn);
        if (buch != null) {
            repo.delete(buch);
        }
    }
}
