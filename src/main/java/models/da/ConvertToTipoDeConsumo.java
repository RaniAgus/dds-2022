package models.da;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class ConvertToTipoDeConsumo  extends AbstractBeanField<TipoDeConsumo, String>{

  @Override
  protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
     return TipoDeConsumo.valueOf(s.replace(' ', '_'));
  }
}