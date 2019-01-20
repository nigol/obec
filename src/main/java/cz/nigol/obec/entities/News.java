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

@NamedQueries({
	@NamedQuery(name=News.GET_ALL, query="SELECT n FROM News n ORDER BY n.changedAt DESC"),
    })
@Entity
@Table(name="OB_NEWS")
public class News implements Serializable {
    private static final long serialVersionUID = -4378072789330339015L;

    public static final String GET_ALL = "News.GET_ALL";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    private long id;

    @Column(name="LABEL", columnDefinition="VARCHAR(300)")
    private String label;

    @Column(name="CHANGED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date changedAt;

    @OneToOne(cascade=CascadeType.ALL)
    private Article article;

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
     * @return the changedAt
     */
    public Date getChangedAt() {
	return changedAt;
    }

    /**
     * @param changedAt the changedAt to set
     */
    public void setChangedAt(Date changedAt) {
	this.changedAt = changedAt;
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
