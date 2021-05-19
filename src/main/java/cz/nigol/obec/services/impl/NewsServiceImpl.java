package cz.nigol.obec.services.impl;

import java.util.ArrayList;
import java.util.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import cz.nigol.obec.entities.Article;
import cz.nigol.obec.entities.News;
import cz.nigol.obec.entities.interfaces.RssItem;
import cz.nigol.obec.services.ArticleService;
import cz.nigol.obec.services.NewsService;

@Stateless
public class NewsServiceImpl implements NewsService {
    @PersistenceContext(unitName="obecPU")
    private EntityManager em;
    @Inject
    private ArticleService articleService;

    @Override
    public News save(News news) {
        Article article = articleService.saveArticle(news.getArticle(), news.getArticle().getBody());
        news.setArticle(article);
        return em.merge(news);
    }

    @Override
    public List<News> getAll() {
        TypedQuery<News> typedQuery = em.createNamedQuery(News.GET_ALL, News.class);
        return new ArrayList<>(typedQuery.getResultList());
    }

    @Override
    public News getById(long id) {
        return em.find(News.class, id);
    }

    @Override
    public void delete(News news) {
        em.remove(em.merge(news));
    }

    @Override
    public List<News> getFeatured() {
        TypedQuery<News> typedQuery = em.createNamedQuery(News.GET_FEATURED, News.class);
        return new ArrayList<>(typedQuery.getResultList());
    }

    @Override
    public List<RssItem> getAllRss() {
        TypedQuery<RssItem> typedQuery = em.createNamedQuery(News.GET_ALL, RssItem.class);
        return new ArrayList<>(typedQuery.getResultList());
    }

    @Override
    public List<News> getFeaturedCount(int count) {
        TypedQuery<News> typedQuery = em.createNamedQuery(News.GET_FEATURED, News.class);
        typedQuery.setMaxResults(count);
        return new ArrayList<>(typedQuery.getResultList());
    }
    
    @Override
    public List<News> getFeaturedByDate(Date date) {
        TypedQuery<News> typedQuery = em.createNamedQuery(News.GET_FEATURED_BY_DATE, News.class);
        typedQuery.setParameter(News.END_DATE_PARAM, date);
        return new ArrayList<>(typedQuery.getResultList());
    }
}
