package Alejandro.BackendCentroNaturista.Repositories;
import Alejandro.BackendCentroNaturista.Model.Tblusuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Tblusuario, Integer> {
    @Query(value = "select * from tblusuario where usunombre = :nombre", nativeQuery = true)
    Optional<Tblusuario> findNamebyId(@Param("nombre")String nombre);
}
