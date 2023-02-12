package cz.nigol.obec.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

import cz.nigol.obec.enums.OfficialDeskCategory;
import cz.nigol.obec.entities.interfaces.RssItem;

@NamedQueries({
@NamedQuery(name=DeskItem.GET_ALL,
query="SELECT d FROM DeskItem d ORDER BY d.activeFrom DESC"),
    @NamedQuery(name=DeskItem.GET_ACTIVE_TO_DATE,
    query="SELECT d FROM DeskItem d WHERE (:date BETWEEN d.activeFrom AND d.activeTo) OR (:date >= d.activeFrom AND d.activeTo IS NULL) ORDER BY d.activeFrom DESC, d.id DESC"),
    @NamedQuery(name=DeskItem.GET_VALID_TO_DATE,
    query="SELECT d FROM DeskItem d WHERE :date >= d.activeFrom ORDER BY d.activeFrom DESC, d.id DESC"),
    @NamedQuery(name=DeskItem.GET_BY_CATEGORY,
    query="SELECT d FROM DeskItem d WHERE d.category = :category AND :date >= d.activeFrom ORDER BY d.activeFrom DESC, d.id DESC"),
})
@Entity
@Table(name="OB_DESK_ITEM")
public class DeskItem implements Serializable, RssItem {
    private static final long serialVersionUID = 8396396135912529543L;

    public static final String GET_ALL = "DeskItem.GET_ALL";
    public static final String GET_ACTIVE_TO_DATE = "DeskItem.GET_ACTIVE_TO_DATE";
    public static final String GET_VALID_TO_DATE = "DeskItem.GET_VALID_TO_DATE";
    public static final String GET_BY_CATEGORY = "DeskItem.GET_BY_CATEGORY";

    public static final String DATE_PARAM = "date";
    public static final String CATEGORY_PARAM = "category";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    private long id;

    @Column(name="CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User createdBy;

    @Column(name="BODY", columnDefinition="VARCHAR(2000)")
    private String body;

    @Column(name="ACTIVE_FROM")
    @Temporal(TemporalType.DATE)
    private Date activeFrom;

    @Column(name="ACTIVE_TO")
    @Temporal(TemporalType.DATE)
    private Date activeTo;

    @Column(name="CATEGORY")
    @Enumerated(EnumType.STRING)
    private OfficialDeskCategory category;


    @Override
    public String getLabel() {
        return "Úřední deska, " + id;
    }

    @Override
    public String getDescription() {
        return body;
    }

    @Override
    public Date getDate() {
        return createdAt;
    }

    @Override
    public String getGuid() {
        return "" + id;
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
     * @return the createdBy
     */
    public User getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * @return the activeFrom
     */
    public Date getActiveFrom() {
        return activeFrom;
    }

    /**
     * @param activeFrom the activeFrom to set
     */
    public void setActiveFrom(Date activeFrom) {
        this.activeFrom = activeFrom;
    }

    /**
     * @return the activeTo
     */
    public Date getActiveTo() {
        return activeTo;
    }

    /**
     * @param activeTo the activeTo to set
     */
    public void setActiveTo(Date activeTo) {
        this.activeTo = activeTo;
    }

    /**
     * @return the category
     */
    public OfficialDeskCategory getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(OfficialDeskCategory category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeskItem)) return false;
        DeskItem deskItem = (DeskItem) o;
        return id == deskItem.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
