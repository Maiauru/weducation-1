package ru.edu.pgtk.weducation.core.ejb;

import ru.edu.pgtk.weducation.core.entity.Person;
import ru.edu.pgtk.weducation.core.entity.Test;
import ru.edu.pgtk.weducation.core.entity.TestSession;

import java.util.List;

/**
 * Интерфейс корпоративного компонента для управления сеансами тестирования
 * Created by admin on 18.05.2016.
 */
public interface TesSessionsDAO extends EntityDAO<TestSession> {
    List<TestSession> fetchForTest(final Test test);

    List<TestSession> fetchFroPerson(final Person person);
}
