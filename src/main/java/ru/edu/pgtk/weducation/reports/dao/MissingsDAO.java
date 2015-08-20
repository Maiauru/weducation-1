package ru.edu.pgtk.weducation.reports.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import ru.edu.pgtk.weducation.entity.GroupSemester;
import ru.edu.pgtk.weducation.entity.StudyCard;
import ru.edu.pgtk.weducation.reports.entity.ReportMissing;

@Stateless
public class MissingsDAO {

  @PersistenceContext(unitName = "weducationPU")
  private EntityManager em;

  public ReportMissing getMonthMissings(final StudyCard card, int year, int month) {
    Query query = em.createNativeQuery(
      "SELECT wmv_crdcode AS mis_crdcode, SUM(wmv_legal) AS mis_legal, SUM(wmv_illegal) AS mis_illegal "
      + "FROM missingsView WHERE (wmv_crdcode = ?1) AND (wmv_year = ?2) AND (wmv_month = ?3) GROUP BY wmv_crdcode;", "missingSummary");
    query.setParameter(1, card.getId());
    query.setParameter(2, year);
    query.setParameter(3, month);
    return (ReportMissing) query.getSingleResult();
  }

  public ReportMissing getSemesterMissings(final StudyCard card, GroupSemester semester) {
    Query query = em.createNativeQuery(
      "SELECT wmv_crdcode AS mis_crdcode, SUM(wmv_legal) AS mis_legal, SUM(wmv_illegal) AS mis_illegal "
      + "FROM missingsView WHERE (wmv_crdcode = ?1) AND (wmv_date >= ?2) AND (wmv_date <= ?3) GROUP BY wmv_crdcode;", "missingSummary");
    query.setParameter(1, card.getId());
    query.setParameter(2, semester.getBeginDate());
    query.setParameter(3, semester.getEndDate());
    return (ReportMissing) query.getSingleResult();
  }
}
