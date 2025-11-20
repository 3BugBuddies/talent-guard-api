package br.com.fiap.to;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeTO {
    private Long idEmployee;
    @NotBlank(message = "O nome completo é obrigatório")
    private String fullName;
    @NotNull (message = "O atributo Data de Nascimento é obrigatório")
    @Past(message = "A data de nascimento obrigatoriamente tem que ser no passado")
    private LocalDate birthDate;
    @NotNull (message = "O atributo Data da Contratação é obrigatório")
    @PastOrPresent (message = "A data de contratação obrigatoriamente tem que ser no passado ou presente")
    private LocalDate hireDate;
    @NotNull (message = "O salário é obrigatório")
    @Positive
    private BigDecimal salary;
    @NotBlank (message = "O departamento é obrigatório")
    private String department;
    @NotBlank (message = "O nível de escolaridade é obrigatório")
    private String educationLevel;
    @NotNull (message = "O cargo é obrigatório para o funcionário ser analisado")
    private RoleTO role;

    public EmployeeTO() {
    }

    public Long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public RoleTO getRole() {
        return role;
    }

    public void setRole(RoleTO role) {
        this.role = role;
    }

}
