package Alejandro.BackendCentroNaturista.Controller;

import Alejandro.BackendCentroNaturista.Model.Tblcliente;
import Alejandro.BackendCentroNaturista.Model.Tblproducto;
import Alejandro.BackendCentroNaturista.Model.Tblventa;
import Alejandro.BackendCentroNaturista.Repositories.ClienteRepository;
import Alejandro.BackendCentroNaturista.Repositories.ProductoRepository;
import Alejandro.BackendCentroNaturista.Repositories.ProveedorRepository;
import Alejandro.BackendCentroNaturista.Repositories.VentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class VentaControllerTest {
    @Mock
    private VentaRepository ventaRepository;
    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @InjectMocks
    private VentaController ventaController;
    private Tblproducto producto;
    private Tblcliente cliente;
    private Tblventa venta;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //venta
        this.venta = new Tblventa();
        this.venta.setVencliente("Alejandro Infante");
        this.venta.setVenproducto("Vitamina C");
        this.venta.setVencantidadunidades(4);
        this.venta.setVeniva(34200);
        this.venta.setVenvalorpagar(214200);
        this.venta.setVenfechaactual("2022-08-05");
        //producto
        this.producto =  new Tblproducto();
        this.producto.setProcodigo("123456");
        this.producto.setProcaracteristica("Capsulas color amarillo");
        this.producto.setProcategoria("Vitaminas");
        this.producto.setProfechaingreso("2022-08-04");
        this.producto.setProfechavencimiento("2024-08-04");
        this.producto.setPronombre("Vitamina c");
        this.producto.setPropreciosugerido(15000);
        this.producto.setProunidadesdisponibles(50);
        this.producto.setTblproveedor_proid("9999");
        this.producto.setTblusuario_usuid("Alejandro");
        //cliente
        this.cliente = new Tblcliente();
        this.cliente.setClicedula("1000000000");
        this.cliente.setClicelular("3100000000");
        this.cliente.setClicorreo("prueba@gmail.com");
        this.cliente.setClinombre("Pruebas Unitarias");
    }
    @Test
    void testNewSale() throws Exception{
        when(ventaRepository.save(this.venta)).thenReturn(this.venta);
        when(productoRepository.findNamebyId("")).thenReturn(Optional.ofNullable(this.producto));
        when(clienteRepository.findNamebyId("Alejandro Infante")).thenReturn(Optional.ofNullable(this.cliente));
        assertNotNull(ventaController.newSale(this.venta));
    }
}