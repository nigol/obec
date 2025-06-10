package cz.nigol.obec.beans.admin;

import java.io.*;
import java.util.*;
import javax.inject.*;
import javax.faces.view.*;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.annotation.*;
import cz.nigol.obec.entities.*;
import cz.nigol.obec.services.*;

@Named
@ViewScoped
public class CouncilMeetingAdminBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private CouncillorService councillorService;
    @Inject
    private FacesContext facesContext;
    private List<ElectionPeriod> electionPeriods;
    private ElectionPeriod electionPeriod;
    private List<Councillor> councillors;
    private List<CouncilMeeting> councilMeetings;
    private CouncilMeeting councilMeeting;
    private List<Councillor> selectedCouncillors;

    @PostConstruct
    public void init() {
        electionPeriods = councillorService.getAllElectionPeriods();    
        if (electionPeriods.size() != 0) {
            electionPeriod = electionPeriods.get(0);     
            councillors = electionPeriod.getCouncillors();
        }
        councilMeetings = councillorService.getAllCouncilMeetings();
    }

    public void newCouncilMeeting() {
        councilMeeting = new CouncilMeeting();
        councilMeetings.add(councilMeeting);
    }

    public void onCouncilMeetingSelect() {
        electionPeriod = councilMeeting.getElectionPeriod();
        selectedCouncillors = councilMeeting.getCouncillors();
    }

    public void saveCouncilMeeting() {
        councilMeeting.setElectionPeriod(electionPeriod);
        councilMeeting.setCouncillors(selectedCouncillors);
        councillorService.saveCouncilMeeting(councilMeeting, selectedCouncillors);
        councilMeeting = null;
        selectedCouncillors = new ArrayList<>();
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Zasedání bylo uloženo."));
    }

    public void cancelEdit() {
        councilMeeting = null;
        init();     
    }

    public List<Councillor> getSelectedCouncillors() {
        return selectedCouncillors;
    }

    public void setSelectedCouncillors(List<Councillor> selectedCouncillors) {
        this.selectedCouncillors = selectedCouncillors;
    }

    public CouncilMeeting getCouncilMeeting() {
        return councilMeeting;
    }

    public void setCouncilMeeting(CouncilMeeting councilMeeting) {
        this.councilMeeting = councilMeeting;
    }

    public List<CouncilMeeting> getCouncilMeetings() {
        return councilMeetings;
    }

    public void setCouncilMeetings(List<CouncilMeeting> councilMeetings) {
        this.councilMeetings = councilMeetings;
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

    public List<ElectionPeriod> getElectionPeriods() {
        return electionPeriods;     
    }

    public void setElectionPeriods(List<ElectionPeriod> electionPeriods) {
        this.electionPeriods = electionPeriods;     
    }
}
