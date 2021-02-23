package cz.nigol.obec.beans;

import java.io.*;
import java.util.*;
import javax.inject.*;
import javax.faces.view.*;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.annotation.*;
import cz.nigol.obec.entities.*;
import cz.nigol.obec.services.*;
import java.util.stream.*;
import cz.nigol.obec.qualifiers.*;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.donut.*;


@Named
@ViewScoped
public class CouncillorBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private CouncillorService councillorService;
    @Inject
    private FacesContext facesContext;
    @Inject
    @CurrentSettings
    private Settings settings;
    private Councillor councillor;
    private long id;
    private int percentage;
    private DonutChartModel chartModel;
    private int allMeetings;
    private int attendedMeetings;
    private Question question;

    public void onLoad() {
        councillor = councillorService.findCouncillorById(id);
        allMeetings = councillorService.getAllCouncilMeetingsFor(councillor).size();
        attendedMeetings = councillor.getCouncilMeetings().size();
        percentage = (100 * attendedMeetings) / allMeetings;
        prepareChartModel();
        question = new Question();
    }

    public void sendQuestion() {
        question.setCreatedAt(new Date());
        councillor.getQuestions().add(question);
        councillorService.saveCouncillor(councillor);
        question = new Question();
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Váš dotaz byl uložen. Bude doručen zastupiteli."));
    }

    private void prepareChartModel() {
        chartModel = new DonutChartModel();
        ChartData data = new ChartData();
        DonutChartDataSet dataSet = new DonutChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(attendedMeetings);
        values.add(allMeetings - attendedMeetings);
        dataSet.setData(values);
        List<String> colors = new ArrayList<>();
        colors.add("rgb(255, 99, 132)");
        colors.add("rgb(255, 205, 86)");
        dataSet.setBackgroundColor(colors);
        List<String> labels = new ArrayList<>();
        labels.add("P");
        labels.add("N");
        data.setLabels(labels);
        data.addChartDataSet(dataSet);
        chartModel.setData(data);
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getUrl() {
        return settings.getBaseUrl() + "/obec/zastupitelstvo/zastupitel.jsf?id=" + councillor.getId();
    }

    public DonutChartModel getChartModel() {
        return chartModel;
    }

    public void setChartModel(DonutChartModel chartModel) {
        this.chartModel = chartModel;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public long getId() {
        return id;
    }

    public void setid(long id) {
        this.id = id;
    }

    public Councillor getCouncillor() {
        return councillor;
    }

    public void setCouncillor(Councillor councillor) {
        this.councillor = councillor;
    }

}