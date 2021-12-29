package cz.nigol.obec.entities;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

@NamedQueries({
    @NamedQuery(name=CouncilMeeting.GET_ALL, query="SELECT cm FROM CouncilMeeting cm ORDER BY cm.id DESC"),
    @NamedQuery(name=CouncilMeeting.GET_BY_DATE_RANGE, 
        query="SELECT cm FROM CouncilMeeting cm WHERE cm.meetingDate >= :startDate AND cm.meetingDate <= :endDate"),
})
@Entity
@Table(name="OB_COUNCIL_MEETING")
public class CouncilMeeting implements Serializable {
    private static final long serialVersionUID = -4378072789330339015L;

    public static final String GET_ALL = "CouncilMeeting.GET_ALL";
    public static final String GET_BY_DATE_RANGE = "CouncilMeeting.GET_BY_DATE_RANGE";
    
    public static final String START_DATE_PARAM = "startDate";
    public static final String END_DATE_PARAM = "endDate";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    private long id;

    @Column(name="LABEL")
    private String label;

    @ManyToOne
    @JoinColumn(name="ELECTION_PERIOD_ID")
    private ElectionPeriod electionPeriod;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
        name="OB_COUNCIL_MEETING_COUNCILLOR",
        joinColumns=@JoinColumn(name="COUNCIL_MEETING_ID", referencedColumnName="ID"),
        inverseJoinColumns=@JoinColumn(name="COUNCILLOR_ID", referencedColumnName="ID"))
    private List<Councillor> councillors;
    
    @Column(name="MEETING_DATE")
    @Temporal(TemporalType.DATE)
    private Date meetingDate;
    
    public Date getMeetingDate() {
        return this.meetingDate;
    }
    
    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    public List<Councillor> getCouncillors() {
        return councillors;     
    }

    public void setCouncillors(List<Councillor> councillors) {
        this.councillors = councillors;     
    }

    public ElectionPeriod getElectionPeriod() {
        return electionPeriod;     
    }

    public void setElectionPeriod(ElectionPeriod electionPeriod) {
        this.electionPeriod = electionPeriod;     
    }

    public String getLabel() {
        return label;     
    }

    public void setLabel(String label) {
        this.label = label;     
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
        if (!(o instanceof CouncilMeeting)) return false;
        CouncilMeeting councilMeeting = (CouncilMeeting) o;
        return id == councilMeeting.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}