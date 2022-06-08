package Alejandro.BackendCentroNaturista.Controller;

import Alejandro.BackendCentroNaturista.Excepcion.Exception;
import Alejandro.BackendCentroNaturista.Model.Tblrol;
import Alejandro.BackendCentroNaturista.Repositories.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor

public class RolController {
    @Autowired
    RolRepository rolRepository;
    @PostMapping("/rol")
    Tblrol newUser(@RequestBody Tblrol tblrol) {
        if(tblrol.getRolnombre().equals("")){
            throw new Exception("P-400","Nombre incorrecto");
        }
        if(tblrol.getRolpermisos().equals("")){
            throw new Exception("P-400","Permisos incorrectos");
        }
        return rolRepository.save(tblrol);
    }
    @GetMapping("/rol")
    public List<Tblrol> getAllRol() {

        return (List<Tblrol>) rolRepository.findAll();
    }
}
