package br.com.fiap.resource;

import br.com.fiap.bo.BenchmarkBO;
import br.com.fiap.exceptions.BenchmarkException;
import br.com.fiap.exceptions.EmployeeException;
import br.com.fiap.to.BenchmarkTO;
import br.com.fiap.to.ErrorResponse;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

@Path("/benchmark")
public class BenchmarkResource {
    private BenchmarkBO benchmarkBO = new BenchmarkBO();
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(@Valid BenchmarkTO benchmark) {
        try {
            BenchmarkTO result = benchmarkBO.save(benchmark);
            if (result == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return Response.status(Response.Status.CREATED).entity(result).build();

        } catch (BenchmarkException e) {
            ErrorResponse errorResponse = new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@Valid BenchmarkTO benchmark, @PathParam("id") Long id) {
        try {
            benchmark.setIdBenchmark(id);
            BenchmarkTO resultado = benchmarkBO.update(benchmark);
            if (resultado == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return Response.status(Response.Status.OK).entity(resultado).build();
        } catch (BenchmarkException e) {
            ErrorResponse errorResponse = new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            if (benchmarkBO.delete(id)) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (BenchmarkException e) {
            ErrorResponse errorResponse = new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        ArrayList<BenchmarkTO> resultado = benchmarkBO.findAll();
        return Response.status(Response.Status.OK).entity(resultado).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        try {
            BenchmarkTO resultado = benchmarkBO.findById(id);
            if (resultado == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.status(Response.Status.OK).entity(resultado).build();
        
        } catch (BenchmarkException e) {
            ErrorResponse errorResponse = new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
    }
}
