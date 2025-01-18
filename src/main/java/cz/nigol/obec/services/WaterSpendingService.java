package cz.nigol.obec.services;

import java.util.*;
import cz.nigol.obec.entities.*;

public interface WaterSpendingService {
    List<WaterSpending> getAllWaterSpendings();
    void saveWaterSpending(WaterSpending waterSpending);
    List<WaterSpending> getWaterSpendingsByPeriod(String period);
    List<String> getAllPeriods();
}