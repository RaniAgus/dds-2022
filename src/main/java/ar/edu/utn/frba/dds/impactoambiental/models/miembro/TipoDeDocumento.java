package ar.edu.utn.frba.dds.impactoambiental.models.miembro;

import javax.persistence.Embeddable;

@Embeddable
public enum TipoDeDocumento {
  DNI, CUIL, CUIT, PASAPORTE
}
