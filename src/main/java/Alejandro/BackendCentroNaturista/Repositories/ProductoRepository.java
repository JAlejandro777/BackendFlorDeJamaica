package Alejandro.BackendCentroNaturista.Repositories;

import Alejandro.BackendCentroNaturista.Model.Tblproducto;
import Alejandro.BackendCentroNaturista.Model.Tblrol;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Tblproducto, String> {
}
