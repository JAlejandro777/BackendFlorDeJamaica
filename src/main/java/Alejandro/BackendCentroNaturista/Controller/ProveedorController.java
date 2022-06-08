package Alejandro.BackendCentroNaturista.Controller;

import Alejandro.BackendCentroNaturista.Model.Tblproveedor;
import Alejandro.BackendCentroNaturista.Repositories.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor

public class ProveedorController {
    @Autowired
    ProveedorRepository proveedorRepository;
    @GetMapping("/proveedor")
    public List<Tblproveedor> getAllSupplier() {

        return (List<Tblproveedor>) proveedorRepository.findAll();
    }
}
