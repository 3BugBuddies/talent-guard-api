package br.com.fiap.resource;

import br.com.fiap.bo.SalaryAnalysisBO;
import br.com.fiap.exceptions.BenchmarkException;
import br.com.fiap.exceptions.EmployeeException;
import br.com.fiap.exceptions.SalaryAnalysisException;
import br.com.fiap.to.SalaryAnalysisTO;
import br.com.fiap.to.ErrorResponse;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

@Path("/analysis")
public class SalaryAnalysisResource {
    private SalaryAnalysisBO salaryAnalysisBO = new SalaryAnalysisBO();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(@Valid SalaryAnalysisTO salaryAnalysis) {
        try {
            SalaryAnalysisTO result = salaryAnalysisBO.save(salaryAnalysis);
            if (result == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return Response.status(Response.Status.CREATED).entity(result).build();

        } catch (SalaryAnalysisException | EmployeeException | BenchmarkException e) {
            ErrorResponse errorResponse = new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@Valid SalaryAnalysisTO salaryAnalysis, @PathParam("id") Long id) {
        try {
            salaryAnalysis.setIdSalaryAnalysis(id);
            SalaryAnalysisTO resultado = salaryAnalysisBO.update(salaryAnalysis);
            if (resultado == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return Response.status(Response.Status.OK).entity(resultado).build();
        } catch (SalaryAnalysisException | EmployeeException | BenchmarkException e) {
            ErrorResponse errorResponse = new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        if (salaryAnalysisBO.delete(id)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        ArrayList<SalaryAnalysisTO> resultado = salaryAnalysisBO.findAll();
        return Response.status(Response.Status.OK).entity(resultado).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        try {
            return Response.status(Response.Status.OK).entity(salaryAnalysisBO.findById(id)).build();
        } catch ( EmployeeException | BenchmarkException e) {
            ErrorResponse errorResponse = new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        } catch (SalaryAnalysisException e) {
            ErrorResponse errorResponse = new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(), e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
        }
    }


}
