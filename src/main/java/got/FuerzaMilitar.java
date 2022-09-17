package got;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "FUERZAS_MILITARES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", length = 1)
public abstract class FuerzaMilitar extends PersistentEntity {
  public abstract void atacarA(Lugar lugar);
}
