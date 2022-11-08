package ar.edu.utn.frba.dds.impactoambiental;

import ar.edu.utn.frba.dds.impactoambiental.models.da.LectorDeArchivos;
import java.util.Optional;

// TODO: Preguntar si se puede usar Guice para evitar esto
public class ServiceLocator {
  private static volatile ServiceLocator instance;

  private String recomendacionesTemplate = System.getenv("RECOMENDACIONES_TEMPLATE");
  private String recomendacionesUrl = System.getenv("RECOMENDACIONES_URL");
  private String smtpUser = System.getenv("SMTP_USER");
  private String smtpPassword = System.getenv("SMTP_PASSWORD");
  private LectorDeArchivos weakPasswordsFile = new LectorDeArchivos(
      Optional.ofNullable(System.getenv("WEAK_PASSWORDS_FILE"))
          .orElse("target/classes/weak_passwords.txt"));
  private String whatsappApiKey = System.getenv("WHATSAPP_API_KEY");
  private String whatsappApiId = System.getenv("WHATSAPP_API_ID");

  protected ServiceLocator() {
  }

  public static ServiceLocator getServiceLocator() {
    if (instance == null) {
      synchronized (ServiceLocator.class) {
        if (instance == null) {
          instance = new ServiceLocator();
        }
      }
    }
    return instance;
  }

  public static void setServiceLocator(ServiceLocator instance) {
    synchronized (ServiceLocator.class) {
      ServiceLocator.instance = instance;
    }
  }

  public String getRecomendacionesTemplate() {
    return recomendacionesTemplate;
  }

  public String getRecomendacionesUrl() {
    return recomendacionesUrl;
  }

  public String getSmtpUser() {
    return smtpUser;
  }

  public String getSmtpPassword() {
    return smtpPassword;
  }

  public LectorDeArchivos getWeakPasswordsFileReader() {
    return weakPasswordsFile;
  }

  public String getWhatsappApiKey() {
    return whatsappApiKey;
  }

  public String getWhatsappApiId() {
    return whatsappApiId;
  }
}
