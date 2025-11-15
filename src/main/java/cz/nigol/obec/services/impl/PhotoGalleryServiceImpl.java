package cz.nigol.obec.services.impl;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.*;

import cz.nigol.obec.entities.*;
import cz.nigol.obec.services.*; 

@Stateless
public class PhotoGalleryServiceImpl implements PhotoGalleryService {
	@PersistenceContext(unitName="obecPU")
	private EntityManager em;

	@Override
	public PhotoGalleryItem findById(long id) {
		return em.find(PhotoGalleryItem.class, id);
	}

	@Override
	public PhotoGalleryItem save(PhotoGalleryItem galleryItem) {
		return em.merge(galleryItem);
	}

	@Override
	public List<PhotoGalleryItem> getAllGalleryItems() {
		TypedQuery<PhotoGalleryItem> typedQuery = em.createNamedQuery(PhotoGalleryItem.GET_ALL, PhotoGalleryItem.class);
		return new ArrayList<>(typedQuery.getResultList());
	}  
}
