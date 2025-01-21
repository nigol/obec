package cz.nigol.obec.beans;

import java.io.*;
import java.util.*;
import javax.inject.*;
import javax.faces.view.*;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.annotation.*;
import cz.nigol.obec.entities.*;
import cz.nigol.obec.services.*;
import cz.nigol.obec.qualifiers.*;

@Named
@ViewScoped
public class UserOverviewBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private FacesContext facesContext;
    @Inject
    @LoggedUser
    private User user;
    @Inject
    private NewsService newsService;
    @Inject
    private AnnouncementService announcementService;
    @Inject
    private OfficialDeskService officialDeskService;
    @Inject
    private EventsService eventsService;
    @Inject
    private UserService userService;
    private List<News> newsList;
    private Announcement announcement;
    private Event event;

    @PostConstruct
    public void init() {
        user = userService.getUserById(user.getId());
        newsList = newsService.getFeaturedCount(4);
        List<Announcement> announcements = announcementService.getLastTen();
        if (!announcements.isEmpty()) {
            announcement = announcements.get(0);
        }
        List<Event> events = eventsService.getValidToDate(new Date(), 1);
        if (!events.isEmpty()) {
            event = events.get(0);
        }
    }

    public void save() {
        userService.saveUser(user);
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Údaje byly uloženy."));
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Announcement getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}