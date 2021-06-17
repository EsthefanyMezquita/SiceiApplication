package mx.uady.sicei.model.Request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class TutoriaRequest {

  @NotNull
  private Integer alumnoId;
  
  @NotNull
  private Integer profesorId;

  @NotNull
  @Max(2)
  @Positive
  private Integer horas;

  public Integer getAlumnoId(){
    return alumnoId;
  }
  
  public void setAlumnoId(Integer alumnoId){
    this.alumnoId = alumnoId;
  }

  public Integer getProfesorId(){
    return profesorId;
  }
  
  public void setProfesorId(Integer profesorId){
    this.profesorId = profesorId;
  }

  public Integer getHoras() {
    return horas;
  }

  public void setHoras(Integer horas) {
    this.horas = horas;
  }
}