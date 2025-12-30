package cz.nigol.obec.entities;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "OB_SETTINGS")
public class Settings implements Serializable {
    private static final long serialVersionUID = -3262642900499913785L;

    @Id
    @Column(name="ID")
    private String id;

    @Column(name="BASE_URL")
    private String baseUrl;

    @Column(name="OG_IMAGE_URL")
    private String ogImageUrl;

    @Column(name="IMPORTANT_MSG")
    private String importantMsg;

    @Column(name="IMPORTANT_MSG_URL")
    private String importantMsgUrl;

    @Column(name="HEADER_IMAGE_URL")
    private String headerImageUrl;
    
    @Column(name="WATER_SPEND_PERIOD")
    private String waterSpendPeriod;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
    @OrderBy("id DESC")
    private List<PhotoGalleryItem> galleryItems;
    
    public List<PhotoGalleryItem> getGalleryItems() {
        return galleryItems;
    }
    
    public void setGalleryItems(List<PhotoGalleryItem> galleryItems) {
        this.galleryItems = galleryItems;
    }
    
    public String getWaterSpendPeriod() {
        return waterSpendPeriod;
    }
    
    public void setWaterSpendPeriod(String waterSpendPeriod) {
        this.waterSpendPeriod = waterSpendPeriod;
    }

    public String getHeaderImageUrl() {
        return headerImageUrl;
    }

    public void setHeaderImageUrl(String headerImageUrl) {
        this.headerImageUrl = headerImageUrl;
    }

    public String getImportantMsgUrl() {
        return importantMsgUrl;
    }

    public void setImportantMsgUrl(String importantMsgUrl) {
        this.importantMsgUrl = importantMsgUrl;
    }

    public String getImportantMsg() {
        return importantMsg;
    }

    public void setImportantMsg(String importantMsg) {
        this.importantMsg = importantMsg;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the baseUrl
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * @param baseUrl the baseUrl to set
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * @return the ogImageUrl
     */
    public String getOgImageUrl() {
        return ogImageUrl;
    }

    /**
     * @param ogImageUrl the ogImageUrl to set
     */
    public void setOgImageUrl(String ogImageUrl) {
        this.ogImageUrl = ogImageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Settings)) return false;
        Settings settings = (Settings) o;
        return id != null && id.equals(settings.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
