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

import cz.nigol.obec.entities.News;
import cz.nigol.obec.entities.Settings;
import cz.nigol.obec.qualifiers.CurrentSettings;
import cz.nigol.obec.services.NewsService;
import cz.nigol.obec.services.RssService;

@Named
@ViewScoped
public class NewsTableBean implements Serializable {
    private static final long serialVersionUID = 3124927174451007316L;
    @Inject
    private NewsService newsService;
    @Inject
    private FacesContext facesContext;
    @Inject
    private RssService rssService;
    @Inject
    @CurrentSettings
    private Settings settings;
    private List<News> newsList;
    private String rss;

    @PostConstruct
    public void init() {
        newsList = newsService.getAll();
    }

    public void onLoad() throws IOException {
        if (rss != null && "true".equals(rss)) {
            generateRssChannel();
        }
    }

    private void generateRssChannel() throws IOException {
        String url = settings.getBaseUrl() + "/obec/aktuality/detail.jsf?id=";
        String feedUrl = settings.getBaseUrl() + "/obec/aktuality/aktuality.jsf?rss=true";
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.responseReset();
        externalContext.setResponseContentType("application/rss+xml");
        OutputStream outputStream = externalContext.getResponseOutputStream();
        rssService.generateRss(newsService.getAllRss(), url, 
            "Aktuality, Obec Tršice", outputStream, feedUrl);
        outputStream.close();
        facesContext.responseComplete();
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
