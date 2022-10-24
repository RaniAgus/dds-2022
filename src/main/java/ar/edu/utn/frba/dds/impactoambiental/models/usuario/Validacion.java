package ar.edu.utn.frba.dds.impactoambiental.models.usuario;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;
import ar.edu.utn.frba.dds.impactoambiental.models.chequeos.Chequeo;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Validacion extends EntidadPersistente implements Predicate<UsuarioDto> {
  public abstract String getMensajeDeError();

  public Chequeo<UsuarioDto> asChequeo() {
    return new Chequeo<>(this, getMensajeDeError());
  }

  public static List<Chequeo<UsuarioDto>> asChequeos(List<Validacion> validaciones) {
    return validaciones.stream().map(Validacion::asChequeo).collect(Collectors.toList());
  }
}
