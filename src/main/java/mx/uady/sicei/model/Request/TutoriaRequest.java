package mx.uady.sicei.model.request;

import javax.validation.constraints.NotNull;

import mx.uady.sicei.model.TutoriaLlave;

public class TutoriaRequest {

  @NotNull
  private TutoriaLlave id;

  @NotNull
  private Integer horas;

  public TutoriaRequest(){}

  public TutoriaRequest(TutoriaLlave id, Integer horas){
    this.id = id;
    this.horas = horas;
  }

  public TutoriaLlave getId() {
    return id;
  }

  public void setId(TutoriaLlave id) {
    this.id = id;
  }

  public Integer getHoras() {
    return horas;
  }

  public void setHoras(Integer horas) {
    this.horas = horas;
  }
}
