package com.example.wanandroid.model;

import java.util.List;

public class Project {
    private String author;
    private String chapterId;
    private String chapterName;
    private String desc;
    private String envelopePic;
    private String niceShareDate;
    private String projectLink;
    private String title;
    private List<Tag> tags;
    private String superChapterName;

    public class Tag {
        private String name;
        private String url;

        public Tag(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public Project(String author, String chapterId, String chapterName, String desc, String envelopePic, String niceShareDate, String projectLink, String title, List<Tag> tags, String superChapterName) {
        this.author = author;
        this.chapterId = chapterId;
        this.chapterName = chapterName;
        this.desc = desc;
        this.envelopePic = envelopePic;
        this.niceShareDate = niceShareDate;
        this.projectLink = projectLink;
        this.title = title;
        this.tags = tags;
        this.superChapterName = superChapterName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEnvelopePic() {
        return envelopePic;
    }

    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
    }

    public String getNiceShareDate() {
        return niceShareDate;
    }

    public void setNiceShareDate(String niceShareDate) {
        this.niceShareDate = niceShareDate;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getSuperChapterName() {
        return superChapterName;
    }

    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
    }
}
