package cz.nigol.obec.beans;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cz.nigol.obec.entities.Announcement;
import cz.nigol.obec.entities.Settings;
import cz.nigol.obec.qualifiers.CurrentSettings;
import cz.nigol.obec.services.*;

@Named
@ViewScoped
public class AnnouncementTableBean implements Serializable {
    private static final long serialVersionUID = 5058918096082190355L;
    @Inject
    private AnnouncementService announcementService;
    @Inject
    @CurrentSettings
    private Settings settings;
    @Inject
    private FacesContext facesContext;
    @Inject
    private RssService rssService;
    @Inject
    private UserService userService;
    private List<Announcement> announcements;
    private String rss;
    private String email;
    private boolean sent;

    @PostConstruct
    public void init() {
        announcements = announcementService.getAll();
    }

    public void onLoad() throws IOException {
        if (rss != null && "true".equals(rss)) {
            generateRssChannel();
        }
    }

    private void generateRssChannel() throws IOException {
        String url = settings.getBaseUrl() + "/obecni-urad/rozhlas/detail.jsf?id=";
        String feedUrl = settings.getBaseUrl() + "/obecni-urad/rozhlas/hlaseni.jsf?rss=true";
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.responseReset();
        externalContext.setResponseContentType("application/rss+xml");
        OutputStream outputStream = externalContext.getResponseOutputStream();
        rssService.generateRss(announcementService.getAllRss(), url, 
            "Hlášení rozhlasu, Obec Tršice", outputStream, feedUrl);
        outputStream.close();
        facesContext.responseComplete();
    }

    public void subscribe() {
        userService.subscribeAnnouncements(email);
        sent = true;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
     * @return the rss
     */
    public String getRss() {
        return rss;
    }

    /**
     * @param rss the rss to set
     */
    public void setRss(String rss) {
        this.rss = rss;
    }
}
