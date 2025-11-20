package br.com.fiap.bo;

import br.com.fiap.dao.EmployeeDAO;
import br.com.fiap.dao.RoleDAO;
import br.com.fiap.exceptions.EmployeeException;
import br.com.fiap.exceptions.RoleException;
import br.com.fiap.to.EmployeeTO;
import br.com.fiap.to.RoleTO;

import java.util.ArrayList;

public class EmployeeBO {
    private EmployeeDAO employeeDAO;
    private RoleDAO roleDAO;

    public EmployeeTO save (EmployeeTO employeeTO) throws EmployeeException {
        employeeDAO = new EmployeeDAO();
        roleDAO = new RoleDAO();

        RoleTO employeeRole = roleDAO.findById(employeeTO.getRole().getIdRole());

        if(employeeRole == null){
            throw new EmployeeException("Não existe um cargo com o id informado.");
        }

        employeeTO.setRole(employeeRole);

        return employeeDAO.save(employeeTO);
    }

    public EmployeeTO update (EmployeeTO employeeTO) throws EmployeeException {
        employeeDAO = new EmployeeDAO();
        roleDAO = new RoleDAO();

        RoleTO employeeRole = roleDAO.findById(employeeTO.getRole().getIdRole());
        if( employeeRole == null){
            throw new EmployeeException("Não existe um cargo com o id informado.");
        }
        employeeTO.setRole(employeeRole);

        return employeeDAO.update(employeeTO);
    }

    public boolean delete (Long idEmployee) throws EmployeeException {
        employeeDAO = new EmployeeDAO();
        // to-do deletar as analises de salario que foram feitas do funcionario
        return employeeDAO.delete(idEmployee);
    }

    public EmployeeTO findById (Long idEmployee) {
        employeeDAO = new EmployeeDAO();
        return employeeDAO.findById(idEmployee);
    }

    public ArrayList<EmployeeTO> findAll () {
        employeeDAO = new EmployeeDAO();
        return employeeDAO.findAll();
    }
}
