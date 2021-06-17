package mx.uady.sicei.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tutorias")
public class Tutoria {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @OneToOne
  @JoinColumn(name = "id_alumno")
  private Alumno alumno;

  @OneToOne
  @JoinColumn(name = "id_profesor")
  private Profesor profesor;

  @Column(name = "horas")
  private Integer horas;

  public Tutoria() {}

  public Tutoria(Integer horas) {
    this.horas = horas;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return this.id;
  }

  public Integer getHoras() {
    return horas;
  }

  public void setHoras(Integer horas) {
    this.horas = horas;
  }

  public Alumno getAlumno() {
    return this.alumno;
  }

  public void setAlumno(Alumno alumno) {
    this.alumno = alumno;
  }

  public Profesor getProfesor() {
    return this.profesor;
  }

  public void setProfesor(Profesor profesor) {
    this.profesor = profesor;
  }

  @Override
  public String toString() {
    return ( "{" + this.getAlumno() + "," + this.getProfesor() + "," + this.horas + "}");
  }
}