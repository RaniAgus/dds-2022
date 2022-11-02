package ar.edu.utn.frba.dds.impactoambiental.models.usuario;

import ar.edu.utn.frba.dds.impactoambiental.dtos.UsuarioDto;
import ar.edu.utn.frba.dds.impactoambiental.models.da.LectorDeArchivos;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("10milcontrasenas")
public class Validar10MilContrasenas extends ValidacionDeUsuario {
  @Transient
  private final LectorDeArchivos lectorDeArchivos;

  public Validar10MilContrasenas() {
   lectorDeArchivos=null;
  }

  public Validar10MilContrasenas(LectorDeArchivos lectorDeArchivos) {
    this.lectorDeArchivos = lectorDeArchivos;
  }


  @Override
  public boolean test(UsuarioDto dto) {
    return !lectorDeArchivos.getLineas().contains(dto.getPassword());
  }

  @Override
  public String getMensajeDeError() {
    return "Contraseña dentro de las 10000 mas usadas. Elija otra por favor.";
  }
}
