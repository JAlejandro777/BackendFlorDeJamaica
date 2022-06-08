package Alejandro.BackendCentroNaturista.Repositories;

import Alejandro.BackendCentroNaturista.Model.Tblusuario;
import Alejandro.BackendCentroNaturista.Model.Tblventa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Tblventa, Long> {


}
