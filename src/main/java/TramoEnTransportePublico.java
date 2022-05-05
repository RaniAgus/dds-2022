import static java.util.Objects.requireNonNull;
public class TramoEnTransportePublico extends Tramo{
  Linea linea;
  public TramoEnTransportePublico(Linea linea, TipoDeTransportePublico tipo){
    this.linea = requireNonNull(linea,"No se puede crear un tramo en transporte publico sin linea");
  }



}
