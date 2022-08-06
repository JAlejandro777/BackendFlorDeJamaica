package Alejandro.BackendCentroNaturista.Controller;

import Alejandro.BackendCentroNaturista.Model.Tblproducto;
import Alejandro.BackendCentroNaturista.Model.Tblproveedor;
import Alejandro.BackendCentroNaturista.Model.Tblusuario;
import Alejandro.BackendCentroNaturista.Repositories.ProductoRepository;
import Alejandro.BackendCentroNaturista.Repositories.ProveedorRepository;
import Alejandro.BackendCentroNaturista.Repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProductoControllerTest {
    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private ProveedorRepository proveedorRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @InjectMocks
    private ProductoController productoController;
    private Tblproducto producto;
    private Tblproveedor proveedor;
    private Tblusuario usuario;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
        //proveedor
        this.proveedor = new Tblproveedor();
        this.proveedor.setProid("9999");
        this.proveedor.setPronombre("Detodito");
        this.proveedor.setProciudad("Bogota");
        this.proveedor.setProcontacto("3204341221");
        //usuario
        this.usuario = new Tblusuario();
        this.usuario.setTblrol_rolid(1);
        this.usuario.setUsucedula("1346464577");
        this.usuario.setUsunombre("Alejandro");
        this.usuario.setUsucelular("3145667845");
        this.usuario.setUsucorreo("prueba@gmail.com");
        this.usuario.setUsucontrasena("Aa124@");
    }
    @Test
    void testNewProducto() throws Exception{
        when(productoRepository.save(this.producto)).thenReturn(this.producto);
        when(productoRepository.findById("")).thenReturn(Optional.ofNullable(this.producto));
        when(proveedorRepository.findById("9999")).thenReturn(Optional.ofNullable(this.proveedor));
        when(usuarioRepository.findNamebyId("Alejandro")).thenReturn(Optional.ofNullable(this.usuario));
        assertNotNull(productoController.newProduct(this.producto));
    }
    @Test
    void testAllProducts() throws Exception{
        when(productoRepository.findAll()).thenReturn(Arrays.asList(this.producto));
        assertNotNull(productoController.getAllProducts());
    }
    @Test
    void testProductById() throws Exception{
        when(productoRepository.findById("123456")).thenReturn(Optional.ofNullable(this.producto));
        assertNotNull(productoController.getProduct("123456"));
    }
}