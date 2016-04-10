package ru.edu.pgtk.weducation.ejb;

import ru.edu.pgtk.weducation.entity.CourseWorkMark;
import ru.edu.pgtk.weducation.entity.StudyCard;
import ru.edu.pgtk.weducation.entity.StudyGroup;
import ru.edu.pgtk.weducation.entity.Subject;

import java.util.List;

/**
 * Интерфейс корпоративного компонента для оценок за курсовые проекты
 * @author Voronin Leonid
 * @since 09.04.2016
 */
public interface CourseWorkMarksDAO extends WeakEntityDAO<CourseWorkMark> {

	CourseWorkMark get(final StudyCard card, final Subject subject, final int course, final int semester);

	List<CourseWorkMark> fetchAll(final StudyGroup group, final Subject subject, final int course, final int semester);

	List<CourseWorkMark> fetchAll(final StudyCard card);
}