package cz.nigol.obec.beans;

import java.io.*;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cz.nigol.obec.entities.*;
import cz.nigol.obec.qualifiers.CurrentSettings;
import cz.nigol.obec.services.*;

@Named
@ViewScoped
public class OfficialDeskTableBean implements Serializable {
    private static final long serialVersionUID = -3038448109293217757L;
    private static final String ACTIVE = "Active";
    private static final String ALL = "All";
    @Inject
    private OfficialDeskService officialDeskService;
    @Inject
    @CurrentSettings
    private Settings settings;
    @Inject
    private RssService rssService;
    @Inject
    private FacesContext facesContext;
    private List<DeskItem> deskItems;
    private String display = ACTIVE;
    private String rss;

    @PostConstruct
    public void init() {
        loadData();
    }

    public void onLoad() throws IOException {
        if (rss != null && "true".equals(rss)) {
            generateRssChannel();
        }
    }

    private void generateRssChannel() throws IOException {
        String url = settings.getBaseUrl() + "/obecni-urad/uredni-desk/prehled.jsf?id=";
        String feedUrl = settings.getBaseUrl() + "/obecni-urad/uredni-deska/prehled.jsf?rss=true";
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.responseReset();
        externalContext.setResponseContentType("application/rss+xml");
        OutputStream outputStream = externalContext.getResponseOutputStream();
        rssService.generateRss(officialDeskService.getAllRss(), url, 
            "Úřední deska, Obec Tršice", outputStream, feedUrl);
        outputStream.close();
        facesContext.responseComplete();
    }

    public void loadData() {
        if (ALL.equals(display)) {
            deskItems = officialDeskService.getAllValidDeskItems(new Date());
        } else {
            deskItems = officialDeskService.getActiveDeskItemsFor(new Date());
        }
    }

    public boolean activeItem(DeskItem deskItem) {
        Date today = new Date();
        Date to = deskItem.getActiveTo();
        to = to == null ? new Date(today.getTime() + 24 * 60 * 60 * 1000) : to;
        return today.compareTo(deskItem.getActiveFrom()) > 0 &&
            today.compareTo(to) < 0;
    }

    public String getRss() {
        return rss;
    }

    public void setRss(String rss) {
        this.rss = rss;
    }

    /**
     * @return the active
     */
    public String getActive() {
        return ACTIVE;
    }

    /**
     * @return the all
     */
    public String getAll() {
        return ALL;
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
     * @return the display
     */
    public String getDisplay() {
        return display;
    }

    /**
     * @param display the display to set
     */
    public void setDisplay(String display) {
        this.display = display;
    }

}
