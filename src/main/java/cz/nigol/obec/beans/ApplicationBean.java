package cz.nigol.obec.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import cz.nigol.obec.entities.Path;
import cz.nigol.obec.qualifiers.SecuredPaths;
import cz.nigol.obec.services.UserService;

@Named
@ApplicationScoped
public class ApplicationBean {
    @Inject
    private UserService userService;
    private Map<Integer, String> daysOfWeek;
    private List<Path> securedPaths;

    @PostConstruct
    public void init() {
        prepareDaysOfWeek();
        preparePaths();
    }

    private void preparePaths() {
        List<String> paths = new ArrayList<>();
        paths.add("/administrace/clanky.jsf");
        paths.add("/administrace/nahled.jsf");
        paths.add("/obec/aktuality/administrace.jsf");
        paths.add("/obecni-urad/rozhlas/administrace.jsf");
        paths.add("/obecni-urad/uredni-deska/administrace.jsf");
        securedPaths = userService.initializePaths(paths);
    }

    private void prepareDaysOfWeek() {
        daysOfWeek = new HashMap<>();
        daysOfWeek.put(1, "neděle");
        daysOfWeek.put(2, "pondělí");
        daysOfWeek.put(3, "úterý");
        daysOfWeek.put(4, "středa");
        daysOfWeek.put(5, "čtvrtek");
        daysOfWeek.put(6, "pátek");
        daysOfWeek.put(7, "sobota");
    }

    public Date today() {
        return new Date();
    }

    public String todayNameOfDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today());
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return daysOfWeek.get(dayOfWeek);
    }

    /**
     * @return the securedPaths
     */
    @Produces
    @SecuredPaths
    public List<Path> getSecuredPaths() {
        return securedPaths;
    }
}
