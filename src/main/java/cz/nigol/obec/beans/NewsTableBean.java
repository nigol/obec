package cz.nigol.obec.beans;

import java.io.*;
import java.util.List;
import java.util.stream.*;

import javax.annotation.PostConstruct;
import javax.faces.context.*;
import javax.faces.view.ViewScoped;
import javax.inject.*;

import cz.nigol.obec.entities.*;
import cz.nigol.obec.qualifiers.CurrentSettings;
import cz.nigol.obec.services.*;

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
    private String search;
    private List<News> newsFilter;

    @PostConstruct
    public void init() {
        newsList = newsService.getAll();
        newsFilter = newsList;
    }

    public void onLoad() throws IOException {
        if (rss != null && "true".equals(rss)) {
            generateRssChannel();
        }
    }

    public void filter() {
        newsFilter = newsList.stream()
            .filter(n -> n.getLabel().toLowerCase().contains(search.toLowerCase()))
            .collect(Collectors.toList());
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

    public List<News> getNewsFilter() {
        return newsFilter;
    }

    public void setNewsFilter(List<News> newsFilter) {
        this.newsFilter = newsFilter;
    }
    
    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
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
