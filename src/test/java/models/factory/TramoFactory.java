package models.factory;

import models.miembro.Tramo;
import models.mediodetransporte.BicicletaOPie;
import models.miembro.TramoEnTransportePublico;
import models.miembro.TramoPrivado;

import static org.mockito.Mockito.mock;

public class TramoFactory {
  public static TramoPrivado tramoAPieDesdeMedranoHastaAlem(){
    return mock(TramoPrivado.class);
  }
  public static TramoEnTransportePublico tramoEnSubteDesdeMedranoHastaAlem() {
    return  mock(TramoEnTransportePublico.class);
  }
}
