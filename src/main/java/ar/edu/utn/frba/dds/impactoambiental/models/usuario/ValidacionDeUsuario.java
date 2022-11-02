package ar.edu.utn.frba.dds.impactoambiental.models.usuario;

import ar.edu.utn.frba.dds.impactoambiental.controllers.validaciones.Validacion;
import ar.edu.utn.frba.dds.impactoambiental.dtos.UsuarioDto;
import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ValidacionDeUsuario extends EntidadPersistente implements Validacion<UsuarioDto> {
}
