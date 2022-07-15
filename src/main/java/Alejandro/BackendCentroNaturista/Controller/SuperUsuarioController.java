package Alejandro.BackendCentroNaturista.Controller;

import Alejandro.BackendCentroNaturista.Excepcion.Exception;
import Alejandro.BackendCentroNaturista.Model.SuperUsuario;
import Alejandro.BackendCentroNaturista.Model.Tblrol;
import Alejandro.BackendCentroNaturista.Model.Tblusuario;
import Alejandro.BackendCentroNaturista.Repositories.RolRepository;
import Alejandro.BackendCentroNaturista.Repositories.SuperUsuarioRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SuperUsuarioController {
    private static final Argon2 ARGON2 = Argon2Factory.create();

    private static final int ITERATIONS = 2;
    private static final int MEMORY= 65536;
    private static final int PARALLELISM = 1;
    @Autowired
    SuperUsuarioRepository superUsuarioRepository;
    @PostMapping("/contraseña")
    SuperUsuario newPassword(@RequestBody SuperUsuario superUsuario) {
        if(superUsuario.getContraseña().equals("")){
            throw new Exception("P-400","Contraseña incorrecta");
        }
        return superUsuarioRepository.save(superUsuario);
    }
    @GetMapping("/superUsuarios")
    public List<SuperUsuario> getAllSuperUsuario() {

        return (List<SuperUsuario>) superUsuarioRepository.findAll();
    }
    @PostMapping("/superUsuarios")
    String validation(@RequestBody String credenciales){
        JSONObject json = new JSONObject(credenciales);
        String contrasena = json.getString("contraseña");
        List<SuperUsuario> superUsuarios = new ArrayList<>();
        //usuarios = (List<Tblusuario>) usuarioRespository.findAll();
        //System.out.println("User:" + usuario + " Pass" + contrasena  );
        for (SuperUsuario u:this.getAllSuperUsuario()) {
            if(ARGON2.verify(u.getContraseña(), contrasena )){
                return "OK";
            }

        }
        return "NO";
    }
}
