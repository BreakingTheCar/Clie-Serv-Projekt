package controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Regal;
import service.RegalService;

import java.util.List;

@Path("/regale")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RegalController {

    private RegalService service = new RegalService();

    @GET
    public List<Regal> getAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public Regal getById(@PathParam("id") Integer id) {
        return service.getById(id);
    }

    @POST
    public void create(Regal regal) {
        service.addRegal(regal);
    }

    @PUT
    public void update(Regal regal) {
        service.updateRegal(regal);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        try {
            service.deleteRegal(id);
            return Response.noContent().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }
}