package cz.nigol.obec.services.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cz.nigol.obec.entities.Settings;
import cz.nigol.obec.services.SettingsService;
import java.util.*;

@Stateless
public class SettingsServiceImpl implements SettingsService {
    @PersistenceContext(unitName="obecPU")
    private EntityManager em;

    @Override
    public Settings findById(String id) {
        Settings settings = em.find(Settings.class, id);
        if (settings == null) {
            settings = new Settings();
            settings.setId(id);
            settings = save(settings);
        }
        return settings;
    }

    @Override
    public Settings save(Settings settings) {
        return em.merge(settings);
    }
}
