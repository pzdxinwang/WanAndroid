package com.example.wanandroid.model;

/**
 * "desc": "扔物线",
 *             "id": 29,
 *             "imagePath": "https://wanandroid.com/blogimgs/5d362c2a-2e9e-4448-8ee4-75470c8c7533.png",
 *             "isVisible": 1,
 *             "order": 0,
 *             "title": "LiveData：还没普及就让我去世？我去你的 Kotlin 协程",
 *             "type": 0,
 *             "url": "https://url.rengwuxian.com/y3zsb"
 */
public class Banner {
    private int id;
    private String imagePath;
    private String title;
    private String url;

    public Banner(int id, String imagePath, String title, String url) {
        this.id = id;
        this.imagePath = imagePath;
        this.title = title;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
