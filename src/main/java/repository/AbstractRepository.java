package repository;

import domain.Entity;

import javax.xml.validation.Validator;
import java.util.Map;

public abstract class AbstractRepository<ID,E extends Entity<ID>> {
    protected Map<ID,E> m;
}
