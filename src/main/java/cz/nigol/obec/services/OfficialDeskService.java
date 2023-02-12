package cz.nigol.obec.services;

import java.util.Date;
import java.util.List;

import cz.nigol.obec.entities.DeskItem;
import cz.nigol.obec.enums.OfficialDeskCategory;
import cz.nigol.obec.entities.interfaces.*;

public interface OfficialDeskService {
    DeskItem getDeskItemById(long id);
    DeskItem saveDeskItem(DeskItem deskItem);
    void deleteDeskItem(DeskItem deskItem);
    List<DeskItem> getAllDeskItems();
    List<DeskItem> getActiveDeskItemsFor(Date date);
    List<DeskItem> getAllValidDeskItems(Date date);
    List<DeskItem> getTenActiveDeskItemsFor(Date date);
	List<DeskItem> getDeskItemsByCategory(OfficialDeskCategory category, Date date);
    List<RssItem> getAllRss();
}
