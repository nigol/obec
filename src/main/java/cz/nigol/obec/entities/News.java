package cz.nigol.obec.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cz.nigol.obec.entities.interfaces.RssItem;

@NamedQueries({
@NamedQuery(name=News.GET_ALL, query="SELECT n FROM News n ORDER BY n.createdAt DESC"),
    @NamedQuery(name=News.GET_FEATURED,
    query="SELECT n FROM News n WHERE n.featured = true ORDER BY n.createdAt DESC"),
    @NamedQuery(name=News.GET_BY_DATE, 
    query="SELECT n FROM News n WHERE n.featured = true AND n.validUntil BETWEEN :startDate AND :endDate ORDER BY n.createdAt DESC"),
    @NamedQuery(name=News.GET_FEATURED_BY_DATE, 
    query="SELECT n FROM News n WHERE n.featured = true AND n.validUntil > :endDate ORDER BY n.createdAt DESC"),
})
@Entity
@Table(name="OB_NEWS")
public class News implements Serializable, RssItem {
    private static final long serialVersionUID = -4378072789330339015L;

    public static final String GET_ALL = "News.GET_ALL";
    public static final String GET_FEATURED = "News.GET_FEATURED";
    public static final String GET_BY_DATE = "News.GET_BY_DATE";
    public static final String GET_FEATURED_BY_DATE = "News.GET_FEATURED_BY_DATE";
    
    public static final String START_DATE_PARAM = "startDate";
    public static final String END_DATE_PARAM = "endDate";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    private long id;

    @Column(name="LABEL", columnDefinition="VARCHAR(300)")
    private String label;

    @Column(name="CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @OneToOne(cascade=CascadeType.ALL)
    private Article article;

    @Column(name="FEATURED")
    private boolean featured;
    
    @Column(name="VALID_UNTIL")
    @Temporal(TemporalType.DATE)
    private Date validUntil;
    
    public Date getValidUntil() {
        return validUntil;
    }
    
    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }

    public String getTruncatedLabel() {
        if (label.length() > 50) {
            return label.substring(0, 50) + "…";
        }
        return label;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the article
     */
    public Article getArticle() {
        return article;
    }

    /**
     * @param article the article to set
     */
    public void setArticle(Article article) {
        this.article = article;
    }

    /**
     * @return the featured
     */
    public boolean isFeatured() {
        return featured;
    }

    /**
     * @param featured the featured to set
     */
    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    @Override
    public String getDescription() {
        return label;
    }

    @Override
    public Date getDate() {
        return createdAt;
    }

    @Override
    public String getGuid() {
        return "" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News)) return false;
        News news = (News) o;
        return id == news.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

