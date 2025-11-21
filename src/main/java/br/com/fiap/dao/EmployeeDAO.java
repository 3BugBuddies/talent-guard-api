package br.com.fiap.dao;

import br.com.fiap.enums.Level;
import br.com.fiap.to.EmployeeTO;
import br.com.fiap.to.RoleTO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAO {
    public EmployeeTO save(EmployeeTO employee) {
        String sql = "INSERT INTO T_TG_FUNCIONARIO (nc_nome_completo, dt_data_nascimento, vl_salario_atual, nm_nome_departamento, ds_nivel_educacao, dt_data_contratacao, id_cargo) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql, new String[]{"ID_FUNCIONARIO"})) {

            ps.setString(1, employee.getFullName());
            ps.setDate(2, Date.valueOf(employee.getBirthDate()));
            ps.setBigDecimal(3, employee.getSalary());
            ps.setString(4, employee.getDepartment());
            ps.setString(5, employee.getEducationLevel());
            ps.setDate(6, Date.valueOf(employee.getHireDate()));
            ps.setLong(7, employee.getRole().getIdRole());

            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        employee.setIdEmployee(rs.getLong(1));
                    }
                }
                return employee;
            } else{
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar o funcionário" + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public EmployeeTO update(EmployeeTO employee) {
        String sql = "UPDATE T_TG_FUNCIONARIO SET nc_nome_completo=?, dt_data_nascimento=?, vl_salario_atual=?, nm_nome_departamento=?, ds_nivel_educacao=?, dt_data_contratacao=?, id_cargo=? WHERE id_funcionario=?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {

            ps.setString(1, employee.getFullName());
            ps.setDate(2, Date.valueOf(employee.getBirthDate()));
            ps.setBigDecimal(3, employee.getSalary());
            ps.setString(4, employee.getDepartment());
            ps.setString(5, employee.getEducationLevel());
            ps.setDate(6, Date.valueOf(employee.getHireDate()));
            ps.setLong(7, employee.getRole().getIdRole());
            ps.setLong(8, employee.getIdEmployee());

            if (ps.executeUpdate() > 0) {
                return employee;
            } else{
                return null;
            }

        } catch (SQLException e) {
            System.out.println("Erro a atualizar funcionário: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public boolean delete(Long idEmployee) {
        String sql = "DELETE FROM T_TG_FUNCIONARIO WHERE id_funcionario = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idEmployee);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro para deletar funcionário: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public ArrayList<EmployeeTO> findAll() {
        String sql = "SELECT f.*, c.nm_nome_cargo, c.ds_funcao, c.nm_nivel FROM T_TG_FUNCIONARIO f " +
                "INNER JOIN T_TG_CARGO c ON f.id_cargo  = c.id_cargo ORDER BY f.nc_nome_completo ASC";
        ArrayList<EmployeeTO> employees = new ArrayList<>();

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs != null) {
                while (rs.next()) {
                    EmployeeTO employee = new EmployeeTO();
                    employee.setIdEmployee(rs.getLong("id_funcionario"));
                    employee.setFullName(rs.getString("nc_nome_completo"));
                    employee.setBirthDate(rs.getDate("dt_data_nascimento").toLocalDate());
                    employee.setSalary(rs.getBigDecimal("vl_salario_atual"));
                    employee.setDepartment(rs.getString("nm_nome_departamento"));
                    employee.setEducationLevel(rs.getString("ds_nivel_educacao"));
                    employee.setHireDate(rs.getDate("dt_data_contratacao").toLocalDate());

                    RoleTO role = new RoleTO();
                    role.setIdRole(rs.getLong("id_cargo"));
                    role.setName(rs.getString("nm_nome_cargo"));
                    role.setDescription(rs.getString("ds_funcao"));
                    role.setLevel(Level.valueOf(rs.getString("nm_nivel")));
                    employee.setRole(role);
                    employees.add(employee);
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro para listar funcionários: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return employees;
    }

    public EmployeeTO findById(Long idEmployee) {
        EmployeeTO employee = new EmployeeTO();
        RoleTO role = new RoleTO();

        String sql = "SELECT * FROM T_TG_FUNCIONARIO WHERE id_funcionario = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idEmployee);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    employee.setIdEmployee(rs.getLong("id_funcionario"));
                    employee.setFullName(rs.getString("nc_nome_completo"));
                    employee.setBirthDate(rs.getDate("dt_data_nascimento").toLocalDate());
                    employee.setSalary(rs.getBigDecimal("vl_salario_atual"));
                    employee.setDepartment(rs.getString("nm_nome_departamento"));
                    employee.setEducationLevel(rs.getString("ds_nivel_educacao"));
                    employee.setHireDate(rs.getDate("dt_data_contratacao").toLocalDate());

                    role.setIdRole(rs.getLong("id_cargo"));
                    employee.setRole(role);
                }else{
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar funcionário por ID: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return employee;
    }

    public ArrayList<EmployeeTO> findAllByRole(Long idRole) {
        String sql = "SELECT * FROM T_TG_FUNCIONARIO WHERE id_cargo = ? ORDER BY nc_nome_completo ASC";
        ArrayList<EmployeeTO> employees = new ArrayList<>();

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idRole);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs != null) {
                    while (rs.next()) {
                        EmployeeTO employee = new EmployeeTO();
                        employee.setIdEmployee(rs.getLong("id_funcionario"));
                        employee.setFullName(rs.getString("nc_nome_completo"));
                        employee.setBirthDate(rs.getDate("dt_data_nascimento").toLocalDate());
                        employee.setSalary(rs.getBigDecimal("vl_salario_atual"));
                        employee.setDepartment(rs.getString("nm_nome_departamento"));
                        employee.setEducationLevel(rs.getString("ds_nivel_educacao"));
                        employee.setHireDate(rs.getDate("dt_data_contratacao").toLocalDate());

                        RoleTO role = new RoleTO();
                        role.setIdRole(rs.getLong("id_cargo"));
                        employee.setRole(role);

                        employees.add(employee);
                    }
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar funcionários por cargo: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return employees;
    }
}
