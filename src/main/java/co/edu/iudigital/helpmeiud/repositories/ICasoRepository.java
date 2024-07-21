package co.edu.iudigital.helpmeiud.repositories;

import co.edu.iudigital.helpmeiud.models.Caso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // ambiguo
public interface ICasoRepository extends JpaRepository<Caso, Long> {

    List<Caso> findAllByUsuarioUsername(/*@Param("username")*/ String username);

    List<Caso> findAllByVisibleTrue();
}