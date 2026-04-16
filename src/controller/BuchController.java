package controller;

import model.Buch;
import service.BuchService;

import java.util.List;

public class BuchController {
    private BuchService service = new BuchService();

    public void createBook(Long isbn, String titel, String autor, String kategorie) {
        Buch buch = new Buch(isbn, titel, autor, kategorie);
        service.addBook(buch);
    }

    public List<Buch> getBooks() {
        return service.getAllBooks();
    }

    public void deleteBook(Long isbn) {
        service.deleteBook(isbn);
    }
}

//wenn mit Springboot:
//@RestController
//@RequestMapping("/books")
//public class BuchController {
//
//    private BuchService service;
//
//    @GetMapping
//    public List<Buch> getAll() {
//        return service.getAllBooks();
//    }
//}
