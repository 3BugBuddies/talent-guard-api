package br.com.fiap.bo;

import br.com.fiap.dao.EmployeeDAO;
import br.com.fiap.dao.RoleDAO;
import br.com.fiap.enums.Level;
import br.com.fiap.exceptions.RoleException;
import br.com.fiap.to.RoleTO;

import java.util.ArrayList;

public class RoleBO {
    private RoleDAO roleDAO;
    private EmployeeDAO employeeDAO;

    public RoleTO save (RoleTO roleTO) throws RoleException {
        roleDAO = new RoleDAO();
        Level roleLevel = roleTO.getLevel();

        if (roleLevel != Level.JUNIOR && roleLevel != Level.PLENO && roleLevel != Level.SENIOR) {
            throw new RoleException("Nível de cargo inválido. Os níveis permitidos são: JUNIOR, PLENO ou SENIOR.");
        }

        return roleDAO.save(roleTO);
    }

    public RoleTO update (RoleTO roleTO) throws RoleException {
        roleDAO = new RoleDAO();
        Level roleLevel = roleTO.getLevel();

        if (roleDAO.findById(roleTO.getIdRole()) == null) {
            throw new RoleException("Não existe um cargo com o id informado.");
        }
        if (roleLevel != Level.JUNIOR && roleLevel != Level.PLENO && roleLevel != Level.SENIOR) {
            throw new RoleException("Nível de cargo inválido. Os níveis permitidos são: JUNIOR, PLENO ou SENIOR.");
        }

        return roleDAO.update(roleTO);
    }

    public boolean delete (Long idRole) throws RoleException {
        roleDAO = new RoleDAO();
        employeeDAO = new EmployeeDAO();
        if (!employeeDAO.findAllByRole(idRole).isEmpty()) {
            throw new RoleException("Não é possível excluir o cargo, existem funcionários associados a ele.");
        }
        return roleDAO.delete(idRole);
    }

    public ArrayList<RoleTO> findAll () {
        roleDAO = new RoleDAO();
        return roleDAO.findAll();
    }

    public RoleTO findById (Long idRole) {
        roleDAO = new RoleDAO();
        return roleDAO.findById(idRole);
    }


}
