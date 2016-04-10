package ru.edu.pgtk.weducation.ejb;

/**
 * Шаблонизированный интерфейс сущности, для которой неактуально получение списка всех записей из БД.
 * как-правило, это "слабые" сущности, которые логически безсмысленны без сильной.
 * Например, оценка немыслима без дисциплины или студента.
 * @author Voronin Leonid
 * @since 30.03.2016
 */
interface WeakEntityDAO<T> {

	/**
	 * Получает один экземпляр по значению первичного ключа
	 * @param id значение первичного ключа
	 * @return экземпляр класса
	 */
	T get(int id);

	/**
	 * Сохраняет сущность в базу данных. Если такой сущности нет - производится добавление.
	 * Если такая сущность уже есть в базе данных - то производится обновление
	 * @param item сущность, которую надо сохранить
	 * @return обновленная сущность
	 */
	T save(T item);

	/**
	 * Удаляет сущность из базы данных.
	 * @param item сущность, которую надо удалить.
	 */
	void delete(T item);
}
