package cz.nigol.obec.services.impl;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import cz.nigol.obec.entities.DeskItem;
import cz.nigol.obec.entities.interfaces.*;
import cz.nigol.obec.enums.OfficialDeskCategory;
import cz.nigol.obec.services.OfficialDeskService;

@Stateless
public class OfficialDeskServiceImpl implements OfficialDeskService {
    @PersistenceContext(unitName="obecPU")
    private EntityManager em;

    @Override
    public DeskItem getDeskItemById(long id) {
        return em.find(DeskItem.class, id);
    }

    @Override
    public DeskItem saveDeskItem(DeskItem deskItem) {
        return em.merge(deskItem);
    }

    @Override
    public void deleteDeskItem(DeskItem deskItem) {
        em.remove(em.merge(deskItem));
    }

    @Override
    public List<DeskItem> getAllDeskItems() {
        TypedQuery<DeskItem> typedQuery = em.createNamedQuery(DeskItem.GET_ALL, DeskItem.class);
        return new ArrayList<>(typedQuery.getResultList());
    }

    @Override
    public List<DeskItem> getActiveDeskItemsFor(Date date) {
        TypedQuery<DeskItem> typedQuery = em.createNamedQuery(DeskItem.GET_ACTIVE_TO_DATE, DeskItem.class);
        typedQuery.setParameter(DeskItem.DATE_PARAM, date);
        return new ArrayList<>(typedQuery.getResultList());
    }

    @Override
    public List<DeskItem> getAllValidDeskItems(Date date) {
        TypedQuery<DeskItem> typedQuery = em.createNamedQuery(DeskItem.GET_VALID_TO_DATE, DeskItem.class);
        typedQuery.setParameter(DeskItem.DATE_PARAM, date);
        return new ArrayList<>(typedQuery.getResultList());
    }

    @Override
    public List<DeskItem> getTenActiveDeskItemsFor(Date date) {
        TypedQuery<DeskItem> typedQuery = em.createNamedQuery(DeskItem.GET_ACTIVE_TO_DATE, DeskItem.class);
        typedQuery.setParameter(DeskItem.DATE_PARAM, date);
        typedQuery.setMaxResults(10);
        return new ArrayList<>(typedQuery.getResultList());
    }

    @Override
    public List<DeskItem> getDeskItemsByCategory(OfficialDeskCategory category, Date date) {
        TypedQuery<DeskItem> typedQuery = em.createNamedQuery(DeskItem.GET_BY_CATEGORY,
                DeskItem.class);
        typedQuery.setParameter(DeskItem.CATEGORY_PARAM, category);
        typedQuery.setParameter(DeskItem.DATE_PARAM, date);
        return new ArrayList<>(typedQuery.getResultList());
    }

    @Override
    public List<RssItem> getAllRss() {
        TypedQuery<RssItem> typedQuery = em.createNamedQuery(DeskItem.GET_ALL, RssItem.class);
        return new ArrayList<>(typedQuery.getResultList());
    }
}
