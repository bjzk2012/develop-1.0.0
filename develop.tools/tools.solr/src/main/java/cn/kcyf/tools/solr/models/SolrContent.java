package cn.kcyf.tools.solr.models;

import org.apache.solr.client.solrj.beans.Field;

/**
 * Solr查询对象
 *
 * @author Tom
 */
public class SolrContent extends BasicSolrContent {
    @Field
    private String title;
    @Field
    private String subject;
    @Field
    private String description;
    @Field
    private String comments;
    @Field
    private String author;
    @Field
    private String keywords;
    @Field
    private String url;
    @Field
    private String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
