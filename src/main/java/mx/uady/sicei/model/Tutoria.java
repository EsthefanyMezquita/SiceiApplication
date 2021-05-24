package mx.uady.sicei.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mx.uady.sicei.model.TutoriaLlave;
import mx.uady.sicei.model.Profesor;
import mx.uady.sicei.model.Alumno;

@Entity
@Table(name = "tutorias")
public class Tutoria {

  @EmbeddedId
  private TutoriaLlave id;

  @ManyToOne
  private Alumno alumno;

  @ManyToOne
  private Profesor profesor;

  @Column(name = "horas")
  private Integer horas;

  public Tutoria() {}

  public Tutoria(TutoriaLlave id, Integer horas) {
    this.id = id;
    this.horas = horas;
  }

  public void setId(TutoriaLlave id) {
    this.id = id;
  }

  public TutoriaLlave getId() {
    return this.id;
  }

  public Integer getHoras() {
    return horas;
  }

  public void setHoras(Integer horas) {
    this.horas = horas;
  }

  @Override
  public String toString() {
    return (
      "{" +
      "id:" +
      "{ 'id_alumno':" +
        this.id.getIdAlumno() +
      "'id_profesor': " +
        this.id.getIdProfesor() +
      "}" +
      "'horas': " +
      this.horas +
      "}"
    );
  }
}
