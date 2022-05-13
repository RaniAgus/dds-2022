package models;

public enum EstadoVinculo {
  PENDIENTE {
    @Override
    public void aceptar(Vinculacion vinculacion) {
      vinculacion.setEstado(EstadoVinculo.ACEPTADO);
    }
    @Override
    public void rechazar(Vinculacion vinculacion) {
      vinculacion.setEstado(EstadoVinculo.RECHAZADO);
    }
  },
  ACEPTADO {
    @Override
    public void aceptar(Vinculacion vinculacion) {
      throw new IllegalStateException("El miembro ya fue aceptado.");
    }
    @Override
    public void rechazar(Vinculacion vinculacion) {
    }
  },
  RECHAZADO {
    @Override
    public void aceptar(Vinculacion vinculacion) {
    }
    @Override
    public void rechazar(Vinculacion vinculacion) {
      throw new IllegalStateException("El miembro ya fue rechazado.");
    }
  };
  abstract void aceptar(Vinculacion vinculacion);
  abstract void rechazar(Vinculacion vinculacion);
}
