package cz.nigol.obec.beans.admin;

import java.io.*;
import java.util.*;
import javax.inject.*;
import javax.faces.view.*;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.annotation.*;
import cz.nigol.obec.entities.*;
import cz.nigol.obec.services.*;

@Named
@ViewScoped
public class WaterSpendingsBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WaterSpendingService waterSpendingService;
    @Inject
    private FacesContext facesContext;
    private Settings settings;
    private List<WaterSpending> waterSpendings;
    private List<String> periods;
    private String period = "";

    @PostConstruct
    public void init() {
        periods = waterSpendingService.getAllPeriods();
        if (periods.size() > 0) {
            period = periods.get(0);
        }
        waterSpendings = waterSpendingService.getWaterSpendingsByPeriod(period);
    }
    
    public void onPeriodSelect(String period) {
        this.period = period;
        waterSpendings = waterSpendingService.getWaterSpendingsByPeriod(period);
    }
    
    public String getPeriod() {
        return this.period;
    }
    
    public List<String> getPeriods() {
        return this.periods;
    }

    public List<WaterSpending> getWaterSpendings() {
        return waterSpendings;
    }
}