package Alejandro.BackendCentroNaturista.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name="tblusuario")
public class Tblusuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuid;
    @Column(name = "tblrol_rolid")
    private int tblrol_rolid;
    @Column(name = "usucedula")
    private String usucedula;
    @Column(name = "usunombre")
    private String usunombre;
    @Column(name = "usucelular")
    private String usucelular;
    @Column(name = "usucorreo")
    private String usucorreo;
    @Column(name = "usucontrasena")
    private String usucontrasena;

    public Tblusuario() {
    }

    public Tblusuario(int tblrol_rolid, String usucedula, String usunombre, String usucelular, String usucorreo, String usucontrasena) {
        this.tblrol_rolid = tblrol_rolid;
        this.usucedula = usucedula;
        this.usunombre = usunombre;
        this.usucelular = usucelular;
        this.usucorreo = usucorreo;
        this.usucontrasena = usucontrasena;
    }

    public Tblusuario(Long  usuId, int tblrol_rolid, String usucedula, String usunombre, String usucelular, String usucorreo, String usucontrasena) {
        this.usuid = usuId;
        this.tblrol_rolid = tblrol_rolid;
        this.usucedula = usucedula;
        this.usunombre = usunombre;
        this.usucelular = usucelular;
        this.usucorreo = usucorreo;
        this.usucontrasena = usucontrasena;
    }

    public Long  getUsuId() {
        return usuid;
    }

    public void setUsuId(Long  usuId) {
        this.usuid = usuId;
    }

    public int getTblrol_rolid() {
        return tblrol_rolid;
    }

    public void setTblrol_rolid(int tblrol_rolid) {
        this.tblrol_rolid = tblrol_rolid;
    }

    public String getUsucedula() {
        return usucedula;
    }

    public void setUsucedula(String usucedula) {
        this.usucedula = usucedula;
    }

    public String getUsunombre() {
        return usunombre;
    }

    public void setUsunombre(String usunombre) {
        this.usunombre = usunombre;
    }

    public String getUsucelular() {
        return usucelular;
    }

    public void setUsucelular(String usucelular) {
        this.usucelular = usucelular;
    }

    public String getUsucorreo() {
        return usucorreo;
    }

    public void setUsucorreo(String usucorreo) {
        this.usucorreo = usucorreo;
    }

    public String getUsucontrasena() {
        return usucontrasena;
    }

    public void setUsucontrasena(String usucontrasena) {
        this.usucontrasena = usucontrasena;
    }
}
