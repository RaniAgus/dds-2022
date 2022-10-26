package ar.edu.utn.frba.dds.impactoambiental.models.notificaciones;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Notificador  extends EntidadPersistente {
  public abstract void enviarGuiaRecomendacion(Contacto contacto, String link);
}
