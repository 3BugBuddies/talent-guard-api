package br.com.fiap.dao;

import br.com.fiap.enums.Level;
import br.com.fiap.to.BenchmarkTO;
import br.com.fiap.to.RoleTO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BenchmarkDAO {
    public BenchmarkTO save(BenchmarkTO benchmark) {
        String sql = "INSERT INTO T_TG_BENCHMARK (id_cargo, vl_piso_mercado, vl_medio_mercado, vl_teto_mercado, dt_ultima_atualizacao) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql, new String[]{"ID_BENCHMARK"})) {

            ps.setLong(1, benchmark.getRole().getIdRole());
            ps.setBigDecimal(2, benchmark.getFloorSalary());
            ps.setBigDecimal(3, benchmark.getAverageSalary());
            ps.setBigDecimal(4, benchmark.getCeilingSalary());
            ps.setDate(5, Date.valueOf(benchmark.getReferenceDate()));
            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        benchmark.setIdBenchmark(rs.getLong(1));
                    }
                }
                return benchmark;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar benchmark: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public BenchmarkTO update(BenchmarkTO benchmark) {
        String sql = "UPDATE T_TG_BENCHMARK SET id_cargo=?, vl_piso_mercado=?, vl_medio_mercado=?, vl_teto_mercado=?, dt_ultima_atualizacao=? WHERE id_benchmark=?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {

            ps.setLong(1, benchmark.getRole().getIdRole());
            ps.setBigDecimal(2, benchmark.getFloorSalary());
            ps.setBigDecimal(3, benchmark.getAverageSalary());
            ps.setBigDecimal(4, benchmark.getCeilingSalary());
            ps.setDate(5, Date.valueOf(benchmark.getReferenceDate()));
            ps.setLong(6, benchmark.getIdBenchmark());

            if (ps.executeUpdate() > 0) {
                return benchmark;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar benchmark: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public boolean delete(Long idBenchmark) {
        String sql = "DELETE FROM T_TG_BENCHMARK WHERE id_benchmark = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idBenchmark);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar benchmark: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public ArrayList<BenchmarkTO> findAll() {
        String sql = "SELECT B.*, C.nm_nome_cargo, C.ds_funcao, C.nm_nivel FROM T_TG_BENCHMARK B INNER JOIN T_TG_CARGO C ON B.id_cargo = C.id_cargo ORDER BY C.nm_nome_cargo ASC";

        ArrayList<BenchmarkTO> benchmarks = new ArrayList<>();

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    BenchmarkTO bench = new BenchmarkTO();
                    bench.setIdBenchmark(rs.getLong("id_benchmark"));
                    bench.setFloorSalary(rs.getBigDecimal("vl_piso_mercado"));
                    bench.setAverageSalary(rs.getBigDecimal("vl_medio_mercado"));
                    bench.setCeilingSalary(rs.getBigDecimal("vl_teto_mercado"));
                    bench.setReferenceDate(rs.getDate("dt_ultima_atualizacao").toLocalDate());

                    RoleTO role = new RoleTO();
                    role.setIdRole(rs.getLong("id_cargo"));
                    role.setName(rs.getString("nm_nome_cargo"));
                    role.setDescription(rs.getString("ds_funcao"));
                    role.setLevel(Level.valueOf(rs.getString("nm_nivel")));
                    bench.setRole(role);
                    benchmarks.add(bench);
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar benchmarks: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return benchmarks;
    }

    public BenchmarkTO findById(Long idBenchmark) {
        BenchmarkTO benchmark = new BenchmarkTO();
        String sql = "SELECT * FROM T_TG_BENCHMARK WHERE id_benchmark = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idBenchmark);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                benchmark.setIdBenchmark(rs.getLong("id_benchmark"));
                benchmark.setFloorSalary(rs.getBigDecimal("vl_piso_mercado"));
                benchmark.setAverageSalary(rs.getBigDecimal("vl_medio_mercado"));
                benchmark.setCeilingSalary(rs.getBigDecimal("vl_teto_mercado"));
                benchmark.setReferenceDate(rs.getDate("dt_ultima_atualizacao").toLocalDate());
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar benchmark por ID: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return benchmark;
    }

}
