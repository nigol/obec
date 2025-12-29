package cz.nigol.obec.beans;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import javax.inject.*;
import javax.enterprise.context.RequestScoped;
import javax.annotation.*;
import cz.nigol.obec.entities.*;
import cz.nigol.obec.services.*;

@Named
@RequestScoped
public class PhotoGalleryShowBean {
    @Inject
    private HelpOfferService helpOfferService;
	@Inject
    	private PhotoGalleryService photoGalleryService;
	private List<PhotoGalleryItem> photos;
	private String gallery = "";

    @PostConstruct
    public void init() {
		photos = photoGalleryService.getAllGalleryItems();
		gallery = photos.stream()
         		.map(p -> "{src: '" + p.getUrl() + "',cap: '" + p.getLabel() + "'}")
         		.collect(Collectors.joining(", "));
    }

    public String getGallery() {
        return gallery;
    }
}