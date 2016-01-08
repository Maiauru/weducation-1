package ru.edu.pgtk.weducation.ejb;

import ru.edu.pgtk.weducation.entity.Department;
import ru.edu.pgtk.weducation.entity.Speciality;
import ru.edu.pgtk.weducation.entity.StudyPlan;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * EJB для организации функционала для учебных планов.
 * @author Воронин Леонид
 */
@Stateless
@Named("studyPlansEJB")
public class StudyPlansEJB {

	@PersistenceContext(unitName = "weducationPU")
	private EntityManager em;

	public StudyPlan get(final int id) {
		StudyPlan result = em.find(StudyPlan.class, id);
		if (null != result) {
			return result;
		}
		throw new EJBException("StudyPlan not found with id " + id);
	}

	public List<StudyPlan> fetchAll() {
		TypedQuery<StudyPlan> q = em.createQuery("SELECT sp FROM StudyPlan sp ORDER BY sp.speciality.name, sp.beginYear DESC", StudyPlan.class);
		return q.getResultList();
	}

	public List<StudyPlan> findBySpeciality(final Speciality spc) {
		TypedQuery<StudyPlan> q = em.createQuery(
				"SELECT sp FROM StudyPlan sp WHERE (sp.speciality = :spec) ORDER BY sp.beginYear DESC", StudyPlan.class);
		q.setParameter("spec", spc);
		return q.getResultList();
	}

	public List<StudyPlan> findBySpeciality(final Speciality spc, final boolean extramural) {
		TypedQuery<StudyPlan> q = em.createQuery(
				"SELECT sp FROM StudyPlan sp WHERE (sp.speciality = :spec) AND (sp.extramural = :em) ORDER BY sp.beginYear DESC", StudyPlan.class);
		q.setParameter("spec", spc);
		q.setParameter("em", extramural);
		return q.getResultList();
	}

  public List<StudyPlan> findByDepartment(final Department dep) {
    TypedQuery<StudyPlan> q = em.createQuery(
      "SELECT sp FROM StudyPlan sp "
      + "WHERE (sp.speciality.id IN (SELECT dp.speciality.id FROM DepartmentProfile dp WHERE (dp.department = :dep)))"
      + " AND (sp.extramural IN (SELECT dp.extramural FROM DepartmentProfile dp WHERE (dp.department = :dep))) "
      + "ORDER BY sp.speciality.name, sp.beginYear DESC", StudyPlan.class);
    q.setParameter("dep", dep);
    return q.getResultList();
  }

  public StudyPlan save(StudyPlan item) {
    if (item.getSpecialityCode() > 0) {
      Speciality spc = em.find(Speciality.class, item.getSpecialityCode());
      if (null != spc) {
        item.setSpeciality(spc);
      } else {
        throw new EJBException("Wrong Speciality code " + item.getSpecialityCode());
      }
    }
    if (item.getId() == 0) {
      em.persist(item);
      return item;
    } else {
      return em.merge(item);
    }
  }

	public void delete(final StudyPlan item) {
		StudyPlan sp = em.find(StudyPlan.class, item.getId());
		if (null != sp) {
			em.remove(sp);
		}
	}
}
