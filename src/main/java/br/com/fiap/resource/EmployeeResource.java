package br.com.fiap.resource;

import br.com.fiap.bo.EmployeeBO;
import br.com.fiap.exceptions.EmployeeException;
import br.com.fiap.to.EmployeeTO;
import br.com.fiap.to.ErrorResponse;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

@Path("/employee")
public class EmployeeResource {
    private EmployeeBO employeeBO = new EmployeeBO();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(@Valid EmployeeTO employee) {
        try {
            EmployeeTO result = employeeBO.save(employee);
            if (result == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return Response.status(Response.Status.CREATED).entity(result).build();

        } catch (EmployeeException e) {
            ErrorResponse errorResponse = new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@Valid EmployeeTO employee, @PathParam("id") Long id) {
        try {
            employee.setIdEmployee(id);
            EmployeeTO resultado = employeeBO.update(employee);
            if (resultado == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return Response.status(Response.Status.OK).entity(resultado).build();
        } catch (EmployeeException e) {
            ErrorResponse errorResponse = new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            if (employeeBO.delete(id)) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (EmployeeException e) {
            ErrorResponse errorResponse = new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        ArrayList<EmployeeTO> resultado = employeeBO.findAll();
        return Response.status(Response.Status.OK).entity(resultado).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        EmployeeTO resultado = employeeBO.findById(id);
        if (resultado == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } return Response.status(Response.Status.OK).entity(resultado).build();
    }


}
