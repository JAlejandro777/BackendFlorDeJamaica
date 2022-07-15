package Alejandro.BackendCentroNaturista.Repositories;

import Alejandro.BackendCentroNaturista.Model.SuperUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperUsuarioRepository extends JpaRepository<SuperUsuario,Long> {
}
