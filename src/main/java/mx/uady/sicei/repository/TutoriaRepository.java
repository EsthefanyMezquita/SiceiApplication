package mx.uady.sicei.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.uady.sicei.model.Tutoria;


@Repository
public interface TutoriaRepository extends CrudRepository<Tutoria, Integer> {
  List<Tutoria> findByAlumnoId(Integer alumnoId);
  List<Tutoria> findByProfesorId(Integer profesorId);
  Tutoria findByAlumnoIdAndProfesorId(Integer alumnoId, Integer profesorId);
}
