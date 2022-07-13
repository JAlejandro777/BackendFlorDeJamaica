package Alejandro.BackendCentroNaturista.Repositories;

import Alejandro.BackendCentroNaturista.Model.Tblrol;
import Alejandro.BackendCentroNaturista.Model.Tblusuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Tblrol, Long> {
}
