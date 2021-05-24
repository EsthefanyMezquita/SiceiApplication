package mx.uady.sicei.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TutoriaLlave implements Serializable {

  @Column(name="id_alumno")
  private Integer id_alumno;

  @Column(name="id_profesor")
  private Integer id_profesor;

  public TutoriaLlave() {}

  public TutoriaLlave(Integer id_alumno, Integer id_profesor) {
    this.id_alumno = id_alumno;
    this.id_profesor = id_profesor;
  }

  public void setid_alumno(Integer id_alumno) {
    this.id_alumno = id_alumno;
  }

  public Integer getid_alumno() {
    return this.id_alumno;
  }

  public void setid_profesor(Integer id_profesor) {
    this.id_profesor = id_profesor;
  }

  public Integer getid_profesor() {
    return this.id_profesor;
  }
}
