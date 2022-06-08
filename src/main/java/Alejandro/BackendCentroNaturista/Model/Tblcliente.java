package Alejandro.BackendCentroNaturista.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Getter
@Setter
@Table(name="tblcliente")
public class Tblcliente {
    @Id
    private String clicedula;
    @Column(name = "clinombre")
    private String clinombre;
    @Column(name = "clicelular")
    private String clicelular;
    @Column(name = "clicorreo")
    private String clicorreo;

    public Tblcliente() {
    }

    public Tblcliente(String clicedula, String clinombre, String clicelular, String clicorreo) {
        this.clicedula = clicedula;
        this.clinombre = clinombre;
        this.clicelular = clicelular;
        this.clicorreo = clicorreo;
    }

    public String getClicedula() {
        return clicedula;
    }

    public void setClicedula(String clicedula) {
        this.clicedula = clicedula;
    }

    public String getClinombre() {
        return clinombre;
    }

    public void setClinombre(String clinombre) {
        this.clinombre = clinombre;
    }

    public String getClicelular() {
        return clicelular;
    }

    public void setClicelular(String clicelular) {
        this.clicelular = clicelular;
    }

    public String getClicorreo() {
        return clicorreo;
    }

    public void setClicorreo(String clicorreo) {
        this.clicorreo = clicorreo;
    }
}
