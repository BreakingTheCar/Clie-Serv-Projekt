package controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Mitglied;
import service.MitgliedService;

import java.util.List;

@Path("/mitglieder")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MitgliedController {

    private MitgliedService service = new MitgliedService();

    @GET
    public List<Mitglied> getAll() {
        return service.getAllMitglieder();
    }

    @GET
    @Path("/loans")
    public List<Mitglied> getAllWithLoans() {
        return service.getAllMitgliederWithLoans();
    }

    @GET
    @Path("/{id}")
    public Mitglied getById(@PathParam("id") Integer id) {
        return service.getMitgliedById(id);
    }
    @GET
    @Path("/name/{name}")
    public Response getByName(@PathParam("name") String name) {
        Mitglied m = service.getMitgliedByName(name);
        if (m == null) return Response.status(404).build();
        return Response.ok(m).build();
    }

    @GET
    @Path("/email/{email}")
    public Response getByEmail(@PathParam("email") String email) {
        Mitglied m = service.getMitgliedByEmail(email);
        if (m == null) return Response.status(404).build();
        return Response.ok(m).build();
    }

    @POST
    public void create(Mitglied mitglied) {
        service.addMitglied(mitglied);
    }

    @PUT
    public void update(Mitglied mitglied) {
        service.updateMitglied(mitglied);
    }
}