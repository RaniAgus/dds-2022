package models.factory;

import models.miembro.TramoPrivado;

import static org.mockito.Mockito.mock;

public class TramoFactory {
  public static TramoPrivado tramoAPieDesdeMedranoHastaAlem(){
    return mock(TramoPrivado.class);
  }
}
