package cz.nigol.obec.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.*;

import cz.nigol.obec.entities.Article;
import cz.nigol.obec.services.ArticleService;

@Named
@RequestScoped
public class FooterBean {
    @Inject
    private ArticleService articleService;
    private Article article;
    private static final String articleId = "footer";

    @PostConstruct
    public void init() {
        article = articleService.getArticleById(articleId);
        if (article != null) {
            article = articleService.loadArticleBody(article);
        }
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
