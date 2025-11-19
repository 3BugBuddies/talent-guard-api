package br.com.fiap.bo;

import br.com.fiap.dao.RemedioDAO;
import br.com.fiap.to.RemedioTO;

import java.util.ArrayList;

public class RemedioBO {
    private RemedioDAO remedioDAO;

    public ArrayList<RemedioTO> findAll() {
        remedioDAO = new RemedioDAO();
        // aqui se implementa a regra de negócios
        return remedioDAO.findAll();
    }

    public RemedioTO findByCodigo(Long codigo) {
        remedioDAO = new RemedioDAO();
        // aqui se implementa a regra de negócios
        return remedioDAO.findByCodigo(codigo);
    }

    public RemedioTO save (RemedioTO remedio) {
        remedioDAO = new RemedioDAO();
        // aqui se implementa a regra de negócios
        // verificando se o remédio já está vencido
        /*if (remedio.getDataDeValidade().isBefore(LocalDate.now())) {
            return null;
        }*/
        return remedioDAO.save(remedio);
    }

    public boolean delete(Long codigo) {
        remedioDAO = new RemedioDAO();
        // aqui se implementa a regra de negócios
        return remedioDAO.delete(codigo);
    }

    public RemedioTO update(RemedioTO remedio) {
        remedioDAO = new RemedioDAO();
        // aqui se implementa a regra de negócios
        return remedioDAO.update(remedio);
    }
}
