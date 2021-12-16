package cz.nigol.obec.services;

import java.util.*;

import cz.nigol.obec.entities.News;
import cz.nigol.obec.entities.interfaces.RssItem;

public interface NewsService {
    News save(News news);
    List<News> getAll();
    News getById(long id);
    void delete(News news);
    List<News> getFeatured();
    List<RssItem> getAllRss();
    List<News> getFeaturedCount(int count);
    List<News> getFeaturedByDate(Date date);
}
