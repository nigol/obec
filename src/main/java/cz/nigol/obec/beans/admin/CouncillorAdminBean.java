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
public class CouncillorAdminBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private CouncillorService councillorService;
    @Inject
    private FacesContext facesContext;
    @Inject
    private UserService userService;
    private List<ElectionPeriod> electionPeriods;
    private ElectionPeriod electionPeriod;
    private List<Councillor> councillors;
    private Councillor councillor;
    private User user;
    private int rowsPerPage = 5;

    @PostConstruct
    public void init() {
        electionPeriods = councillorService.getAllElectionPeriods();    
        if (electionPeriods.size() != 0) {
            electionPeriod = electionPeriods.get(0);
            rowsPerPage = electionPeriod.getCouncillors().size();
        }
        councillors = councillorService.getAllCouncillors();
    }

    public void newCouncillor() {
        councillor = new Councillor();
        councillors.add(councillor);     
    }

    public void onCouncillorSelect() {
        user = councillor.getUser();
        electionPeriod = councillor.getElectionPeriod();
    }

    public void saveCouncillor() {
        councillor.setUser(user);
        councillor.setElectionPeriod(electionPeriod);
        councillorService.saveCouncillor(councillor);
        councillor = null;
        user = null;
        init();
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Zastupitel byl uložen."));
    }

    public void cancelEdit() {
        councillor = null;
        user = null;
        init();     
    }

    public List<User> completeUser(String query) {
        return userService.findUsersByFullName(query);     
    }
    
    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public User getUser() {
        return user;     
    }

    public void setUser(User user) {
        this.user = user;     
    }

    public Councillor getCouncillor() {
        return councillor;     
    }

    public void setCouncillor(Councillor councillor) {
        this.councillor = councillor;     
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