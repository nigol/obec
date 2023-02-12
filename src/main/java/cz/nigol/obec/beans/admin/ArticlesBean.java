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
import cz.nigol.obec.services.UserService;

@Named
@ViewScoped
public class ArticlesBean implements Serializable {
    private static final long serialVersionUID = -2626968175893152327L;
    @Inject
    private ArticleService articleService;
    @Inject
    private UserService userService;
    @Inject
    @LoggedUser
    private User user;
    @Inject
    private FacesContext facesContext;
    @Inject
    @CurrentSettings
    private Settings settings;
    private List<Article> articles;
    private Article article;
    private String body;
    private String edit = "Text";
    public static final String NEW_ID = "článek";
    private String pattern;

    @PostConstruct
    public void init() {
        user = userService.getUserById(user.getId());
    }

    public void onLoad() {
        if (pattern != null ) {
            articles = articleService.getArticlesByPattern(pattern
                    .replaceAll("\\*", "%"));
        }
    }

    public void newArticle() {
        article = new Article();
        body = null;
        article.setId(NEW_ID);
        article.setOgImageUrl(settings.getOgImageUrl());
        articles.add(article);
    }

    public void delete() {
        articleService.deleteArticle(article);
        article = null;
        body = null;
        onLoad();
    }

    public void save() {
        article.setChangedAt(new Date());
        article.setEdit(edit);
        article.setChangedBy(user);
        articleService.saveArticle(article, body);
        String id = article.getId();
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Článek '" +
                    id + "' byl uložen."));
    }

    public void onArticleSelect() {
        article = articleService.loadArticleBody(article);
        edit = article.getEdit();
        body = article.getBody();
    }

    public void cancelEdit() {
        article = null;
        body = null;
        onLoad();
    }

    public boolean isLimited() {
        return !"*".equals(pattern);
    }

    /**
     * @return the articles
     */
    public List<Article> getArticles() {
        return articles;
    }

    /**
     * @param articles the articles to set
     */
    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    /**
     * @return the article
     */
    public Article getArticle() {
        return article;
    }

    /**
     * @param article the article to set
     */
    public void setArticle(Article article) {
        this.article = article;
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

    /**
     * @return the editSource
     */
    public boolean isEditSource() {
        return "HTML".equals(edit);
    }

    /**
     * @return the edit
     */
    public String getEdit() {
        return edit;
    }

    /**
     * @param edit the edit to set
     */
    public void setEdit(String edit) {
        this.edit = edit;
    }

    /**
     * @return the pattern
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * @param pattern the pattern to set
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
