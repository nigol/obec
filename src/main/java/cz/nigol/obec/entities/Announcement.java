package cz.nigol.obec.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

import cz.nigol.obec.entities.interfaces.RssItem;

@NamedQueries({
@NamedQuery(name=Announcement.GET_ALL, query="SELECT a FROM Announcement a ORDER BY a.createdAt DESC"),
})
@Entity
@Table(name="OB_ANNOUNCEMENT")
public class Announcement implements Serializable, RssItem {
    private static final long serialVersionUID = -4378072789330339015L;

    public static final String GET_ALL = "Announcement.GET_ALL";

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

    @Override
    public String getLabel() {
        return "Hlášení obecního rozhlasu, " + id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Announcement)) return false;
        Announcement announcement = (Announcement) o;
        return id == announcement.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
