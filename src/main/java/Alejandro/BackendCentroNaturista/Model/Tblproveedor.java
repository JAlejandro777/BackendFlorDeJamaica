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
@Table(name="tblproveedor")
public class Tblproveedor {
    @Id
    private int proid;
    @Column(name = "pronombre")
    private String pronombre;
    @Column(name = "procontacto")
    private String procontacto;
    @Column(name = "prociudad")
    private String prociudad;

    public Tblproveedor() {
    }

    public Tblproveedor(int proid, String pronombre, String procontacto, String prociudad) {
        this.proid = proid;
        this.pronombre = pronombre;
        this.procontacto = procontacto;
        this.prociudad = prociudad;
    }

    public int getProid() {
        return proid;
    }

    public void setProid(int proid) {
        this.proid = proid;
    }

    public String getPronombre() {
        return pronombre;
    }

    public void setPronombre(String pronombre) {
        this.pronombre = pronombre;
    }

    public String getProcontacto() {
        return procontacto;
    }

    public void setProcontacto(String procontacto) {
        this.procontacto = procontacto;
    }

    public String getProciudad() {
        return prociudad;
    }

    public void setProciudad(String prociudad) {
        this.prociudad = prociudad;
    }
}
