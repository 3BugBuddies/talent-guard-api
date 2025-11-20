package br.com.fiap.bo;

import br.com.fiap.dao.BenchmarkDAO;
import br.com.fiap.dao.RoleDAO;
import br.com.fiap.dao.SalaryAnalysisDAO;
import br.com.fiap.exceptions.BenchmarkException;
import br.com.fiap.to.BenchmarkTO;
import br.com.fiap.to.RoleTO;

import java.time.LocalDate;
import java.util.ArrayList;

public class BenchmarkBO {
    private BenchmarkDAO benchmarkDAO;
    private RoleDAO roleDAO;
    private SalaryAnalysisDAO salaryAnalysisDAO;

    public BenchmarkTO save(BenchmarkTO benchmarkTO) throws BenchmarkException {

        benchmarkDAO = new BenchmarkDAO();
        roleDAO = new RoleDAO();
        RoleTO benchmarkRole = roleDAO.findById(benchmarkTO.getRole().getIdRole());
        if (benchmarkRole == null) {
            throw new BenchmarkException("Não existe um cargo com o id informado.");
        }
        benchmarkTO.setRole(benchmarkRole);
        benchmarkTO.setReferenceDate(LocalDate.now());
        return benchmarkDAO.save(benchmarkTO);
    }

    public BenchmarkTO update(BenchmarkTO benchmarkTO) throws BenchmarkException {
        benchmarkDAO = new BenchmarkDAO();
        roleDAO = new RoleDAO();

        if (benchmarkDAO.findById(benchmarkTO.getIdBenchmark()) == null) {
            throw new BenchmarkException("Não existe um benchmark com o id informado.");
        }

        RoleTO benchmarkRole = roleDAO.findById(benchmarkTO.getRole().getIdRole());
        if (benchmarkRole == null) {
            throw new BenchmarkException("Não existe um cargo com o id informado.");
        }
        benchmarkTO.setRole(benchmarkRole);
        benchmarkTO.setReferenceDate(LocalDate.now());
        return benchmarkDAO.update(benchmarkTO);
    }

    public boolean delete(Long idBenchmark) throws BenchmarkException {
        benchmarkDAO = new BenchmarkDAO();
        salaryAnalysisDAO = new SalaryAnalysisDAO();

        if(!salaryAnalysisDAO.findAllByBenchmark(idBenchmark).isEmpty()) {
            throw new BenchmarkException("Não é possível deletar o benchmark pois existem análises salariais associadas a ele.");
        }

        return benchmarkDAO.delete(idBenchmark);
    }

    public ArrayList<BenchmarkTO> findAll() {
        benchmarkDAO = new BenchmarkDAO();
        return benchmarkDAO.findAll();
    }

    public BenchmarkTO findById(Long idBenchmark) throws BenchmarkException {
        benchmarkDAO = new BenchmarkDAO();
        roleDAO = new RoleDAO();

        BenchmarkTO benchmark = benchmarkDAO.findById(idBenchmark);

        if (benchmark == null) {
            throw new BenchmarkException("Não existe um benchmark com o id informado.");
        }
        benchmark.setRole(roleDAO.findById(benchmark.getRole().getIdRole()));

        return benchmark;
    }

}
