package ar.edu.utn.frba.dds.impactoambiental.models.notificaciones;

public class WhatsAppError {
  private ErrorMessage error;

  public String getErrorMessage() {
    return error.getMessage();
  }

  public static class ErrorMessage {
    private String message;
    private String type;
    private Integer code;
    private Integer error_subcode;
    private String fbtrace_id;

    public String getMessage() {
      return String.format("%s: %s", type, message);
    }
  }
}
