package cz.nigol.obec.services.impl;

import java.util.*;
import javax.ejb.*;
import javax.persistence.*;
import cz.nigol.obec.entities.*;
import cz.nigol.obec.services.*;

@Stateless
public class WaterSpendingServiceImpl implements WaterSpendingService {
    @PersistenceContext(unitName="obecPU")
    private EntityManager em;

    @Override
    public List<WaterSpending> getAllWaterSpendings() {
        TypedQuery<WaterSpending> typedQuery = em.createNamedQuery(WaterSpending.GET_ALL, WaterSpending.class);
        return typedQuery.getResultList();
    }

    @Override
    public void saveWaterSpending(WaterSpending waterSpending) {
        em.merge(waterSpending);
    }
    
    @Override
    public List<WaterSpending> getWaterSpendingsByPeriod(String period) {
        TypedQuery<WaterSpending> typedQuery = em.createNamedQuery(WaterSpending.GET_BY_PERIOD, WaterSpending.class);
        typedQuery.setParameter(WaterSpending.PERIOD_PARAM, period);
        return typedQuery.getResultList();
    }
    
    @Override
    public List<String> getAllPeriods() {
        TypedQuery<String> typedQuery = em.createNamedQuery(WaterSpending.GET_PERIODS, String.class);
        return typedQuery.getResultList();
    }
}