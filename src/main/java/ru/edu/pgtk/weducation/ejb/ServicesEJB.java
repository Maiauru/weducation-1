package ru.edu.pgtk.weducation.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ru.edu.pgtk.weducation.entity.FinalPractic;
import ru.edu.pgtk.weducation.entity.Practic;
import ru.edu.pgtk.weducation.entity.StudyModule;
import ru.edu.pgtk.weducation.entity.StudyPlan;
import ru.edu.pgtk.weducation.entity.Subject;

/**
 * EJB компонент для реализации различных серсов.
 *
 * @author Воронин Леонид
 */
@Stateless
public class ServicesEJB {

  @PersistenceContext(name = "weducationPU")
  private EntityManager em;
  @Inject
  private StudyPlansEJB plans;
  @Inject
  private SubjectsEJB subjects;
  @Inject
  private PracticsEJB practics;
  @Inject
  private StudyModulesEJB modules;
  @Inject
  private FinalPracticsEJB fpractics;
  
  /**
   * Копирует содержимое из другого плана.
   *
   * @param source План-источник из которого копируется содержимое.
   * @param destination План-назначение в которое копируется содержимое.
   */
  public void copyPlan(final StudyPlan source, final StudyPlan destination) {
    if (null == source) {
      throw new IllegalArgumentException("Источник для копирования плана не должен быть null!");
    }
    if (null == destination) {
      throw new IllegalArgumentException("Назначение для копирования плана не должен быть null!");
    }
    // Проверяем наличие отсутствия практик
    if (!practics.findByPlan(destination).isEmpty()) {
      throw new IllegalArgumentException("В назначении уже есть практики. Копирование невозможно!");
    }
    // Проверяем наличие отсутствия модулей
    if (!modules.fetchAll(destination).isEmpty()) {
      throw new IllegalArgumentException("В назначении уже есть модули. Копирование невозможно!");
    }
    // Проверяем наличие отсутствия дисциплин
    if (!subjects.fetchAll(destination).isEmpty()) {
      throw new IllegalArgumentException("В назначении уже есть дисциплины. Копирование невозможно!");
    }
    // Проверяем наличие отсутствия итоговых практик
    if (!fpractics.fetchAll(destination).isEmpty()) {
      throw new IllegalArgumentException("В назначении уже есть итоговые практики. Копирование невозможно!");
    }
    // Проверяем наличие дисциплин
    if (subjects.fetchAll(source).isEmpty()) {
      throw new IllegalArgumentException("В источнике отсутствуют дисциплины. Копирование невозможно!");
    }
    // Проверяем наличие отсутствия итоговых практик
    if (fpractics.fetchAll(source).isEmpty()) {
      throw new IllegalArgumentException("В отсутствуют итоговые практики. Копирование невозможно!");
    }
    // Копируем итоговые практики
    for (FinalPractic fp: fpractics.fetchAll(source)) {
      FinalPractic copy = new FinalPractic(fp);
      copy.setPlan(destination);
      fpractics.save(copy);
    }
    // Копируем модули
    for (StudyModule sm: modules.fetchAll(source)) {
      StudyModule copy = new StudyModule(sm);
      copy.setPlan(destination);
      modules.save(copy);
      modules.copy(sm, copy);
    }
    // Копируем дисциплины без модулей
    for (Subject s: subjects.fetchNoModules(source)) {
      Subject copy = new Subject(s);
      copy.setModule(null);
      copy.setPlan(destination);
      subjects.save(copy);
      subjects.copy(s, copy);
    }
  }
}