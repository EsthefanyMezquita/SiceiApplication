package mx.uady.sicei.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.uady.sicei.model.Tutoria;
import mx.uady.sicei.model.TutoriaLlave;

@Repository
public interface TutoriaRepository extends CrudRepository<Tutoria, TutoriaLlave> {
  List<Tutoria> findByAlumnoId(Integer id_Alumno);
  List<Tutoria> findByProfesorId(Integer id_Profesor);
}
