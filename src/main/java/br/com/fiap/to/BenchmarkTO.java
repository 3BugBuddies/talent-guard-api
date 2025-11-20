package br.com.fiap.to;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BenchmarkTO {
    private Long idBenchmark;
    @NotNull (message = "O piso salarial não pode ser nulo")
    @Positive
    private BigDecimal floorSalary;
    @NotNull (message = "A média salarial não pode ser nula")
    @Positive
    private BigDecimal averageSalary;
    @NotNull (message = "O teto salarial não pode ser nulo")
    @Positive
    private BigDecimal ceilingSalary;
    @PastOrPresent (message = "A data de referência não pode ser futura")
    private LocalDate referenceDate;

    @NotNull (message = "O cargo é obrigatório para criar o benchmark salarial")
    private RoleTO role;

    public BenchmarkTO() {
    }

    public Long getIdBenchmark() {
        return idBenchmark;
    }

    public void setIdBenchmark(Long idBenchmark) {
        this.idBenchmark = idBenchmark;
    }

    public BigDecimal getFloorSalary() {
        return floorSalary;
    }

    public void setFloorSalary(BigDecimal floorSalary) {
        this.floorSalary = floorSalary;
    }

    public BigDecimal getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(BigDecimal averageSalary) {
        this.averageSalary = averageSalary;
    }

    public BigDecimal getCeilingSalary() {
        return ceilingSalary;
    }

    public void setCeilingSalary(BigDecimal ceilingSalary) {
        this.ceilingSalary = ceilingSalary;
    }

    public LocalDate getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(LocalDate referenceDate) {
        this.referenceDate = referenceDate;
    }

    public RoleTO getRole() {
        return role;
    }

    public void setRole(RoleTO role) {
        this.role = role;
    }
}
