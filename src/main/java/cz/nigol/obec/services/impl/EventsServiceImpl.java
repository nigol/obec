package cz.nigol.obec.services.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import cz.nigol.obec.entities.Event;
import cz.nigol.obec.services.EventsService;

@Stateless
public class EventsServiceImpl implements EventsService {
    @PersistenceContext(unitName="obecPU")
    private EntityManager em;

    @Override
    public List<Event> getAllEvents() {
        TypedQuery<Event> typedQuery = em.createNamedQuery(Event.GET_ALL, Event.class);
        return new ArrayList<>(typedQuery.getResultList());
    }

    @Override
    public Event getById(long id) {
        return em.find(Event.class, id);
    }

    @Override
    public List<Event> getValidToDate(Date date) {
        TypedQuery<Event> typedQuery = em.createNamedQuery(Event.GET_VALID_TO_DATE, Event.class);
        LocalDate localDate = date.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
        Date adjustedDate = Date.from(localDate.minusDays(1)
            .atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant());
        typedQuery.setParameter(Event.DATE_PARAM, date);
        typedQuery.setMaxResults(2);
        return new ArrayList<>(typedQuery.getResultList());
    }

    @Override
    public Event save(Event event) {
        return em.merge(event);
    }

    @Override
    public void delete(Event event) {
        em.remove(em.merge(event));
    }

    @Override
    public String getAsIcal(Event event) {
        StringBuilder result = new StringBuilder();
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        result.append("BEGIN:VCALENDAR\r\n");
        result.append("PRODID:-//Obec Tršice//\r\n");
        result.append("VERSION:2.0\r\n");
        result.append("METHOD:PUBLISH\r\n");
        result.append("BEGIN:VEVENT\r\n");
        result.append("UID:trsice.cz");
        result.append(event.getId());
        result.append("\r\n");
        result.append("DTSTAMP:");
        result.append(formatter.format(event.getStartDate()));
        result.append("T000000Z\r\n");
        result.append("CLASS:PUBLIC\r\n");
        result.append("DESCRIPTION:");
        result.append(event.getDescription());
        result.append("\r\n");
        result.append("DTSTART;VALUE=DATE:");
        result.append(formatter.format(event.getStartDate()));
        result.append("\r\n");
        result.append("SUMMARY:");
        result.append(event.getDescription());
        result.append("\r\n");
        result.append("END:VEVENT\r\n");
        result.append("END:VCALENDAR\r\n");
        return result.toString();
    }
}
