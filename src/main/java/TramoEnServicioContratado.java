import static java.util.Objects.requireNonNull;
public class TramoEnServicioContratado extends Tramo{
  private String nombre;

  public TramoEnServicioContratado(String nombre) {
    this.nombre = requireNonNull(nombre,"No se puede instanciar un Tramo en servicio contratado sin nombre");
  }

  public static final TramoEnServicioContratado Taxi = new TramoEnServicioContratado("Taxi");
  public static final TramoEnServicioContratado Remis = new TramoEnServicioContratado("Remis");
}
