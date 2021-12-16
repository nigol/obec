package cz.nigol.obec.services.impl;

import java.util.*;
import javax.ejb.*;
import javax.persistence.*;
import cz.nigol.obec.entities.*;
import cz.nigol.obec.services.*;

@Stateless
public class CouncillorServiceImpl implements CouncillorService {
    @PersistenceContext(unitName="obecPU")
    private EntityManager em;

    @Override
    public List<Councillor> getAllCouncillors() {
        TypedQuery<Councillor> typedQuery = em.createNamedQuery(Councillor.GET_ALL, Councillor.class);
        return new ArrayList<>(typedQuery.getResultList());
    }
    
    @Override
    public List<CouncilMeeting> getAllCouncilMeetings() {
        TypedQuery<CouncilMeeting> typedQuery = em.createNamedQuery(CouncilMeeting.GET_ALL, CouncilMeeting.class);
        return new ArrayList<>(typedQuery.getResultList());
    }
    
    @Override
    public List<ElectionPeriod> getAllElectionPeriods() {
        TypedQuery<ElectionPeriod> typedQuery = em.createNamedQuery(ElectionPeriod.GET_ALL, ElectionPeriod.class);
        return new ArrayList<>(typedQuery.getResultList());
    }

    @Override
    public void saveCouncillor(Councillor councillor) {
        em.merge(councillor);     
    }

    @Override
    public void saveCouncilMeeting(CouncilMeeting councilMeeting, List<Councillor> councillors) {
        CouncilMeeting cm = em.merge(councilMeeting);     
        cm.getCouncillors().clear();
        cm.getCouncillors().addAll(councillors);
    }

    @Override
    public void saveElectionPeriod(ElectionPeriod electionPeriod) {
        em.merge(electionPeriod);     
    }

    @Override
    public Councillor findCouncillorById(long id) {
        return em.find(Councillor.class, id);    
    }

    @Override
    public ElectionPeriod findElectionPeriodById(long id) {
        return em.find(ElectionPeriod.class, id);    
    }

    @Override
    public Councillor getCouncillorByUser(User user) {
        TypedQuery<Councillor> typedQuery = em.createNamedQuery(Councillor.GET_BY_USER, Councillor.class);
        typedQuery.setParameter(Councillor.USER_PARAM, user);
        List<Councillor> councillors = typedQuery.getResultList();
        return councillors.isEmpty() ? null : councillors.get(0);
    }
    
    @Override
    public List<CouncilMeeting> getAllCouncilMeetingsFor(Councillor councillor) {
        TypedQuery<CouncilMeeting> typedQuery = em.createNamedQuery(CouncilMeeting.GET_BY_DATE_RANGE, CouncilMeeting.class);
        typedQuery.setParameter(CouncilMeeting.START_DATE_PARAM, councillor.getStartDate());
        typedQuery.setParameter(CouncilMeeting.END_DATE_PARAM, councillor.getEndDate() == null ? new Date() : councillor.getEndDate());
        return typedQuery.getResultList();
    }
}