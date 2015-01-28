package ru.edu.pgtk.weducation.ejb;

import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import ru.edu.pgtk.weducation.entity.School;

@Stateless
@Named("schoolsEJB")
public class SchoolsEJB {
  
  @PersistenceContext(unitName = "weducationPU")
  private EntityManager em;
  
  public School get(final int id) {
    School result = em.find(School.class, id);
    if (null != result) {
      return result;
    }
    throw new EJBException("School not found with id " + id);
  }
  
  public List<School> fetchAll() {
    TypedQuery<School> q = em.createQuery(
            "SELECT s FROM School s ORDER BY s.shortName", School.class);
    return q.getResultList();
  }
  
  public School save(School item) {
    if (item.getId() == 0) {
      em.persist(item);
      return item;
    } else {
      return em.merge(item);
    }
  }
  
  public void delete(final School item) {
    School s = em.find(School.class, item.getId());
    if (null != s) {
      em.remove(s);
    }
  }
}