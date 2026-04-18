package controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Exemplar;
import service.ExemplarService;
import service.RegalService;

import java.util.List;

@Path("/exemplare")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExemplarController {

    private ExemplarService service = new ExemplarService();

    @GET
    public List<Exemplar> getAll() {
        return service.getAll();
    }

    @GET
    @Path("/available")
    public List<Exemplar> getAvailable() {
        return service.getAvailableExemplars();
    }

    @GET
    @Path("/{id}")
    public Exemplar getById(@PathParam("id") Integer id) {
        return service.getById(id);
    }
    @GET
    @Path("/regal/{regalId}")
    public List<Exemplar> getByRegal(@PathParam("regalId") Integer regalId) {
        return service.getExemplarsByRegalId(regalId);
    }

    @PUT
    @Path("/move/{exemplarId}/{regalId}")
    public Response moveToRegal(@PathParam("exemplarId") Integer exemplarId,
                                @PathParam("regalId") Integer regalId) {
        try {
            Exemplar exemplar = service.getById(exemplarId);
            if (exemplar == null) {
                return Response.status(404).entity("Exemplar nicht gefunden").build();
            }
            RegalService regalService = new RegalService();
            model.Regal regal = regalService.getById(regalId);
            if (regal == null) {
                return Response.status(404).entity("Regal nicht gefunden").build();
            }
            service.moveToRegal(exemplar, regal);
            return Response.ok().build();
        } catch (RuntimeException e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @POST
    public void create(Exemplar exemplar) {
        service.addExemplar(exemplar);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Integer id) {
        service.deleteExemplar(id);
    }
}