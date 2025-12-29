package cz.nigol.obec.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

@NamedQueries({
@NamedQuery(name=PhotoGalleryItem.GET_ALL, query="SELECT p FROM PhotoGalleryItem p ORDER BY p.id ASC")
})
@Entity
@Table(name = "OB_PHOTOGALLERYITEM")
public class PhotoGalleryItem implements Serializable {
	private static final long serialVersionUID = -3262642900499913785L;

	public static final String GET_ALL = "PhotoGalleryItem.GET_ALL";

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private long id;

	@Column(name = "LABEL", nullable = false, length = 1024)
	private String label;

	@Column(name = "URL", nullable = false, length = 1024)
	private String url;

	// Constructors
	public PhotoGalleryItem() {
	}

	public PhotoGalleryItem(String label, String url) {
		this.label = label;
		this.url = url;
	}

	public PhotoGalleryItem(Long id, String label, String url) {
		this.id = id;
		this.label = label;
		this.url = url;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	// Optional: toString(), equals(), and hashCode()
	@Override
	public String toString() {
		return "PhotoGalleryItem{" +
				"id=" + id +
				", label='" + label + '\'' +
				", url='" + url + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PhotoGalleryItem)) return false;
		PhotoGalleryItem that = (PhotoGalleryItem) o;
		return id == that.id;
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}