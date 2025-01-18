package cz.nigol.obec.entities;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

@NamedQueries({
    @NamedQuery(name=WaterSpending.GET_ALL, query="SELECT w FROM WaterSpending w ORDER BY w.houseNumber"),
    @NamedQuery(name=WaterSpending.GET_BY_PERIOD, query="SELECT w FROM WaterSpending w WHERE w.period = :period ORDER BY w.houseNumber"),
    @NamedQuery(name=WaterSpending.GET_PERIODS, query="SELECT w.period FROM WaterSpending w GROUP BY w.period"),
})
@Entity
@Table(name="OB_WATER_SPENDING")
public class WaterSpending implements Serializable {
    private static final long serialVersionUID = -4378072789330339015L;

    public static final String GET_ALL = "WaterSpending.GET_ALL";
    public static final String GET_BY_PERIOD = "WaterSpending.GET_BY_PERIOD";
    public static final String GET_PERIODS = "WaterSpending.GET_PERIODS";
    
    public static final String PERIOD_PARAM = "period";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    private long id;

    @Column(name="PLACE")
    private String place;

    @Column(name="HOUSE_NUMBER")
    private String houseNumber;

    @Column(name="PERSONS")
    private int persons;

    @Column(name="NAME")
    private String name;

    @Column(name="CUBIC_METERS")
    private int cubicMeters;

    @Column(name="CONTACT")
    private String contact;

    @Column(name="PERIOD")
    private String period;

    @Column(name="CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getCubicMeters() {
        return cubicMeters;
    }

    public void setCubicMeters(int cubicMeters) {
        this.cubicMeters = cubicMeters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPersons() {
        return persons;
    }

    public void setPersons(int persons) {
        this.persons = persons;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public long getId() {
        return id;     
    }

    public void setId(long id) {
        this.id = id;     
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WaterSpending)) return false;
        WaterSpending waterSpending = (WaterSpending) o;
        return id == waterSpending.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}