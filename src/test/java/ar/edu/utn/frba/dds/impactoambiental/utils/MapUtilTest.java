package ar.edu.utn.frba.dds.impactoambiental.utils;

import static ar.edu.utn.frba.dds.impactoambiental.utils.MapUtil.mapOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;
import org.junit.jupiter.api.Test;

public class MapUtilTest {
  @Test
  public void mapOfFunciona() {
    Map<String, Object> mapa = mapOf("a", 1, "b", 2);
    assertThat(mapa).containsEntry("a", 1);
    assertThat(mapa).containsEntry("b", 2);
  }

  @Test
  public void mapOfArrojaExcepcionSiLePasanUnaCantidadDeParametrosImpar() {
    assertThatThrownBy(() -> mapOf("a", 1, "b"))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void mapOfArrojaExcepcionSiLePasanUnParametroQueNoSeaString() {
    assertThatThrownBy(() -> mapOf(1, 1, "b", 2))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void mapOfFuncionaConValoresNulos() {
    Map<String, Object> mapa = mapOf("a", 1, "b", null);
    assertThat(mapa).containsEntry("a", 1);
    assertThat(mapa).containsEntry("b", null);
  }
}
