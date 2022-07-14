package Alejandro.BackendCentroNaturista.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;


@Entity
@Getter
@Setter
@Table(name="tblproducto")
public class Tblproducto {
    @Id
    private String procodigo;
    @Column(name = "tblproveedor_proid")
    private String tblproveedor_proid;
    @Column(name = "tblusuario_usuid")
    private String tblusuario_usuid;
    @Column(name = "pronombre")
    private String pronombre;
    @Column(name = "procaracteristica")
    private  String procaracteristica;
    @Column(name = "procategoria")
    private String procategoria;
    @Column(name = "propreciosugerido")
    private int propreciosugerido;
    @Column(name = "prounidadesdisponibles")
    private int prounidadesdisponibles;
    @Column(name = "profechaingreso")
    private String profechaingreso;
    @Column(name = "profechavencimiento")
    private String profechavencimiento;

    public Tblproducto() {
    }

    public Tblproducto(String procodigo, String tblproveedor_proid, String tblusuario_usuid, String pronombre, String procaracteristica, String procategoria, int propreciosugerido, int prounidadesdisponibles, String profechaingreso, String profechavencimiento) {
        this.procodigo = procodigo;
        this.tblproveedor_proid = tblproveedor_proid;
        this.tblusuario_usuid = tblusuario_usuid;
        this.pronombre = pronombre;
        this.procaracteristica = procaracteristica;
        this.procategoria = procategoria;
        this.propreciosugerido = propreciosugerido;
        this.prounidadesdisponibles = prounidadesdisponibles;
        this.profechaingreso = profechaingreso;
        this.profechavencimiento = profechavencimiento;
    }

    public String getProcodigo() {
        return procodigo;
    }

    public void setProcodigo(String procodigo) {
        this.procodigo = procodigo;
    }

    public String getTblproveedor_proid() {
        return tblproveedor_proid;
    }

    public void setTblproveedor_proid(String tblproveedor_proid) {
        this.tblproveedor_proid = tblproveedor_proid;
    }

    public String getTblusuario_usuid() {
        return tblusuario_usuid;
    }

    public void setTblusuario_usuid(String tblusuario_usuid) {
        this.tblusuario_usuid = tblusuario_usuid;
    }

    public String getPronombre() {
        return pronombre;
    }

    public void setPronombre(String pronombre) {
        this.pronombre = pronombre;
    }

    public String getProcaracteristica() {
        return procaracteristica;
    }

    public void setProcaracteristica(String procaracteristica) {
        this.procaracteristica = procaracteristica;
    }

    public String getProcategoria() {
        return procategoria;
    }

    public void setProcategoria(String procategoria) {
        this.procategoria = procategoria;
    }

    public int getPropreciosugerido() {
        return propreciosugerido;
    }

    public void setPropreciosugerido(int propreciosugerido) {
        this.propreciosugerido = propreciosugerido;
    }

    public int getProunidadesdisponibles() {
        return prounidadesdisponibles;
    }

    public void setProunidadesdisponibles(int prounidadesdisponibles) {
        this.prounidadesdisponibles = prounidadesdisponibles;
    }

    public String getProfechaingreso() {
        return profechaingreso;
    }

    public void setProfechaingreso(String profechaingreso) {
        this.profechaingreso = profechaingreso;
    }

    public String getProfechavencimiento() {
        return profechavencimiento;
    }

    public void setProfechavencimiento(String profechavencimiento) {
        this.profechavencimiento = profechavencimiento;
    }
}
