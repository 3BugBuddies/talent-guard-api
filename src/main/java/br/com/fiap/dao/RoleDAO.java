package br.com.fiap.dao;

import br.com.fiap.enums.Level;
import br.com.fiap.to.RoleTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {

    public RoleTO save(RoleTO roleTO) {
        String sql = "INSERT INTO T_TG_CARGO (nm_nome_cargo, ds_funcao, nm_nivel) VALUES (?, ?, ?)";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql, new String[]{"ID_CARGO"})) {
            ps.setString(1, roleTO.getName());
            ps.setString(2, roleTO.getDescription());
            ps.setString(3, roleTO.getLevel().name());

            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        roleTO.setIdRole(rs.getLong(1));
                    }
                }
                return roleTO;
            } else{
                return null;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao salvar cargo: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public RoleTO update(RoleTO roleTO) {
        String sql = "UPDATE T_TG_CARGO SET nm_nome_cargo=?, ds_funcao=?, nm_nivel=? WHERE id_cargo=?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, roleTO.getName());
            ps.setString(2, roleTO.getDescription());
            ps.setString(3, roleTO.getLevel().name());
            ps.setLong(4, roleTO.getIdRole());

            if (ps.executeUpdate() > 0) {
                return roleTO;
            }else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar cargo: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public boolean delete(Long idRole) {
        String sql = "DELETE FROM T_TG_CARGO WHERE id_cargo = ?";

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

    public ArrayList<RoleTO> findAll() {
        String sql = "SELECT * FROM T_TG_CARGO ORDER BY id_cargo ASC";
        ArrayList<RoleTO> roles = new ArrayList<>();

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs != null) {
                while (rs.next()) {
                    RoleTO role = new RoleTO();
                    role.setIdRole(rs.getLong("id_cargo"));
                    role.setName(rs.getString("nm_nome_cargo"));
                    role.setDescription(rs.getString("ds_funcao"));
                    role.setLevel(Level.valueOf(rs.getString("nm_nivel")));
                    roles.add(role);
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar cargos: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return roles;
    }

    public RoleTO findById(Long idRole) {
        RoleTO role = new RoleTO();
        String sql = "SELECT * FROM T_TG_CARGO WHERE id_cargo = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idRole);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    role.setIdRole(rs.getLong("id_cargo"));
                    role.setName(rs.getString("nm_nome_cargo"));
                    role.setDescription(rs.getString("ds_funcao"));
                    role.setLevel(Level.valueOf(rs.getString("nm_nivel")));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar o cargo por ID: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return role;
    }
}

