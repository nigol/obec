package cz.nigol.obec.services;

import java.io.OutputStream;
import java.util.List;

import cz.nigol.obec.entities.interfaces.RssItem;

public interface RssService {
    public void generateRss(List<RssItem> items, String url, String title, 
        OutputStream outputStream, String feedUrl);
}
