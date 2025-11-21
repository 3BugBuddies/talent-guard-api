package br.com.fiap.bo;

import br.com.fiap.dao.BenchmarkDAO;
import br.com.fiap.dao.EmployeeDAO;
import br.com.fiap.dao.RoleDAO;
import br.com.fiap.dao.SalaryAnalysisDAO;
import br.com.fiap.enums.RiskClassification;
import br.com.fiap.exceptions.BenchmarkException;
import br.com.fiap.exceptions.EmployeeException;
import br.com.fiap.exceptions.SalaryAnalysisException;
import br.com.fiap.to.BenchmarkTO;
import br.com.fiap.to.EmployeeTO;
import br.com.fiap.to.RoleTO;
import br.com.fiap.to.SalaryAnalysisTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class SalaryAnalysisBO {
    private SalaryAnalysisDAO analysisDAO;
    private EmployeeDAO employeeDAO;
    private BenchmarkDAO benchmarkDAO;
    private RoleDAO roleDAO;

    public SalaryAnalysisTO save(SalaryAnalysisTO salaryAnalysisTO) throws EmployeeException, BenchmarkException, SalaryAnalysisException {
        analysisDAO = new SalaryAnalysisDAO();
        employeeDAO = new EmployeeDAO();
        benchmarkDAO = new BenchmarkDAO();
        roleDAO = new RoleDAO();

        EmployeeTO employee = employeeDAO.findById(salaryAnalysisTO.getEmployee().getIdEmployee());
        if (employee == null) {
            throw new EmployeeException("Não existe um funcionário com o id informado.");
        }
        BenchmarkTO benchmark = benchmarkDAO.findById(salaryAnalysisTO.getBenchmark().getIdBenchmark());

        if (benchmark == null) {
            throw new BenchmarkException("Não existe um benchmark com o id informado.");
        }


        if (!employee.getRole().getIdRole().equals(benchmark.getRole().getIdRole())) {
            throw new SalaryAnalysisException("O cargo do funcionário não corresponde ao cargo do benchmark usado para a análise.");
        }

        RoleTO role = roleDAO.findById(employee.getRole().getIdRole());

        employee.setRole(role);
        benchmark.setRole(role);
        salaryAnalysisTO.setEmployee(employee);
        salaryAnalysisTO.setBenchmark(benchmark);
        salaryAnalysisTO.setRecordedSalary(employee.getSalary());
        salaryAnalysisTO.setMarketAverage(benchmark.getAverageSalary());
        salaryAnalysisTO.setAnalysisDate(LocalDate.now());

        RiskClassification risk = calculateRisk(employee.getSalary(), benchmark);
        salaryAnalysisTO.setRisk(risk);

        return analysisDAO.save(salaryAnalysisTO);
    }

    public SalaryAnalysisTO update(SalaryAnalysisTO salaryAnalysisTO) throws EmployeeException, BenchmarkException, SalaryAnalysisException {
        analysisDAO = new SalaryAnalysisDAO();
        employeeDAO = new EmployeeDAO();
        benchmarkDAO = new BenchmarkDAO();
        roleDAO = new RoleDAO();

        if (analysisDAO.findById(salaryAnalysisTO.getIdSalaryAnalysis()) == null) {
            throw new SalaryAnalysisException("Não existe uma análise salarial com o id informado.");
        }

        EmployeeTO employee = employeeDAO.findById(salaryAnalysisTO.getEmployee().getIdEmployee());
        if (employee == null) {
            throw new EmployeeException("Não existe um funcionário com o id informado.");
        }

        BenchmarkTO benchmark = benchmarkDAO.findById(salaryAnalysisTO.getBenchmark().getIdBenchmark());
        if (benchmark == null) {
            throw new BenchmarkException("Não existe um benchmark com o id informado.");
        }

        if (!employee.getRole().getIdRole().equals(benchmark.getRole().getIdRole())) {
            throw new SalaryAnalysisException("O cargo do funcionário não corresponde ao cargo do benchmark usado para a análise.");
        }

        RoleTO role = roleDAO.findById(employee.getRole().getIdRole());

        employee.setRole(role);
        benchmark.setRole(role);

        salaryAnalysisTO.setEmployee(employee);
        salaryAnalysisTO.setBenchmark(benchmark);
        salaryAnalysisTO.setRecordedSalary(employee.getSalary());
        salaryAnalysisTO.setMarketAverage(benchmark.getAverageSalary());
        salaryAnalysisTO.setAnalysisDate(LocalDate.now());


        RiskClassification risk = calculateRisk(employee.getSalary(), benchmark);
        salaryAnalysisTO.setRisk(risk);
        return analysisDAO.update(salaryAnalysisTO);
    }

    public boolean delete(Long idAnalysis) {
        analysisDAO = new SalaryAnalysisDAO();
        return analysisDAO.delete(idAnalysis);
    }

    public ArrayList<SalaryAnalysisTO> findAll() {
        analysisDAO = new SalaryAnalysisDAO();
        return analysisDAO.findAll();
    }

    public SalaryAnalysisTO findById(Long idAnalysis) throws EmployeeException, BenchmarkException, SalaryAnalysisException {
        analysisDAO = new SalaryAnalysisDAO();
        employeeDAO = new EmployeeDAO();
        benchmarkDAO = new BenchmarkDAO();
        roleDAO = new RoleDAO();

        SalaryAnalysisTO salaryAnalysisTO = analysisDAO.findById(idAnalysis);
        if (salaryAnalysisTO == null) {
            throw new SalaryAnalysisException("Não existe uma análise salarial com o id informado.");
        }

        EmployeeTO employee = employeeDAO.findById(salaryAnalysisTO.getEmployee().getIdEmployee());
        if (employee == null) {
            throw new EmployeeException("Não existe um funcionário com o id informado.");
        }

        BenchmarkTO benchmark = benchmarkDAO.findById(salaryAnalysisTO.getBenchmark().getIdBenchmark());
        if (benchmark == null) {
            throw new BenchmarkException("Não existe um benchmark com o id informado.");
        }

        RoleTO role = roleDAO.findById(employee.getRole().getIdRole());

        employee.setRole(role);
        benchmark.setRole(role);

        salaryAnalysisTO.setEmployee(employee);
        salaryAnalysisTO.setBenchmark(benchmark);

        return salaryAnalysisTO;
    }


    private RiskClassification calculateRisk(BigDecimal employeeSalary, BenchmarkTO benchmark) {
        BigDecimal marketAvg = benchmark.getAverageSalary();
        if (employeeSalary.compareTo(marketAvg.multiply(new BigDecimal("0.8"))) < 0) {
            return RiskClassification.BELOW_FLOOR;
        }
        else if (employeeSalary.compareTo(marketAvg.multiply(new BigDecimal("1.2"))) > 0) {
            return RiskClassification.ABOVE_CEILING;
        }
        else {
            return RiskClassification.ON_TARGET;
        }
    }
}
