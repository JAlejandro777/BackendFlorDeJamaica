package Alejandro.BackendCentroNaturista.Controller;

import Alejandro.BackendCentroNaturista.Model.Tblcliente;
import Alejandro.BackendCentroNaturista.Model.Tblrol;
import Alejandro.BackendCentroNaturista.Model.Tblusuario;
import Alejandro.BackendCentroNaturista.Repositories.ClienteRepository;
import Alejandro.BackendCentroNaturista.Repositories.RolRepository;
import Alejandro.BackendCentroNaturista.Repositories.UsuarioRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UsuarioControllerTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private RolRepository rolRepository;
    @InjectMocks
    private UsuarioController usuarioController;
    private Tblusuario tblusuario;
    private Tblrol tblrol;
    private JSONObject json;
    private static final Argon2 ARGON2 = Argon2Factory.create();

    private static final int ITERATIONS = 2;
    private static final int MEMORY= 65536;
    private static final int PARALLELISM = 1;
    @BeforeEach
    void setUp() throws JSONException {
        MockitoAnnotations.openMocks(this);
        this.tblusuario = new Tblusuario();
        this.tblusuario.setTblrol_rolid(1);
        this.tblusuario.setUsucedula("1346464577");
        this.tblusuario.setUsunombre("Prueba Unitaria");
        this.tblusuario.setUsucelular("3145667845");
        this.tblusuario.setUsucorreo("prueba@gmail.com");
        this.tblusuario.setUsucontrasena("Aa124@");
        String pass = ARGON2.hash(ITERATIONS, MEMORY, PARALLELISM, this.tblusuario.getUsucontrasena());
        this.tblusuario.setUsucontrasena(pass);
        this.tblrol = new Tblrol();
        this.tblrol.setRolnombre("prueba");
        this.tblrol.setRolpermisos("todos");
        this.json = new JSONObject();
        json.put("correo", "prueba@gmail.com");
        json.put("contrasena", "Aa124@");
    }
    @Test
    void testNewCliente() throws Exception{
        when(usuarioRepository.save(this.tblusuario)).thenReturn(this.tblusuario);
        when(rolRepository.findById(new Long(1))).thenReturn(Optional.ofNullable(this.tblrol));
        assertNotNull(usuarioController.newUser(this.tblusuario));
    }
    @Test
    void testNewSesion() throws Exception{
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(this.tblusuario));
        when(rolRepository.findById(new Long(1))).thenReturn(Optional.ofNullable(this.tblrol));
        assertNotNull(usuarioController.validation(String.valueOf(this.json)));
    }
}