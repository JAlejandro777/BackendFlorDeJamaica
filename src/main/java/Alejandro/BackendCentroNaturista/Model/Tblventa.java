package Alejandro.BackendCentroNaturista.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="tblventa")
public class Tblventa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long venid;
    @Column(name = "vencantidadunidades")
    private int vencantidadunidades;
    @Column(name = "veniva")
    private int veniva;
    @Column(name = "venvalorpagar")
    private int venvalorpagar;
    @Column(name = "venproducto")
    private String venproducto;
    @Column(name = "vencliente")
    private String vencliente;
    @Column(name = "venfechaactual")
    private String venfechaactual;

    public Tblventa() {
    }

    public Tblventa(int vencantidadunidades, int veniva, int venvalorpagar, String venproducto, String vencliente, String venfechaactual) {
        this.vencantidadunidades = vencantidadunidades;
        this.veniva = veniva;
        this.venvalorpagar = venvalorpagar;
        this.venproducto = venproducto;
        this.vencliente = vencliente;
        this.venfechaactual = venfechaactual;
    }

    public Long getVenid() {
        return venid;
    }

    public void setVenid(Long venid) {
        this.venid = venid;
    }

    public int getVencantidadunidades() {
        return vencantidadunidades;
    }

    public void setVencantidadunidades(int vencantidadunidades) {
        this.vencantidadunidades = vencantidadunidades;
    }

    public int getVeniva() {
        return veniva;
    }

    public void setVeniva(int veniva) {
        this.veniva = veniva;
    }

    public int getVenvalorpagar() {
        return venvalorpagar;
    }

    public void setVenvalorpagar(int venvalorpagar) {
        this.venvalorpagar = venvalorpagar;
    }

    public String getVenproducto() {
        return venproducto;
    }

    public void setVenproducto(String venproducto) {
        this.venproducto = venproducto;
    }

    public String getVencliente() {
        return vencliente;
    }

    public void setVencliente(String vencliente) {
        this.vencliente = vencliente;
    }

    public String getVenfechaactual() {
        return venfechaactual;
    }

    public void setVenfechaactual(String venfechaactual) {
        this.venfechaactual = venfechaactual;
    }
}
