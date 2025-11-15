package cz.nigol.obec.services;

import java.util.List;

import cz.nigol.obec.entities.*;

public interface PhotoGalleryService {
	List<PhotoGalleryItem> getAllGalleryItems();
	PhotoGalleryItem findById(long id);
	PhotoGalleryItem save(PhotoGalleryItem galleryItem);
}
