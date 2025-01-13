package cz.nigol.obec.services;

import java.util.Date;
import java.util.List;

import cz.nigol.obec.entities.Event;

public interface EventsService {
    List<Event> getAllEvents();
    Event getById(long id);
    List<Event> getValidToDate(Date date, int count);
    Event save(Event event);
    void delete(Event event);
    String getAsIcal(Event event);
}
