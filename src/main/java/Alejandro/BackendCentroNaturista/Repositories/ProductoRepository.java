package Alejandro.BackendCentroNaturista.Repositories;

import Alejandro.BackendCentroNaturista.Model.Tblproducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ProductoRepository extends JpaRepository<Tblproducto, String> {
    @Query(value = "select * from tblproducto where pronombre = :nombre", nativeQuery = true)
    Optional<Tblproducto> findNamebyId(@Param("nombre")String nombre);
}
