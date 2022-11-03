package ar.edu.utn.frba.dds.impactoambiental.models.notificaciones;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;

import javax.persistence.Entity;
import java.time.LocalDate;
@Entity
public class Recomendacion  extends EntidadPersistente {
    private LocalDate fechaRecomendacion;
    private String titulo;
    private String descripcion;
    private String link;

    public Recomendacion(LocalDate fechaRecomendacion, String titulo, String descripcion, String link) {
        this.fechaRecomendacion = fechaRecomendacion;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.link = link;
    }

    public Recomendacion() {

    }

    public LocalDate getFechaRecomendacion() {
        return fechaRecomendacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getLink() {
        return link;
    }
}
