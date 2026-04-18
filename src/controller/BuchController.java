package controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import model.Buch;
import service.BuchService;

import java.util.List;

@Path("/buecher")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BuchController {

    private BuchService service = new BuchService();

    @GET
    public List<Buch> getBooks() {
        return service.getAllBooks();
    }
    @GET
    @Path("/regal/{regalId}")
    public List<Buch> getAvailableByRegal(@PathParam("regalId") Integer regalId) {
        return service.getAvailableBooksByRegal(regalId);
    }

    @POST
    public void createBook(Buch buch) {
        service.addBook(buch);
    }

    @DELETE
    @Path("/{isbn}")
    public void deleteBook(@PathParam("isbn") Long isbn) {
        service.deleteBook(isbn);
    }
}