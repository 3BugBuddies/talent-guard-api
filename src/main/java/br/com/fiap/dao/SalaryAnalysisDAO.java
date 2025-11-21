package br.com.fiap.dao;

import br.com.fiap.enums.Level;
import br.com.fiap.enums.RiskClassification;
import br.com.fiap.to.BenchmarkTO;
import br.com.fiap.to.EmployeeTO;
import br.com.fiap.to.RoleTO;
import br.com.fiap.to.SalaryAnalysisTO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalaryAnalysisDAO {
    public SalaryAnalysisTO save(SalaryAnalysisTO analysis) {
        String sql = "INSERT INTO T_TG_ANALISE_SALARIAL (id_funcionario, id_benchmark, vl_salario_analise, vl_medio_mercado_analise, ds_risco, dt_data_analise) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql, new String[]{"ID_ANALISE_SALARIAL"})) {

            ps.setLong(1, analysis.getEmployee().getIdEmployee());
            ps.setLong(2, analysis.getBenchmark().getIdBenchmark());
            ps.setBigDecimal(3, analysis.getRecordedSalary());
            ps.setBigDecimal(4, analysis.getMarketAverage());
            ps.setString(5, analysis.getRisk().name());
            ps.setDate(6, Date.valueOf(analysis.getAnalysisDate()));

            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        analysis.setIdSalaryAnalysis(rs.getLong(1));
                    }
                }
                return analysis;
            }
            return null;

        } catch (SQLException e) {
            System.out.println("Erro ao salvar a análise: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public SalaryAnalysisTO update(SalaryAnalysisTO analysis) {

        String sql = "UPDATE T_TG_ANALISE_SALARIAL SET id_benchmark=?, vl_salario_analise=?, vl_medio_mercado_analise=?, ds_risco=?, dt_data_analise=? " +
                "WHERE id_analise_salarial=?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, analysis.getBenchmark().getIdBenchmark());
            ps.setBigDecimal(2, analysis.getRecordedSalary());
            ps.setBigDecimal(3, analysis.getMarketAverage());
            ps.setString(4, analysis.getRisk().name());
            ps.setDate(5, Date.valueOf(analysis.getAnalysisDate()));
            ps.setLong(6, analysis.getIdSalaryAnalysis());
            if (ps.executeUpdate() > 0) {
                return analysis;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar a análise: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public boolean delete(Long idRole) {
        String sql = "DELETE FROM T_TG_ANALISE_SALARIAL WHERE id_cargo = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idRole);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar cargo: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
    }


    public ArrayList<SalaryAnalysisTO> findAll() {
        ArrayList<SalaryAnalysisTO> listAnalysis = new ArrayList<>();
        String sql = "SELECT A.*, F.*, C.* FROM T_TG_ANALISE_SALARIAL A INNER JOIN T_TG_FUNCIONARIO F ON A.id_funcionario = F.id_funcionario " +
                "INNER JOIN T_TG_CARGO C ON F.id_cargo = C.id_cargo ORDER BY A.dt_data_analise DESC";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    SalaryAnalysisTO analysis = new SalaryAnalysisTO();

                    analysis.setIdSalaryAnalysis(rs.getLong("id_analise_salarial"));
                    analysis.setRecordedSalary(rs.getBigDecimal("vl_salario_analise"));
                    analysis.setMarketAverage(rs.getBigDecimal("vl_medio_mercado_analise"));
                    analysis.setRisk(RiskClassification.valueOf(rs.getString("ds_risco")));
                    analysis.setAnalysisDate(rs.getDate("dt_data_analise").toLocalDate());

                    EmployeeTO employee = new EmployeeTO();
                    employee.setIdEmployee(rs.getLong("id_funcionario"));
                    employee.setFullName(rs.getString("nc_nome_completo"));
                    employee.setSalary(rs.getBigDecimal("vl_salario_atual"));
                    employee.setDepartment(rs.getString("nm_nome_departamento"));
                    employee.setEducationLevel(rs.getString("ds_nivel_educacao"));
                    employee.setBirthDate(rs.getDate("dt_data_nascimento").toLocalDate());
                    employee.setHireDate(rs.getDate("dt_data_contratacao").toLocalDate());

                    RoleTO role = new RoleTO();
                    role.setIdRole(rs.getLong("id_cargo"));
                    role.setName(rs.getString("nm_nome_cargo"));
                    role.setDescription(rs.getString("ds_funcao"));
                    role.setLevel(Level.valueOf(rs.getString("nm_nivel")));

                    BenchmarkTO benchmark = new BenchmarkTO();
                    benchmark.setIdBenchmark(rs.getLong("id_benchmark"));

                    employee.setRole(role);
                    analysis.setEmployee(employee);
                    analysis.setBenchmark(benchmark);
                    listAnalysis.add(analysis);
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar as análises: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return listAnalysis;
    }


    public SalaryAnalysisTO findById(Long idBenchmark) {

        SalaryAnalysisTO analysis = new SalaryAnalysisTO();
        String sql = "SELECT * FROM T_TG_ANALISE_SALARIAL WHERE id_benchmark = ? ORDER BY dt_data_analise DESC";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idBenchmark);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                analysis.setIdSalaryAnalysis(rs.getLong("id_benchmark"));
                analysis.setRecordedSalary(rs.getBigDecimal("vl_salario_analise"));
                analysis.setMarketAverage(rs.getBigDecimal("vl_medio_mercado_analise"));
                analysis.setAnalysisDate(rs.getDate("dt_data_analise").toLocalDate());
                analysis.setRisk(RiskClassification.valueOf(rs.getString("ds_risco")));

                EmployeeTO employee = new EmployeeTO();
                employee.setIdEmployee(rs.getLong("id_funcionario"));

                BenchmarkTO benchmark = new BenchmarkTO();
                benchmark.setIdBenchmark(rs.getLong("id_benchmark"));

                analysis.setEmployee(employee);
                analysis.setBenchmark(benchmark);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar análises pelo benchmark: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return analysis;
    }

    public ArrayList<SalaryAnalysisTO> findAllByBenchmark(Long idBenchmark) {

        ArrayList<SalaryAnalysisTO> listAnalysis = new ArrayList<>();
        String sql = "SELECT * FROM T_TG_ANALISE_SALARIAL WHERE id_benchmark = ? ORDER BY dt_data_analise DESC";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idBenchmark);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    SalaryAnalysisTO analysis = new SalaryAnalysisTO();
                    analysis.setIdSalaryAnalysis(rs.getLong("id_benchmark"));
                    analysis.setRecordedSalary(rs.getBigDecimal("vl_salario_analise"));
                    analysis.setMarketAverage(rs.getBigDecimal("vl_medio_mercado_analise"));
                    analysis.setRisk(RiskClassification.valueOf(rs.getString("ds_risco")));
                    listAnalysis.add(analysis);
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar análises pelo benchmark: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return listAnalysis;
    }

    public boolean deleteByEmployee(Long idEmployee) {
        String sql = "DELETE FROM T_TG_ANALISE_SALARIAL WHERE id_funcionario = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idEmployee);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir análises: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
    }


}
