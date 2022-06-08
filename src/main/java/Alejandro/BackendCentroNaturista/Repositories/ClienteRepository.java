package Alejandro.BackendCentroNaturista.Repositories;

import Alejandro.BackendCentroNaturista.Model.Tblcliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Tblcliente, String> {
}
