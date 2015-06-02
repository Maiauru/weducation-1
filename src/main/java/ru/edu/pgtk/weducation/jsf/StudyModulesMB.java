package ru.edu.pgtk.weducation.jsf;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import ru.edu.pgtk.weducation.ejb.StudyModulesEJB;
import ru.edu.pgtk.weducation.ejb.StudyPlansEJB;
import ru.edu.pgtk.weducation.entity.StudyModule;
import ru.edu.pgtk.weducation.entity.StudyPlan;
import ru.edu.pgtk.weducation.entity.ExamForm;
import static ru.edu.pgtk.weducation.jsf.Utils.addMessage;

@ManagedBean(name = "studyModulesMB")
@ViewScoped
public class StudyModulesMB extends GenericBean<StudyModule> implements Serializable {

  @EJB
  private transient StudyModulesEJB ejb;
  @EJB
  private transient StudyPlansEJB planEJB;

  private StudyPlan plan = null;
  private int planCode;

  public StudyPlan getPlan() {
    return plan;
  }

  public int getPlanCode() {
    return planCode;
  }

  public void setPlanCode(int planCode) {
    this.planCode = planCode;
  }

  public void loadPlan() {
    try {
      if (planCode > 0) {
        plan = planEJB.get(planCode);
      } else {
        addMessage("Wrond StudyPlan identifier " + planCode);
      }
    } catch (Exception e) {
      addMessage(e);
    }
  }

  public List<StudyModule> getStudyModules() {
    return ejb.fetchAll(plan);
  }

  public ExamForm[] getExamForms() {
    return ExamForm.values();
  }

  @Override
  public void newItem() {
    item = new StudyModule();
    item.setPlan(plan);
  }

  @Override
  public void deleteItem() {
    if ((null != item) && delete) {
      ejb.delete(item);
    }
  }

  @Override
  public void saveItem() {
    ejb.save(item);
  }
}
