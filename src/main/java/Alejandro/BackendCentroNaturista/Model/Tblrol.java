package Alejandro.BackendCentroNaturista.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="tblrol")
public class Tblrol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolid;
    @Column(name = "rolnombre")
    private String rolnombre;
    @Column(name = "rolpermisos")
    private String rolpermisos;

    public Tblrol() {
    }

    public Tblrol(String rolnombre, String rolpermisos) {
        this.rolnombre = rolnombre;
        this.rolpermisos = rolpermisos;
    }

    public Long getRolid() {
        return rolid;
    }

    public void setRolid(Long rolid) {
        this.rolid = rolid;
    }

    public String getRolnombre() {
        return rolnombre;
    }

    public void setRolnombre(String rolnombre) {
        this.rolnombre = rolnombre;
    }

    public String getRolpermisos() {
        return rolpermisos;
    }

    public void setRolpermisos(String rolpermisos) {
        this.rolpermisos = rolpermisos;
    }
}
