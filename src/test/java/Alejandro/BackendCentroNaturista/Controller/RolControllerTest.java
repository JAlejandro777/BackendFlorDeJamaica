package Alejandro.BackendCentroNaturista.Controller;

import Alejandro.BackendCentroNaturista.Model.Tblrol;
import Alejandro.BackendCentroNaturista.Repositories.RolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RolControllerTest {
    @Mock
    private RolRepository rolRepository;
    @InjectMocks
    private RolController rolController;
    private Tblrol rol;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.rol =  new Tblrol();
        this.rol.setRolnombre("asdfg");
        this.rol.setRolpermisos("Visualizar todos los productos");
    }
    @Test
    void testNewRol() throws Exception{
        when(rolRepository.save(this.rol)).thenReturn(this.rol);
        assertNotNull(rolController.newRol(this.rol));
    }
}