package br.com.fiap.to;

import br.com.fiap.enums.RiskClassification;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;
import java.time.LocalDate;

public class SalaryAnalysisTO {
    private Long idSalaryAnalysis;
    private BigDecimal recordedSalary;
    private BigDecimal marketAverage;
    private RiskClassification risk;
    private LocalDate analysisDate;
    @NotNull(message = "O funcionário analisado é obrigatório")
    private EmployeeTO employee;
    @NotNull(message = "O benchmark utilizado é obrigatório")
    private BenchmarkTO benchmark;

    public SalaryAnalysisTO() {
    }

    public Long getIdSalaryAnalysis() {
        return idSalaryAnalysis;
    }

    public void setIdSalaryAnalysis(Long idSalaryAnalysis) {
        this.idSalaryAnalysis = idSalaryAnalysis;
    }

    public BigDecimal getRecordedSalary() {
        return recordedSalary;
    }

    public void setRecordedSalary(BigDecimal recordedSalary) {
        this.recordedSalary = recordedSalary;
    }

    public BigDecimal getMarketAverage() {
        return marketAverage;
    }

    public void setMarketAverage(BigDecimal marketAverage) {
        this.marketAverage = marketAverage;
    }

    public RiskClassification getRisk() {
        return risk;
    }

    public void setRisk(RiskClassification risk) {
        this.risk = risk;
    }

    public LocalDate getAnalysisDate() {
        return analysisDate;
    }

    public void setAnalysisDate(LocalDate analysisDate) {
        this.analysisDate = analysisDate;
    }

    public EmployeeTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeTO employee) {
        this.employee = employee;
    }

    public BenchmarkTO getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(BenchmarkTO benchmark) {
        this.benchmark = benchmark;
    }
}
