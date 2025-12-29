package cz.nigol.obec.beans.admin;

import java.io.*;
import java.util.*;
import javax.inject.*;
import javax.faces.view.*;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.annotation.*;
import cz.nigol.obec.entities.*;
import cz.nigol.obec.services.*;
import org.primefaces.event.*;

@Named
@ViewScoped
public class PhotoGalleryBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
	private PhotoGalleryService photoGalleryService;
	@Inject
	private FacesContext facesContext;
	private List<PhotoGalleryItem> galleryItems;

	@PostConstruct
	public void init() {
		galleryItems = photoGalleryService.getAllGalleryItems();
	}
	
	public void newGalleryItem() {
		PhotoGalleryItem galleryItem = new PhotoGalleryItem();
		galleryItem.setLabel("Nový popis");
		galleryItem.setUrl("https://");
		photoGalleryService.save(galleryItem);
		facesContext.addMessage(null, new FacesMessage		(FacesMessage.SEVERITY_INFO, "", "Položka byla uložena."));
		init();
	}
	
	public void saveOnEdit(PhotoGalleryItem item) {
		photoGalleryService.save(item);
		facesContext.addMessage(null, new FacesMessage		(FacesMessage.SEVERITY_INFO, "", "Položka byla uložena."));
		init();
	}

	public List<PhotoGalleryItem> getGalleryItems() {
		return galleryItems;
	}
}