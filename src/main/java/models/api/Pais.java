package models.api;

public class Pais {
  private Integer id;
  private String nombre;

  public Pais(Integer id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }

  public Integer getId() {
    return id;
  }

  @Override
  public String toString() {
    return "Pais{" +
        "id=" + id +
        ", nombre='" + nombre + '\'' +
        '}';
  }
}
