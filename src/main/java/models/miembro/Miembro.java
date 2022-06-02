package models.miembro;

public class Miembro {
  private String nombre;
  private String apellido;
  private String documento;
  private TipoDeDocumento tipoDeDocumento;

  public Miembro(String nombre,
                 String apellido,
                 String documento,
                 TipoDeDocumento tipoDeDocumento) {
    this.apellido = apellido;
    this.nombre = nombre;
    this.documento = documento;
    this.tipoDeDocumento = tipoDeDocumento;
  }
}
