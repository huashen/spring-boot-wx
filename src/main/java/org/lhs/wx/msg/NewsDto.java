package org.lhs.wx.msg;

import java.util.List;

/**
 * Created by longhuashen on 17/5/5.
 */
public class NewsDto {

    private List<Articles> articles;

    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }
}
