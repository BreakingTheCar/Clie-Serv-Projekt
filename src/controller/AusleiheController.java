package controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Ausleihe;
import model.Exemplar;
import model.Mitglied;
import service.AusleiheService;
import service.ExemplarService;
import service.MitgliedService;

import java.util.List;

@Path("/ausleihen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AusleiheController {

    private AusleiheService ausleiheService = new AusleiheService();
    private ExemplarService exemplarService = new ExemplarService();
    private MitgliedService mitgliedService = new MitgliedService();

    @GET
    public List<Ausleihe> getAll() {
        return ausleiheService.getAllLoans();
    }

    @GET
    @Path("/active")
    public List<Ausleihe> getActive() {
        return ausleiheService.getActiveLoans();
    }

    @POST
    @Path("/borrow/{exemplarId}/{mitgliedId}")
    public Response borrow(@PathParam("exemplarId") Integer exemplarId,
                           @PathParam("mitgliedId") Integer mitgliedId) {
        try {
            Exemplar exemplar = exemplarService.getById(exemplarId);
            Mitglied mitglied = mitgliedService.getMitgliedById(mitgliedId);

            if (exemplar == null) {
                return Response.status(404).entity("Exemplar nicht gefunden").build();
            }
            if (mitglied == null) {
                return Response.status(404).entity("Mitglied nicht gefunden").build();
            }

            ausleiheService.borrowBook(exemplar, mitglied);
            return Response.status(201).build();

        } catch (RuntimeException e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/return/{ausleiheId}")
    public Response returnBook(@PathParam("ausleiheId") Integer ausleiheId) {
        try {
            ausleiheService.returnBook(ausleiheId);
            return Response.ok().build();
        } catch (RuntimeException e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }
}