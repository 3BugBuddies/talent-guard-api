package br.com.fiap.resource;

import br.com.fiap.bo.RoleBO;
import br.com.fiap.exceptions.RoleException;
import br.com.fiap.to.RoleTO;
import br.com.fiap.to.ErrorResponse;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

@Path("/role")
public class RoleResource {
    private RoleBO roleBO = new RoleBO();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(@Valid RoleTO role) {
        try {
            RoleTO result = roleBO.save(role);
            if (result == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return Response.status(Response.Status.CREATED).entity(result).build();
        } catch (RoleException e) {
            ErrorResponse errorResponse = new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@Valid RoleTO role, @PathParam("id") Long id) {
        try {
            role.setIdRole(id);
            RoleTO resultado = roleBO.update(role);
            if (resultado == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.status(Response.Status.OK).entity(resultado).build();
        } catch (RoleException e) {
            ErrorResponse errorResponse = new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            if (roleBO.delete(id)) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (RoleException e) {
            ErrorResponse errorResponse = new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        ArrayList<RoleTO> resultado = roleBO.findAll();
        return Response.status(Response.Status.OK).entity(resultado).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        RoleTO resultado = roleBO.findById(id);
        if (resultado == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } return Response.status(Response.Status.OK).entity(resultado).build();
    }

    
}
