import java.util.List;
import static java.util.Objects.requireNonNull;

public class Linea {
  private List<Parada> paradas;
  private TipoDeTransportePublico tipo;
  private String nombre;
  public Linea(List<Parada> paradas, TipoDeTransportePublico tipo){
    this.paradas = paradas;
    this.tipo = requireNonNull(tipo,"No se puede crear un tramo en transporte publico sin tipo");
  }
  public void agregarParada(Parada nuevaParada){
    this.paradas.add(nuevaParada);
  }

}
