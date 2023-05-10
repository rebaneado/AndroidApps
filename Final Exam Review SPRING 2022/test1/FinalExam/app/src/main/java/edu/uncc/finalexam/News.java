package edu.uncc.finalexam;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class News implements Serializable {

    String source_id,source_name;

    String author,title, description,url,urlToImage,publishedAt,content;
    /*
     {
    "source": {
        "id": null,
        "name": "Kotaku"
    },
    "author": "Luke Plunkett",
    "title": "Tragic Nintendo Unboxing For Trashed Relics Has Happy Ending - Kotaku",
    "description": "From despair to triumph, a Nintendo collector finally has his cards",
    "url": "https://kotaku.com/nintendo-collector-merch-kyoto-unbox-unboxing-cards-1850417280",
    "urlToImage": "https://i.kinja-img.com/gawker-media/image/upload/c_fill,f_auto,fl_progressive,g_center,h_675,pg_1,q_80,w_1200/5a83fdfb005722762e1fab86b4758d38.png",
    "publishedAt": "2023-05-08T23:40:00Z",
    "content": "In August 2022 we told the very sad story of a serious Nintendo collector who, having thought hed got hold of some very special old playing cards, had in fact got hold of two hunks of worthless cardb… [+2793 chars]"
        },
     */

    public News() {

    }

    public News(JSONObject jsonObject)throws JSONException {
        this.source_id = jsonObject.getJSONObject("source").getString("id");
        this.source_name = jsonObject.getJSONObject("source").getString("name");

        this.author = jsonObject.getString("author");
        this.title = jsonObject.getString("title");
        this.description = jsonObject.getString("description");
        this.url = jsonObject.getString("url");
        this.urlToImage = jsonObject.getString("urlToImage");
        this.publishedAt = jsonObject.getString("publishedAt");
        this.content = jsonObject.getString("context");


    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
