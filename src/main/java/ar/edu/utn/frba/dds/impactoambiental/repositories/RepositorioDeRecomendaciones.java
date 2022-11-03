package ar.edu.utn.frba.dds.impactoambiental.repositories;

import ar.edu.utn.frba.dds.impactoambiental.models.notificaciones.Recomendacion;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioDeRecomendaciones implements Repositorio<Recomendacion> {
    private static final RepositorioDeRecomendaciones  instance = new RepositorioDeRecomendaciones ();

    public static RepositorioDeRecomendaciones getInstance() {
        return instance;
    }

    @Override
    public Class<Recomendacion> clase() {
        return Recomendacion.class;
    }

    public List<Recomendacion> obtenerLasDiezMasCercanas(){
        return obtenerTodos()
                .stream()
                .sorted(Comparator.comparing(Recomendacion::getFechaRecomendacion))
                .limit(10)
                .collect(Collectors.toList());
    }
}
