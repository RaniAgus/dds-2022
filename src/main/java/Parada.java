import static java.util.Objects.requireNonNull;
public class Parada {
  private String nombre;
  private Ubicacion ubicacion;

  public Parada(String nombre, Ubicacion ubicacion) {
    this.nombre = requireNonNull(nombre,"No se puede crear una parada sin nombre");
    this.ubicacion = requireNonNull(ubicacion,"Toda parada necesita tener una ubicacion");
  }
}
