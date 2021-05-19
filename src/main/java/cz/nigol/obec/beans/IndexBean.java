package cz.nigol.obec.beans;

import java.util.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cz.nigol.obec.entities.*;
import cz.nigol.obec.qualifiers.*;
import cz.nigol.obec.services.*;

@Named
@RequestScoped
public class IndexBean {
    @Inject
    private NewsService newsService;
    @Inject
    private AnnouncementService announcementService;
    @Inject
    private OfficialDeskService officialDeskService;
    @Inject
    private EventsService eventsService;
    @Inject
    private PollService pollService;
    @Inject
    @CurrentSettings
    private Settings settings;
    @Inject
    private SettingsService settingsService;
    private List<News> newsList;
    private List<Announcement> announcements;
    private List<DeskItem> deskItems;
    private List<Event> events;
    private String nameOfPhoto;
    private Poll poll;
    private List<String> galleryPhotos;

    @PostConstruct
    public void init() {
        newsList = newsService.getFeaturedByDate(new Date());
        announcements = announcementService.getLastTen();
        deskItems = officialDeskService.getTenActiveDeskItemsFor(new Date());
        events = eventsService.getValidToDate(new Date());
        galleryPhotos = settingsService.processGalleryUrls(settings);
        nameOfPhoto = preparePhoto(galleryPhotos.size());
        poll = pollService.getActivePoll();
    }

    private String preparePhoto(int numOfPhotos) {
        Random random = new Random();
        int num = random.nextInt(numOfPhotos); 
        return galleryPhotos.get(num);
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    /**
     * @return the newsList
     */
    public List<News> getNewsList() {
        return newsList;
    }

    /**
     * @param newsList the newsList to set
     */
    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    /**
     * @return the announcements
     */
    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    /**
     * @param announcements the announcements to set
     */
    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    /**
     * @return the deskItems
     */
    public List<DeskItem> getDeskItems() {
        return deskItems;
    }

    /**
     * @param deskItems the deskItems to set
     */
    public void setDeskItems(List<DeskItem> deskItems) {
        this.deskItems = deskItems;
    }

    /**
     * @return the events
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * @param events the events to set
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    /**
     * @return the nameOfPhoto
     */
    public String getNameOfPhoto() {
        return nameOfPhoto;
    }

    /**
     * @param nameOfPhoto the nameOfPhoto to set
     */
    public void setNameOfPhoto(String nameOfPhoto) {
        this.nameOfPhoto = nameOfPhoto;
    }

    /**
     * @return the poll
     */
    public Poll getPoll() {
        return poll;
    }

    /**
     * @param poll the poll to set
     */
    public void setPoll(Poll poll) {
        this.poll = poll;
    }

}
