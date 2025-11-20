package br.com.fiap.to;

import br.com.fiap.enums.Level;
import jakarta.validation.constraints.NotNull;

public class RoleTO {
    private Long idRole;
    @NotNull (message = "O nome do cargo é obrigatório")
    private String name;
    @NotNull (message = "A descrição do cargo é obrigatória")
    private String description;
    @NotNull (message = "O nivel do cargo [JUNIOR, PLENO ou SENIOR] é obrigatório")
    private Level level;

    public RoleTO() {
    }

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
