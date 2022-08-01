package Alejandro.BackendCentroNaturista.Repositories;

import Alejandro.BackendCentroNaturista.Model.RecuperarContraseña;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecuperContraseñaRepository extends JpaRepository<RecuperarContraseña,String> {
}
