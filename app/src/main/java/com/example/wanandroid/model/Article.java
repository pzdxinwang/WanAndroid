package com.example.wanandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Article {
/*
{
    "apkLink": "",
    "audit": 1,
    "author": "鸿洋",
    "canEdit": false,
    "chapterId": 408,
    "chapterName": "鸿洋",
    "collect": false,
    "courseId": 13,
    "desc": "",
    "descMd": "",
    "envelopePic": "",
    "fresh": false,
    "host": "",
    "id": 18119,
    "link": "https://mp.weixin.qq.com/s/q4Xjy7snARM8R9LZ5nxu5g",
    "niceDate": "2021-04-29 00:00",
    "niceShareDate": "2021-04-29 23:44",
    "origin": "",
    "prefix": "",
    "projectLink": "",
    "publishTime": 1619625600000,
    "realSuperChapterId": 407,
    "selfVisible": 0,
    "shareDate": 1619711065000,
    "shareUser": "",
    "superChapterId": 408,
    "superChapterName": "公众号",
    "tags": [
        {+
            "name": "公众号",
            "url": "/wxarticle/list/408/1"
        }
    ],
    "title": "又卡了～从王者荣耀看Android屏幕刷新机制",
    "type": 0,
    "userId": -1,
    "visible": 1,
    "zan": 0
}
 */
    private String title;
    private String author;
    private List<Tag> tags;
    //使用SerializedName将niceData重命名为data
    @SerializedName("niceDate")
    private String date;
    private String link;
    private String id;
    private String chapterName;

    public Article(){

    }

    public Article(String title, String author, List<Tag> tags, String date, String link){
        this.title = title;
        this.author = author;
        this.tags = tags;
        this.date = date;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public String getDate() {
        return date;
    }

    public String getLink() {
        return link;
    }

    public String getId() {
        return id;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setId(String id) {
        this.id = id;
    }

    public class Tag {
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
}
