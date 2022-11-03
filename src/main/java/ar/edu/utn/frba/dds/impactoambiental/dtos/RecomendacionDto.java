package ar.edu.utn.frba.dds.impactoambiental.dtos;

import ar.edu.utn.frba.dds.impactoambiental.models.notificaciones.Recomendacion;

public class RecomendacionDto {
    private String titulo;
    private String descripcion;
    private String linkRecomendacion;

    public RecomendacionDto(String titulo, String descripcion, String linkRecomendacion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.linkRecomendacion = linkRecomendacion;
    }

    public static RecomendacionDto fromRecomendacion(Recomendacion recomendacion) {
        return new RecomendacionDto(recomendacion.getTitulo(), recomendacion.getDescripcion(), recomendacion.getLink());
    }
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLinkRecomendacion() {
        return linkRecomendacion;
    }

    public void setLinkRecomendacion(String linkRecomendacion) {
        this.linkRecomendacion = linkRecomendacion;
    }
}
