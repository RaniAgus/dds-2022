import static java.util.Objects.requireNonNull;
public class TramoEnTransportePublico extends Tramo {
  private Linea linea;

  public TramoEnTransportePublico(Ubicacion ubicacionInicial,
                                  Ubicacion ubicacionFinal,
                                  Linea linea) {
    super(ubicacionInicial, ubicacionFinal);
    this.linea = requireNonNull(linea,"No se puede crear un tramo en transporte publico sin linea");
  }

}
