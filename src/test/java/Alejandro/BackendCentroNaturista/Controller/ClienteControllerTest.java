package Alejandro.BackendCentroNaturista.Controller;

import Alejandro.BackendCentroNaturista.Model.Tblcliente;
import Alejandro.BackendCentroNaturista.Repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ClienteControllerTest {
    @Mock
    private ClienteRepository clienteRepository;
    @InjectMocks
    private ClienteController clienteController;


    private Tblcliente cliente;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.cliente = new Tblcliente();
        this.cliente.setClicedula("1000000000");
        this.cliente.setClicelular("3100000000");
        this.cliente.setClicorreo("prueba@gmail.com");
        this.cliente.setClinombre("Pruebas Unitarias");
    }

    @Test
    void testAllClientes() throws Exception{
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(this.cliente));
        assertNotNull(clienteController.getAllCustomers());
    }
    @Test
    void testFindCliente() throws Exception{
        when(clienteRepository.findById("1000000000")).thenReturn(Optional.ofNullable(this.cliente));
        assertNotNull(clienteController.getCustomer("1000000000"));
    }
    @Test
    void testNewCliente() throws Exception{
        when(clienteRepository.save(this.cliente)).thenReturn(this.cliente);
        assertNotNull(clienteController.newCustomer(this.cliente));
    }
}