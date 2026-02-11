package cz.nigol.obec.services;

import cz.nigol.obec.entities.Settings;
import java.util.*;

public interface SettingsService {
    Settings findById(String id);
    Settings save(Settings settings);
}
