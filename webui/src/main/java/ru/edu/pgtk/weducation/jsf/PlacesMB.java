package ru.edu.pgtk.weducation.jsf;

import ru.edu.pgtk.weducation.ejb.PlacesEJB;
import ru.edu.pgtk.weducation.entity.Place;
import ru.edu.pgtk.weducation.entity.PlaceType;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named("placesMB")
public class PlacesMB extends GenericBean<Place> implements Serializable {

	long serialVersionUID = 0L;

	@EJB
	private transient PlacesEJB ejb;

	public PlaceType[] getPlaceTypes() {
		return PlaceType.values();
	}

	@Override
	public void newItem() {
		item = new Place();
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
