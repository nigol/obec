package cz.nigol.obec.beans.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cz.nigol.obec.entities.*;
import cz.nigol.obec.qualifiers.CurrentSettings;
import cz.nigol.obec.qualifiers.LoggedUser;
import cz.nigol.obec.services.ArticleService;
import cz.nigol.obec.services.NewsService;
import cz.nigol.obec.services.UserService;

@Named
@ViewScoped
public class NewsBean implements Serializable {
    private static final long serialVersionUID = 3124927174451007316L;
    @Inject
    private NewsService newsService;
    @Inject
    @LoggedUser
    private User user;
    @Inject
    private FacesContext facesContext;
    @Inject
    private UserService userService;
    @Inject
    private ArticleService articleService;
    @Inject
    @CurrentSettings
    private Settings settings;
    private List<News> newsList;
    private News news;
    private String body;

    @PostConstruct
    public void init() {
        user = userService.getUserById(user.getId());
        loadNews();
    }

    private void loadNews() {
        newsList = newsService.getAll();
    } 

    public void onNewsSelect() {
        news.setArticle(articleService.loadArticleBody(news.getArticle()));
        body = news.getArticle().getBody();
    }

    public void newNews() {
        news = new News();
        news.setArticle(new Article());
        news.getArticle().setOgImageUrl(settings.getOgImageUrl());
        newsList.add(news);
        news.setCreatedAt(new Date());
        body = null;
    }

    public void save() {
        Article article = news.getArticle();
        article.setChangedAt(news.getCreatedAt());
        if (article.getId() == null) {
            String id = news.getLabel().split(" ",2)[0]; 
            id = id + "-" + new Date();
            article.setId(id);
        }
        article.setChangedBy(user);
        article.setBody(body);
        article.setOgDescription(news.getLabel());
        newsService.save(news);
        String label = news.getLabel();
        news = null;
        body = null;
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Aktualita '" +
                    label + "' byla uložena."));
        loadNews();
    }

    public void cancelEdit() {
        news = null;
        body = null;
        loadNews();
    }

    public void delete() {
        newsService.delete(news);
        news = null;
        body = null;
        loadNews();
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
     * @return the news
     */
    public News getNews() {
        return news;
    }

    /**
     * @param news the news to set
     */
    public void setNews(News news) {
        this.news = news;
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(String body) {
        this.body = body;
    }
}
