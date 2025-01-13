package cz.nigol.obec.beans;

import java.io.*;
import java.util.*;
import java.nio.charset.Charset;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.*;
import javax.inject.*;

import cz.nigol.obec.entities.Event;
import cz.nigol.obec.services.EventsService;

@Named
@RequestScoped
public class EventBean {
    @Inject
    private EventsService eventsService;
    @Inject
    private FacesContext facesContext;
    private String ical;
    private String id;
    private Event event;
    private List<Event> events;

    @PostConstruct
    public void init() {
        events = eventsService.getValidToDate(new Date(), 100);
    }
    
    public void onLoad() throws IOException {
        event = eventsService.getById(Long.parseLong(id));
        if (event != null && ical != null && "true".equals(ical)) {
            generateIcal();
        }
    }

    private void generateIcal() throws IOException {
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.responseReset();
        externalContext.setResponseHeader("Content-Type", "text/calendar; charset=utf-8");
        externalContext.setResponseHeader("Content-Disposition", "inline;filename=event.ics");
        OutputStream outputStream = externalContext.getResponseOutputStream();
        outputStream.write(eventsService.getAsIcal(event).getBytes(Charset.forName("UTF-8")));
        outputStream.close();
        facesContext.responseComplete();
    }
    
    public List<Event> getEvents() {
        return this.events;
    }

    /**
     * @return the ical
     */
    public String getIcal() {
        return ical;
    }

    /**
     * @param ical the ical to set
     */
    public void setIcal(String ical) {
        this.ical = ical;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the event
     */
    public Event getEvent() {
        return event;
    }

    /**
     * @param event the event to set
     */
    public void setEvent(Event event) {
        this.event = event;
    }
}
