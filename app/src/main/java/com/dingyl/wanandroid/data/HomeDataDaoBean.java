package com.dingyl.wanandroid.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class HomeDataDaoBean {
    @Id
    private long id;
    private String author;
    private String publishTime;
    private String content;
    private String rootTitle;
    private String isProject;
    private boolean isLove;
    @Generated(hash = 1561749012)
    public HomeDataDaoBean(long id, String author, String publishTime,
            String content, String rootTitle, String isProject, boolean isLove) {
        this.id = id;
        this.author = author;
        this.publishTime = publishTime;
        this.content = content;
        this.rootTitle = rootTitle;
        this.isProject = isProject;
        this.isLove = isLove;
    }
    @Generated(hash = 634196705)
    public HomeDataDaoBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getAuthor() {
        return this.author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getPublishTime() {
        return this.publishTime;
    }
    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getRootTitle() {
        return this.rootTitle;
    }
    public void setRootTitle(String rootTitle) {
        this.rootTitle = rootTitle;
    }
    public String getIsProject() {
        return this.isProject;
    }
    public void setIsProject(String isProject) {
        this.isProject = isProject;
    }
    public boolean getIsLove() {
        return this.isLove;
    }
    public void setIsLove(boolean isLove) {
        this.isLove = isLove;
    }
}
